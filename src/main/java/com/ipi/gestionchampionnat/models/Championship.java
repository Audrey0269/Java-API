package com.ipi.gestionchampionnat.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Championship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le champ name ne peut pas etre null")
    @NotBlank(message = "Le champs name ne peut pas etre vide")
    private String name;

    @NotNull(message = "Le champ startDate avis ne peut pas etre null")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyy-MM-dd")
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Le champ endDate avis ne peut pas etre null")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyy-MM-dd")
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Le champ wonPoint ne peut pas etre null")
    @Min(value = 0, message = "La wonPoint ne peut pas être inférieur à zéro")
    private Long wonPoint;

    @NotNull(message = "Le champ lostPoint ne peut pas etre null")
    @Min(value = 0, message = "La lostPoint ne peut pas être inférieur à zéro")
    private Long lostPoint;

    @NotNull(message = "Le champ drawPoint ne peut pas etre null")
    @Min(value = 0, message = "La drawPoint ne peut pas être inférieur à zéro")
    private Long drawPoint;

    @OneToMany(mappedBy="championship", fetch = FetchType.LAZY)
    @JsonIgnore //
    private List<Day> day;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(
            name = "teamChampionship",
            joinColumns =  @JoinColumn(name = "idChampionship"),
            inverseJoinColumns = @JoinColumn(name = "idTeam")
    )
    @JsonIgnore
    private List<Team> teams = new ArrayList<>();

    public Championship(){

    }

    public Championship(String name, LocalDate startDate, LocalDate endDate, Long wonPoint, Long lostPoint, Long drawPoint) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.wonPoint = wonPoint;
        this.lostPoint = lostPoint;
        this.drawPoint = drawPoint;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getWonPoint() {
        return wonPoint;
    }

    public void setWonPoint(Long wonPoint) {
        this.wonPoint = wonPoint;
    }

    public Long getLostPoint() {
        return lostPoint;
    }

    public void setLostPoint(Long lostPoint) {
        this.lostPoint = lostPoint;
    }

    public Long getDrawPoint() {
        return drawPoint;
    }

    public void setDrawPoint(Long drawPoint) {
        this.drawPoint = drawPoint;
    }

    public List<Day> getDay() {
        return day;
    }

    public void setDay(List<Day> day) {
        this.day = day;
    }


    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
