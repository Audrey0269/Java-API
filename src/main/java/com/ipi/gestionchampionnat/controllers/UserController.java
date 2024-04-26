package com.ipi.gestionchampionnat.controllers;

import com.ipi.gestionchampionnat.models.User;
import com.ipi.gestionchampionnat.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //Get All users
    @GetMapping("/")
    public List<User> getAll(){
        return userRepository.findAll();
    }

    //Get one user by id
    @GetMapping("/{user}")
    public User getOne(@PathVariable(name = "user", required = false) User user){
        if (user == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User introuvable"
            );
        }
        return user;
    }

    //Get one user by email and password
    @GetMapping("/email/{email}/password/{password}")
    public List<User> getFromEmailAndPassword(@PathVariable(name = "email", required = false) String email, @PathVariable(name = "password", required = false) String password){
        return userRepository.getUserFromEmailAndPassword(email, password);
    }

    //Create user
    @PostMapping(value = "/")
    public ResponseEntity<User> createUser (@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        } else {
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    //Update user
    @PutMapping(value = "/{user}")
    public ResponseEntity<User> updateUser(@PathVariable(name = "user", required = false) User user, @Valid @RequestBody User userUpdate, BindingResult bindingResult) {
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User introuvable"
            );
        } else {
            if (bindingResult.hasErrors()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
            } else {
                userUpdate.setId(user.getId());
                userRepository.save(userUpdate);
                return new ResponseEntity<>(userUpdate, HttpStatus.CREATED);
            }
        }
    }

    //Delete user
    @DeleteMapping(value = "/{user}")
    public void deleteUser(@PathVariable(name = "user", required = false) User user) {
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User introuvable"
            );
        } else {
            userRepository.delete(user);
        }
    }

}
