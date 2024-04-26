package com.ipi.gestionchampionnat.repository;

import com.ipi.gestionchampionnat.models.Championship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChampionShipRepository extends CrudRepository<Championship, Long>  {
    @Override
    List<Championship> findAll();

}
