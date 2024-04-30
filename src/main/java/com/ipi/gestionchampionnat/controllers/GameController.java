package com.ipi.gestionchampionnat.controllers;

import com.ipi.gestionchampionnat.models.Day;
import com.ipi.gestionchampionnat.models.Game;
import com.ipi.gestionchampionnat.models.Team;
import com.ipi.gestionchampionnat.models.User;
import com.ipi.gestionchampionnat.repository.DayRepository;
import com.ipi.gestionchampionnat.repository.GameRepository;
import com.ipi.gestionchampionnat.repository.TeamRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
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
    @PostMapping(value = "/day/{dayId}/team1/{team1Id}/team2/{team2Id}")
    public ResponseEntity<String> createGameForDay(@PathVariable Long dayId, @PathVariable Long team1Id, @PathVariable Long team2Id, @RequestBody Game game) {
        // Vérifier si day existe
        if (!dayRepository.existsById(dayId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Day non trouvée");
        }

        // Vérifier si team existent
        if (!teamRepository.existsById(team1Id) || !teamRepository.existsById(team2Id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team non trouvées");
        }

        // Récupérer day et team associées
        Day day = dayRepository.findById(dayId).orElse(null);
        Team team1 = teamRepository.findById(team1Id).orElse(null);
        Team team2 = teamRepository.findById(team2Id).orElse(null);

        // Associer day et team au game
        game.setDay(day);
        game.setTeam1(team1);
        game.setTeam2(team2);

        // Save
        gameRepository.save(game);

        return ResponseEntity.status(HttpStatus.CREATED).body("Game créée");
    }


    //Update game
    @PutMapping("/{gameId}")
    public ResponseEntity<Game> updateGame(@PathVariable("gameId") Long id, @RequestBody Game gameUpdate) {
        Game existingGame = gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game inexistant"));

        // Mise à jour des données
        existingGame.setTeam1Point(gameUpdate.getTeam1Point());
        existingGame.setTeam2Point(gameUpdate.getTeam2Point());

        // Save game
        Game updatedGame = gameRepository.save(existingGame);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
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
