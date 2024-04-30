package com.ipi.gestionchampionnat.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le champ name ne peut pas etre null")
    @NotBlank(message = "Le champs name ne peut pas etre vide")
    private String name;

    @NotNull(message = "Le champ creationDate avis ne peut pas etre null")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyy-MM-dd")
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private LocalDate creationDate;

    @OneToMany(mappedBy="team1", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Game> gameTeam1;

    @OneToMany(mappedBy="team2", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Game> gameTeam2;

    @ManyToMany (mappedBy = "teams")
    @JsonIgnore
    private List<Championship> championships = new ArrayList<>();

    public Team(){

    }

    public Team(String name, LocalDate creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<Game> getGameTeam1() {
        return gameTeam1;
    }

    public void setGameTeam1(List<Game> gameTeam1) {
        this.gameTeam1 = gameTeam1;
    }

    public List<Game> getGameTeam2() {
        return gameTeam2;
    }

    public void setGameTeam2(List<Game> gameTeam2) {
        this.gameTeam2 = gameTeam2;
    }

    public List<Championship> getChampionships() {
        return championships;
    }

    public void setTeams(List<Championship> championships) {
        this.championships = championships;
    }
}
