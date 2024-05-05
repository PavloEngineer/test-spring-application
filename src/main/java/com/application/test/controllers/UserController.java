package com.application.test.controllers;

import com.application.test.businessLogic.domain.interfaces.UserService;
import com.application.test.businessLogic.domain.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Async
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        if (userService.createUser(userDto)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    @Async
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        if (userService.deleteUser(userId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}")
    @Async
    public ResponseEntity<Void> updateUser(@PathVariable int userId, @RequestBody UserDto userDto) {
        if (userService.updateUser(userDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{userId}/last-name")
    @Async
    public ResponseEntity<Void> updateLastName(@PathVariable int userId, @RequestParam String lastName) {
        if (userService.updateLastName(userId, lastName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/birth-date")
    @Async
    public ResponseEntity<List<UserDto>> getUsersByBirthDateRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<UserDto> users = userService.getUsersByBirthDateRange(beginDate, endDate);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
