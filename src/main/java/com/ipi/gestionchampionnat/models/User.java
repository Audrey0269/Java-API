package com.ipi.gestionchampionnat.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le champ firstName ne peut pas etre null")
    @NotBlank(message = "Le champ firstName ne peut pas etre vide")
    private String firstName;

    @NotNull(message = "Le champ lastName ne peut pas etre null")
    @NotBlank(message = "Le champ lastName ne peut pas etre vide")
    private String lastName;

    @NotNull(message = "Le champ email ne peut pas etre null")
    @NotBlank(message = "Le champs email ne peut pas etre vide")
    private String email;

    @NotNull(message = "Le champ password ne peut pas etre null")
    @NotBlank(message = "Le champs password ne peut pas etre vide")
    private String password;

    @NotNull(message = "Le champ creationDate avis ne peut pas etre null")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyy-MM-dd")
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private LocalDate creationDate;

    public User(){

    }

    public User(String firstName, String lastName, String email, String password, LocalDate creationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
