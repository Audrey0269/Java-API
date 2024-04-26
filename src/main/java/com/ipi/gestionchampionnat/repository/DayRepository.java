package com.ipi.gestionchampionnat.repository;

import com.ipi.gestionchampionnat.models.Day;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DayRepository extends CrudRepository<Day, Long> {
    @Override
    List<Day> findAll();
}
