package com.ipi.gestionchampionnat.repository;

import com.ipi.gestionchampionnat.models.Championship;
import com.ipi.gestionchampionnat.models.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {
    @Override
    List<Team> findAll();
}
