package com.application.test.services;

import com.application.test.businessLogic.domain.interfaces.UserService;
import com.application.test.businessLogic.domain.models.UserDto;
import com.application.test.data.entities.UserEntity;
import com.application.test.data.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser_validUser() {
        UserDto userDto = new UserDto("opa@gmail.com","Maks", "SDF", LocalDate.of(1987, 3, 12), "+0943453421");

        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());

        assertTrue(userService.createUser(userDto));

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testCreateUser_invalidUser() {
        UserDto userDto = new UserDto();

        assertFalse(userService.createUser(userDto));

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testDeleteUser_userExists() {
        int userId = 4;
        when(userRepository.existsById(userId)).thenReturn(true);

        assertTrue(userService.deleteUser(userId));

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUser_userDoesNotExist() {
        int userId = 100;
        when(userRepository.existsById(userId)).thenReturn(false);

        assertFalse(userService.deleteUser(userId));

        verify(userRepository, never()).deleteById(userId);
    }

    @Test
    public void testUpdateUser_validUser() {
        UserDto userDto = new UserDto("opa@gmail.com","Maks", "SDF", LocalDate.of(1987, 3, 12), "+0943453421");

        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());

        assertTrue(userService.updateUser(userDto));

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testUpdateUser_invalidUser() {
        UserDto userDto = new UserDto();

        assertFalse(userService.updateUser(userDto));

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testUpdateLastName_userExists() {
        int userId = 4;
        String newLastName = "Doe";
        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserEntity()));

        assertTrue(userService.updateLastName(userId, newLastName));

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testUpdateLastName_userDoesNotExist() {
        int userId = 1000;
        String newLastName = "Doe";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertFalse(userService.updateLastName(userId, newLastName));

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUsersByBirthDateRange_invalidDates() {
        LocalDate beginDate = LocalDate.of(2022, 5, 1);
        LocalDate endDate = LocalDate.of(2022, 4, 1);

        userService.getUsersByBirthDateRange(beginDate, endDate);
    }

    @Test
    public void testGetUsersByBirthDateRange_noUsersFound() {
        LocalDate beginDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);

        when(userRepository.getUsersByBirthDateRange(beginDate, endDate)).thenReturn(Optional.empty());

        List<UserDto> result = userService.getUsersByBirthDateRange(beginDate, endDate);

        assertEquals(0, result.size());
    }

    @Test
    public void testGetUsersByBirthDateRange_usersFound() {
        LocalDate beginDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);

        List<UserEntity> userEntities = new ArrayList<>();

        when(userRepository.getUsersByBirthDateRange(beginDate, endDate)).thenReturn(Optional.of(userEntities));

        List<UserDto> expected = new ArrayList<>();


        List<UserDto> result = userService.getUsersByBirthDateRange(beginDate, endDate);

        assertEquals(expected, result);
    }
}
