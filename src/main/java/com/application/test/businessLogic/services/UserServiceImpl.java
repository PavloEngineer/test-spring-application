package com.application.test.businessLogic.services;

import com.application.test.businessLogic.domain.interfaces.UserService;
import com.application.test.businessLogic.mapper.Mapper;
import com.application.test.businessLogic.domain.models.UserDto;
import com.application.test.businessLogic.utills.ValidateUser;
import com.application.test.data.entities.UserEntity;
import com.application.test.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private ValidateUser validateUser;

    /**
     * Creates a new user based on the provided UserDto object.
     * @param userDto The UserDto object representing the data of the new user.
     * @return true if the user is successfully created; false if creation fails.
     */
    @Override
    @Async
    public boolean createUser(UserDto userDto) {
        if (isUserValid(userDto)) {
            userRepository.save(mapper.map(userDto, UserEntity.class));
            return true;
        }
        return false;
    }

    /**
     * Deletes a user based on their identifier.
     * @param userId The identifier of the user to be deleted.
     * @return true if the user is successfully deleted; false if deletion fails.
     */
    @Override
    @Async
    public boolean deleteUser(int userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    /**
     * Updates user information based on the provided UserDto object.
     * @param userDto The UserDto object containing the new data for updating.
     * @return true if the user is successfully updated; false if update fails.
     */
    @Override
    @Async
    public boolean updateUser(UserDto userDto) {
        if (isUserValid(userDto)) {
            userRepository.save(mapper.map(userDto, UserEntity.class));
            return true;
        }
        return false;
    }


    /**
     * Updates the last name of a user identified by userId.
     * @param userId The identifier of the user whose last name will be updated.
     * @param lastName The new last name to be set for the user.
     * @return true if the last name is successfully updated; false if update fails.
     */
    @Override
    @Async
    public boolean updateLastName(int userId, String lastName) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();
            user.setLastName(lastName);

            userRepository.save(user);
            return true;
        }

        return false;
    }


    /**
     * Retrieves a list of users within the specified birth date range.
     * @param beginDate The start date of the birth date range.
     * @param endDate The end date of the birth date range.
     * @return A list of UserDto objects representing users within the specified range.
     * @throws IllegalArgumentException if beginDate or endDate is null or if beginDate is after endDate.
     */
    @Override
    @Async
    public List<UserDto> getUsersByBirthDateRange(LocalDate beginDate, LocalDate endDate) {
        if (beginDate != null && endDate != null && (beginDate.isBefore(endDate) || beginDate.isEqual(endDate))) {
            List<UserEntity> userEntities = userRepository.getUsersByBirthDateRange(beginDate, endDate).orElse(new ArrayList<>());
            return mapper.map(userEntities, UserDto.class);
        }

        throw new IllegalArgumentException();
    }

    private boolean isUserValid(UserDto userDto) {
        return validateUser.isUserOldEnough(userDto.getBirthDate()) && validateUser.isPhoneNumberCorrect(userDto.getPhoneNumber())
                && validateUser.isEmailCorrect(userDto.getEmail());
    }
}
