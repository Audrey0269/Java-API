package com.ipi.gestionchampionnat.repository;

import com.ipi.gestionchampionnat.models.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {
    @Override
    List<Game> findAll();
    List<Game> findByDayChampionshipId(Long championshipId);
    List<Game> findByDayId(Long dayId);
}
