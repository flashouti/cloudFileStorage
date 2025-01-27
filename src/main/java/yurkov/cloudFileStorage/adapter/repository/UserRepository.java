package yurkov.cloudFileStorage.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yurkov.cloudFileStorage.domain.user.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
}
