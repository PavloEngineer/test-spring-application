package com.application.test.data.repositories;

import com.application.test.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = "SELECT * FROM user WHERE birth_date >= :begin_date AND birth_date <= :end_date", nativeQuery = true)
    Optional<List<UserEntity>> getUsersByBirthDateRange(@Param("begin_date") LocalDate beginDate, @Param("end_date") LocalDate endDate);
}
