package com.application.test.controllers;

import com.application.test.businessLogic.domain.interfaces.UserService;
import com.application.test.businessLogic.domain.models.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCreateUser_userCorrect_success() throws Exception {
        when(userService.createUser(any(UserDto.class))).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"userId\": 1,\n" +
                                "  \"email\": \"pavlo@example.com\",\n" +
                                "  \"firstName\": \"John\",\n" +
                                "  \"lastName\": \"Doe\",\n" +
                                "  \"birthDate\": \"1992-01-01\",\n" +
                                "  \"phoneNumber\": \"1234567890\"\n" +
                                "}"))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        verify(userService, times(1)).createUser(any(UserDto.class));
    }

    @Test
    public void testCreateUser_userIncorrect_failure() {
        UserDto userDto = new UserDto();

        when(userService.createUser(userDto)).thenReturn(false);

        ResponseEntity<Void> responseEntity = userController.createUser(userDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(userService, times(1)).createUser(userDto);
    }

    @Test
    public void testDeleteUser_userAvailable_success() throws Exception {
        int userId = 1;
        when(userService.deleteUser(userId)).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/users/1", userId))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void testDeleteUser_notFound() throws Exception {
        int userId = 1;
        when(userService.deleteUser(userId)).thenReturn(false);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/users/1", userId))
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void testUpdateUser_notFound() throws Exception {
        int userId = 1;
        UserDto userDto = new UserDto();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"userId\": 1,\n" +
                                "  \"email\": \"pavlo@example.com\",\n" +
                                "  \"firstName\": \"John\",\n" +
                                "  \"lastName\": \"Doe\",\n" +
                                "  \"birthDate\": \"1992-01-01\",\n" +
                                "  \"phoneNumber\": \"1234567890\"\n" +
                                "}"))
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void testUpdateLastName_success() throws Exception {
        int userId = 4;
        String lastName = "UTL";

        when(userService.updateLastName(userId, lastName)).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/users/{userId}/last-name", userId)
                        .param("lastName", lastName))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        verify(userService, times(1)).updateLastName(userId, lastName);
    }

    @Test
    public void testUpdateLastName_notFound() throws Exception {
        int userId = 1;
        String lastName = "Doe";

        when(userService.updateLastName(userId, lastName)).thenReturn(false);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/users/{userId}/last-name", userId)
                        .param("lastName", lastName))
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        verify(userService, times(1)).updateLastName(userId, lastName);
    }

    @Test
    public void testGetUsersByBirthDateRange_success() throws Exception {
        LocalDate beginDate = LocalDate.of(1990, 1, 1);
        LocalDate endDate = LocalDate.of(1991, 12, 31);

        List<UserDto> users = new ArrayList<>();
        when(userService.getUsersByBirthDateRange(beginDate, endDate)).thenReturn(users);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/birth-date")
                        .param("beginDate", beginDate.toString())
                        .param("endDate", endDate.toString()))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        verify(userService, times(1)).getUsersByBirthDateRange(beginDate, endDate);
    }

    @Test
    public void testGetUsersByBirthDateRange_invalidDates() throws Exception {
        LocalDate beginDate = LocalDate.of(1990, 1, 1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/birth-date")
                        .param("beginDate", beginDate.toString())
                        .param("endDate", "fff"))
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }
}
