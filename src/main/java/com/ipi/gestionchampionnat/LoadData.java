package com.ipi.gestionchampionnat;

import com.ipi.gestionchampionnat.models.*;
import com.ipi.gestionchampionnat.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class LoadData {

    private final Logger log = LoggerFactory.getLogger(LoadData.class);

    @Bean
    public CommandLineRunner initDatabase(DayRepository dayRepository,
                                   ChampionShipRepository championShipRepository,
                                   GameRepository gameRepository,
                                   TeamRepository teamRepository,
                                   UserRepository userRepository) {
        return args -> {
            log.info("chargement des données");

            if (userRepository.count() == 0) {

                //___USER___
                //Créer un objet creationDate
                LocalDate creationDateUser = LocalDate.parse("2024-04-25");
                //Créer un user
                User user1 = new User("Audrey", "Gaudilliere", "audrey.gaudilliere@ipi.com", "123", creationDateUser);
                //Enregistrer le jeu dans la BDD
                userRepository.save(user1);

                //___TEAM___
                //Créer un objet creationDate
                LocalDate creationDateTeam = LocalDate.parse("2024-04-20");
                //Créer une team
                Team team1 = new Team("Team Bleu", creationDateTeam);
                Team team2 = new Team("Team Rouge", creationDateTeam);
                //Enregistrer la team dans la BDD
                teamRepository.save(team1);
                teamRepository.save(team2);

                //___CHAMPIONSHIP___
                //Créer un objet creationDate
                LocalDate startDate = LocalDate.parse("2024-05-20");
                //Créer un objet creationDate
                LocalDate endDate = LocalDate.parse("2024-06-20");
                //Créer un championShip
                Championship championship1 = new Championship("ChampionShip One", startDate, endDate, 30L, 9L, 4L);
                //Associer les équipes au championnat
                championship1.getTeams().add(team1);
                championship1.getTeams().add(team2);
                //Enregistrer championShip dans la BDD
                championShipRepository.save(championship1);

                //___DAY___L
                //Créer un day
                Day day1 = new Day("First Day", championship1);
                //Enregistrer day dans la BDD
                dayRepository.save(day1);

                // Charger les équipes et le jour à partir de la base de données
                Team loadedTeam1 = teamRepository.findById(team1.getId()).orElseThrow(() -> new RuntimeException("Équipe non trouvée"));
                Team loadedTeam2 = teamRepository.findById(team2.getId()).orElseThrow(() -> new RuntimeException("Équipe non trouvée"));
                Day loadedDay1 = dayRepository.findById(day1.getId()).orElseThrow(() -> new RuntimeException("Jour non trouvé"));

                //___GAME___
                //Créer une game
                Game game1 = new Game(1L, 3L, loadedDay1, loadedTeam1, loadedTeam2);
                //Enregistrer le jeu dans la BDD
                gameRepository.save(game1);


                log.info("Données chargées avec succès");

            } else {
                log.info("Données déja chargées");
            }
        };
    }
}
