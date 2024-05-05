package com.application.test.services.utills;

import com.application.test.businessLogic.utills.ValidateUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidateUserTest {

    @Autowired
    private ValidateUser validateUser;

    @Test
    public void testIsUserOldEnough_userIsOldEnough() {
        LocalDate birthDate = LocalDate.now().minusYears(20);

        assertTrue(validateUser.isUserOldEnough(birthDate));
    }

    @Test
    public void testIsUserOldEnough_userIsNotOldEnough() {
        LocalDate birthDate = LocalDate.now().minusYears(15);

        assertFalse(validateUser.isUserOldEnough(birthDate));
    }

    @Test
    public void testIsUserOldEnough_nullBirthDate() {
        assertFalse(validateUser.isUserOldEnough(null));
    }

    @Test
    public void testIsEmailCorrect_validEmail() {
        String email = "test@example.com";

        assertTrue(validateUser.isEmailCorrect(email));
    }

    @Test
    public void testIsEmailCorrect_invalidEmail() {
        String email = "invalid_email";

        assertFalse(validateUser.isEmailCorrect(email));
    }

    @Test
    public void testIsEmailCorrect_nullEmail() {
        assertFalse(validateUser.isEmailCorrect(null));
    }

    @Test
    public void testIsPhoneNumberCorrect_validPhoneNumber() {
        String phoneNumber = "+1234567890123";

        assertTrue(validateUser.isPhoneNumberCorrect(phoneNumber));
    }

    @Test
    public void testIsPhoneNumberCorrect_invalidPhoneNumber() {
        String phoneNumber = "1234567890";

        assertFalse(validateUser.isPhoneNumberCorrect(phoneNumber));
    }

    @Test
    public void testIsPhoneNumberCorrect_nullPhoneNumber() {
        assertTrue(validateUser.isPhoneNumberCorrect(null));
    }
}
