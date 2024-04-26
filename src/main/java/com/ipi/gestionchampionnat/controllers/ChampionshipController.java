package com.ipi.gestionchampionnat.controllers;

import com.ipi.gestionchampionnat.models.Championship;
import com.ipi.gestionchampionnat.repository.ChampionShipRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/championships")
public class ChampionshipController {

    private ChampionShipRepository championShipRepository;

    public ChampionshipController(ChampionShipRepository championShipRepository){
        this.championShipRepository = championShipRepository;
    }

    //Get all championships
    @GetMapping("/")
    public List<Championship> getAll(){
        //championship.setAvis(null);
        return championShipRepository.findAll();
    }

    //Get one championship by id
    @GetMapping("/{championship}")
    public Championship getOne(@PathVariable(name = "championship", required = false) Championship championship){
        if (championship == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Championship introuvable"
            );
        }
        return championship;
    }


    //Create championship
    @PostMapping(value = "/")
    public ResponseEntity<Championship> createChampionship (@Valid @RequestBody Championship championship, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        } else {
            championShipRepository.save(championship);
            return new ResponseEntity<>(championship, HttpStatus.CREATED);
        }
    }

    //Update championship
    @PutMapping(value = "/{championship}")
    public ResponseEntity<Championship> updateChampionship(@PathVariable(name = "championship", required = false) Championship championship, @Valid @RequestBody Championship championshipUpdate, BindingResult bindingResult) {
        if (championship == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Championship introuvable"
            );
        } else {
            if (bindingResult.hasErrors()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
            } else {
                championshipUpdate.setId(championship.getId());
                championShipRepository.save(championshipUpdate);
                return new ResponseEntity<>(championshipUpdate, HttpStatus.CREATED);
            }
        }
    }

    //Delete championship
    @DeleteMapping(value = "/{championship}")
    public void deleteChampionship(@PathVariable(name = "championship", required = false) Championship championship) {
        if (championship == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Championship introuvable"
            );
        } else {
            championShipRepository.delete(championship);
        }
    }

}
