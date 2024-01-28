package org.adaschool.api.controller.user;

import org.adaschool.api.exception.UserIDNotDetectedException;
import org.adaschool.api.exception.UserNotFoundException;
import org.adaschool.api.repository.user.User;
import org.adaschool.api.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/users/")
public class UsersController {

    private final UsersService usersService;

    public UsersController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User userAdded = usersService.save(user);
            URI createdUserUri = URI.create(user.getId());
            return ResponseEntity.created(createdUserUri).body(userAdded);
        } catch (UserIDNotDetectedException e) {
            return ResponseEntity.badRequest().body(user);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(usersService.all());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(usersService.findById(id).get());
        } catch(NoSuchElementException e) {
            throw new UserNotFoundException(id);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        if (usersService.findById(id).isPresent()) {
            usersService.update(user, id);
            return ResponseEntity.ok(null);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        if (usersService.findById(id).isPresent()) {
            usersService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            throw new UserNotFoundException(id);
        }
    }
}
