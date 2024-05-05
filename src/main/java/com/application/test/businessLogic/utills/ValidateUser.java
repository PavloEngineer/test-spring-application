package com.application.test.businessLogic.utills;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidateUser {

    @Value("${user.minAge}")
    private int minAge;

    public boolean isUserOldEnough(LocalDate birthDate) {
        if (birthDate != null) {
            Period period = Period.between(birthDate, LocalDate.now());
            int age = period.getYears();

            return age >= minAge;
        }

        return false;
    }

    public boolean isEmailCorrect(String email) {
        if (email != null) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
        return false;
    }

    public boolean isPhoneNumberCorrect(String phoneNumber) {
        if (phoneNumber != null) {

            // Regular expression to check phone number format
            String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phoneNumber);

            return matcher.matches();
        }
        return true;
    }
}
