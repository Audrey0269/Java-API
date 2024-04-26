package com.ipi.gestionchampionnat.controllers;

import com.ipi.gestionchampionnat.models.Championship;
import com.ipi.gestionchampionnat.models.Day;
import com.ipi.gestionchampionnat.models.Team;
import com.ipi.gestionchampionnat.repository.ChampionShipRepository;
import com.ipi.gestionchampionnat.repository.DayRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/days")
public class DayController {

    private DayRepository dayRepository;
    private ChampionShipRepository championShipRepository;

    public DayController(DayRepository dayRepository,
                         ChampionShipRepository championShipRepository){
        this.dayRepository = dayRepository;
        this.championShipRepository = championShipRepository;
    }

    //Get All days
    @GetMapping("/")
    public List<Day> getAll(){
        return dayRepository.findAll();
    }

    //Get one day by id
    @GetMapping("/{day}")
    public Day getOne(@PathVariable(name = "day", required = false) Day day){
        if (day == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Day introuvable"
            );
        }
        return day;
    }

    //Get the list of days with championship id
    @GetMapping("/championship/{championshipId}/")
    public ResponseEntity<?> getAllDaysByChampionship(@PathVariable Long championshipId){
        //Vérifier si le champ existe
        if (!championShipRepository.existsById(championshipId)){
            return ResponseEntity.notFound().build();
        }
        //Récupérer le championship
        Championship championship = championShipRepository.findById(championshipId).orElse(null);

        //Récupérer les équipes associées au championship
        List<Day> days = championship.getDay();

        return ResponseEntity.ok(days);
    }


    //Create day to championship
    @PostMapping(value = "/add/{championshipId}")
    public ResponseEntity<String> createDayForChampionship (@PathVariable Long championshipId, @RequestBody Day day, BindingResult bindingResult) {
        //Vérifier si championship exist
        if (!championShipRepository.existsById(championshipId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Championship non trouvé");
        }
        //Récupérer le championship
        Championship championship = championShipRepository.findById(championshipId).orElse(null);

        // Associer day au championship
        day.setChampionship(championship);

        // Save
        dayRepository.save(day);

        return ResponseEntity.status(HttpStatus.OK).body("Day créé pour championship");
    }


    //Update day
    @PutMapping(value = "/{dayId}")
    public ResponseEntity<String> updateDay(@PathVariable Long dayId, @RequestBody Day updatedDay) {
        // Vérifie si day exist
        if (!dayRepository.existsById(dayId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Day not found");
        }
        // Récupérer day
        Day day = dayRepository.findById(dayId).orElse(null);

        // update day
        day.setNumber(updatedDay.getNumber());

        // save
        dayRepository.save(day);

        return ResponseEntity.status(HttpStatus.OK).body("Day mis à jour");
    }

    //Delete day
    @DeleteMapping(value = "/{day}")
    public void deleteDay(@PathVariable(name = "day", required = false) Day day) {
        if (day == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Day introuvable"
            );
        } else {
            dayRepository.delete(day);
        }
    }

}
