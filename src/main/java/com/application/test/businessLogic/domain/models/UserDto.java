package com.application.test.businessLogic.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDto {

    private int userId;

    private String email;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String phoneNumber;

    public UserDto(String email, String firstName, String lastName, LocalDate birthDate, String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }
}
