package ru.rsreu.TrafficCodeServer.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.TrafficCodeServer.database.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}