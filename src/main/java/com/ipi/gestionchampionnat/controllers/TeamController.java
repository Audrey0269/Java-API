package com.ipi.gestionchampionnat.controllers;

import com.ipi.gestionchampionnat.models.Championship;
import com.ipi.gestionchampionnat.models.Team;
import com.ipi.gestionchampionnat.models.User;
import com.ipi.gestionchampionnat.repository.ChampionShipRepository;
import com.ipi.gestionchampionnat.repository.TeamRepository;
import com.ipi.gestionchampionnat.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/teams")
public class TeamController {
    private TeamRepository teamRepository;
    private ChampionShipRepository championShipRepository;

    public TeamController(TeamRepository teamRepository,
                          ChampionShipRepository championShipRepository){
        this.teamRepository = teamRepository;
        this.championShipRepository = championShipRepository;
    }

    //Get All teams
    @GetMapping("/")
    public List<Team> getAll(){
        return teamRepository.findAll();
    }

    //Get one game by id
    @GetMapping("/{team}")
    public Team getOne(@PathVariable(name = "team", required = false) Team team){
        if (team == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Team introuvable"
            );
        }
        return team;
    }

    //Get the list of teams with championship id
    @GetMapping("/championship/{championshipId}/")
    public ResponseEntity<?> getAllTeamsByChampionship(@PathVariable Long championshipId){
        //Vérifier si le champ existe
        if (!championShipRepository.existsById(championshipId)){
            return ResponseEntity.notFound().build();
        }
        //Récupérer le championship
        Championship championship = championShipRepository.findById(championshipId).orElse(null);

        //Récupérer les équipes associées au championship
        List<Team> teams = championship.getTeams();

        return ResponseEntity.ok(teams);
    }

    //Create team
    @PostMapping(value = "/")
    public ResponseEntity<Team> createTeam (@Valid @RequestBody Team team, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        } else {
            teamRepository.save(team);
            return new ResponseEntity<>(team, HttpStatus.CREATED);
        }
    }

    //Add team to a championship
    /*@PostMapping(value = "/add/{championshipId}")
    public ResponseEntity<String> addTeamToChampionship(@PathVariable Long championshipId, @RequestBody Team team){
        //Vérifier si championship exist
        if(!championShipRepository.existsById(championshipId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Championship non trouvé");
        }
        //Récupérer le championship
        Championship championship = championShipRepository.findById(championshipId).orElse(null);

        //Vérifier si team exist
        if(!teamRepository.existsById(team.getId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team non trouvée");
        }

        //Ajouter team à championship
        championship.getTeams().add(team);

        //Save
        championShipRepository.save(championship);

        return ResponseEntity.status(HttpStatus.OK).body("Team ajoutée au championship");
    }*/

    //Add team to a championship
    @PostMapping("/{teamId}/add/")
    public ResponseEntity<String> addTeamToChampionship(@PathVariable("teamId") Long teamId, @RequestParam("championshipId") Long championshipId) {
        // Vérifie si championship et team existent
        if (!championShipRepository.existsById(championshipId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Championship non trouvée");
        }
        if (!teamRepository.existsById(teamId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team non trouvée");
        }

        // Récupère le championship et team associés
        Championship championship = championShipRepository.findById(championshipId).orElse(null);
        Team team = teamRepository.findById(teamId).orElse(null);

        // Associer team et championship
        championship.getTeams().add(team);

        // Save
        championShipRepository.save(championship);

        return ResponseEntity.status(HttpStatus.CREATED).body("Team ajoutée au championship");
    }


    //Update team
    @PutMapping(value = "/{team}")
    public ResponseEntity<Team> updateTeam(@PathVariable(name = "team", required = false) Team team, @Valid @RequestBody Team teamUpdate, BindingResult bindingResult) {
        if (team == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Team introuvable"
            );
        } else {
            if (bindingResult.hasErrors()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
            } else {
                teamUpdate.setId(team.getId());
                teamRepository.save(teamUpdate);
                return new ResponseEntity<>(teamUpdate, HttpStatus.CREATED);
            }
        }
    }

    //Delete team
    @DeleteMapping(value = "/{team}")
    public void deleteTeam(@PathVariable(name = "team", required = false) Team team) {
        if (team == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Team introuvable"
            );
        } else {
            teamRepository.delete(team);
        }
    }
}
