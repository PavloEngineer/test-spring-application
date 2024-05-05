package com.application.test.repositories;

import com.application.test.data.entities.UserEntity;
import com.application.test.data.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {


    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetUsersByBirthDateRange_noUsersFound() {
        LocalDate beginDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);

        when(userRepository.getUsersByBirthDateRange(beginDate, endDate)).thenReturn(Optional.empty());

        Optional<List<UserEntity>> result = userRepository.getUsersByBirthDateRange(beginDate, endDate);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testGetUsersByBirthDateRange_usersFound() {
        LocalDate beginDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);

        List<UserEntity> userEntities = new ArrayList<>();

        when(userRepository.getUsersByBirthDateRange(beginDate, endDate)).thenReturn(Optional.of(userEntities));

        Optional<List<UserEntity>> expected = Optional.of(userEntities);

        Optional<List<UserEntity>> result = userRepository.getUsersByBirthDateRange(beginDate, endDate);

        assertEquals(expected, result);
    }
}
