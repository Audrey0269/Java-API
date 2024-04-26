package com.ipi.gestionchampionnat.controllers;

import com.ipi.gestionchampionnat.models.Day;
import com.ipi.gestionchampionnat.models.Game;
import com.ipi.gestionchampionnat.models.Team;
import com.ipi.gestionchampionnat.models.User;
import com.ipi.gestionchampionnat.repository.DayRepository;
import com.ipi.gestionchampionnat.repository.GameRepository;
import com.ipi.gestionchampionnat.repository.TeamRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/games")
public class GameController {

    private GameRepository gameRepository;
    private DayRepository dayRepository;
    private TeamRepository teamRepository;

    public GameController(GameRepository gameRepository,
                          DayRepository dayRepository,
                          TeamRepository teamRepository){
        this.gameRepository = gameRepository;
        this.dayRepository = dayRepository;
        this.teamRepository = teamRepository;
    }

    //Get All games
    @GetMapping("/")
    public List<Game> getAll(){
        return gameRepository.findAll();
    }

    //Get one game by id
    @GetMapping("/{game}")
    public Game getOne(@PathVariable(name = "game", required = false) Game game){
        if (game == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Game introuvable"
            );
        }
        return game;
    }

    //Get game by championshipId
    @GetMapping("/championship/{championshipId}")
    public ResponseEntity<List<Game>> getResultsByChampionshipId(@PathVariable Long championshipId) {
        // Récupérer la liste des game associés à un championship
        List<Game> games = gameRepository.findByDayChampionshipId(championshipId);

        // Vérifier si des jeux ont été trouvés pour ce championnat
        if (games.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // Retourner la liste des jeux
        return ResponseEntity.ok(games);
    }

    //Get Game by day
    @GetMapping("/day/{dayId}")
    public ResponseEntity<List<Game>> getResultsByDayId(@PathVariable Long dayId) {
        // Récupérer la liste des game associés à un day
        List<Game> games = gameRepository.findByDayId(dayId);

        // Vérifier si des jeux ont été trouvés pour ce championnat
        if (games.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // Retourner la liste des jeux
        return ResponseEntity.ok(games);
    }


    //Create game for day
    @PostMapping(value = "/{dayId}")
    public ResponseEntity<String> createGameForDay(@PathVariable Long dayId, @RequestBody Game game) {
        // Vérifie si la journée existe
        if (!dayRepository.existsById(dayId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Day non trouvée");
        }

        // Récupérer day
        Day day = dayRepository.findById(dayId).orElse(null);

        // Associer la journée au jeu
        game.setDay(day);

        // Sauvegarder le résultat de jeu dans la base de données
        gameRepository.save(game);

        return ResponseEntity.status(HttpStatus.CREATED).body("Game créée");
    }


    //Update game
    @PutMapping(value = "/{game}")
    public ResponseEntity<Game> updateGame(@PathVariable(name = "game", required = false) Game game, @Valid @RequestBody Game gameUpdate, BindingResult bindingResult) {
        if (game == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Game introuvable"
            );
        } else {
            if (bindingResult.hasErrors()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
            } else {
                gameUpdate.setId(game.getId());
                gameRepository.save(gameUpdate);
                return new ResponseEntity<>(gameUpdate, HttpStatus.CREATED);
            }
        }
    }

    //Delete user
    @DeleteMapping(value = "/{game}")
    public void deleteGame(@PathVariable(name = "game", required = false) Game game) {
        if (game == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Game introuvable"
            );
        } else {
            gameRepository.delete(game);
        }
    }

}
