package com.ipi.gestionchampionnat.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le champ number ne peut pas etre null")
    @NotBlank(message = "Le champs number ne peut pas etre vide")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idChampionship")
    @JsonIgnore
    private Championship championship;

    @OneToMany(mappedBy="day", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Game> games;

    public Day(){

    }

    public Day(String number, Championship championship) {
        this.number = number;
        this.championship = championship;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
