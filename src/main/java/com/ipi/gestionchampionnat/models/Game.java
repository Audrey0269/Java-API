package com.ipi.gestionchampionnat.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le champ team1Point ne peut pas etre null")
    @Min(value = 0, message = "La team1Point ne peut pas être inférieur à zéro")
    private Long team1Point;

    @NotNull(message = "Le champ team2Point ne peut pas etre null")
    @Min(value = 0, message = "La team2Point ne peut pas être inférieur à zéro")
    private Long team2Point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDay")
    @JsonIgnore //
    private Day day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTeam1")
    @JsonIgnore
    private Team team1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTeam2")
    @JsonIgnore
    private Team team2;

    public Game(){

    }

    public Game(Long team1Point, Long team2Point, Day day, Team team1, Team team2) {
        this.team1Point = team1Point;
        this.team2Point = team2Point;
        this.day = day;
        this.team1 = team1;
        this.team2 = team2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeam1Point() {
        return team1Point;
    }

    public void setTeam1Point(Long team1Point) {
        this.team1Point = team1Point;
    }

    public Long getTeam2Point() {
        return team2Point;
    }

    public void setTeam2Point(Long team2Point) {
        this.team2Point = team2Point;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }
}
