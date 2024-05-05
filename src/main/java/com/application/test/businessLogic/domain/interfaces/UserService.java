package com.application.test.businessLogic.domain.interfaces;

import com.application.test.businessLogic.domain.models.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    boolean createUser(UserDto userDto);

    boolean deleteUser(int userId);

    boolean updateUser(UserDto userDto);

    boolean updateLastName(int userId, String lastName);

    List<UserDto> getUsersByBirthDateRange(LocalDate beginDate, LocalDate endDate);
}
