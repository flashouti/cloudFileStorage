package yurkov.cloudFileStorage.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yurkov.cloudFileStorage.adapter.repository.UserRepository;
import yurkov.cloudFileStorage.adapter.web.errors.AlreadyExistException;
import yurkov.cloudFileStorage.domain.storage.user.UserEntity;
import yurkov.cloudFileStorage.domain.storage.user.UserRole;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class RegistrationService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;


    public void registerUser(UserEntity userEntity) {
        String encodePass = passwordEncoder.encode(userEntity.getPassword());
        Optional<UserEntity> checkUsername = userRepository.findByUsername(userEntity.getUsername());
        if (checkUsername.isPresent()) {
            throw new AlreadyExistException("User");
        }

        UserEntity userEntityNew = new UserEntity(
                userEntity.getUsername(),
                encodePass);

        userEntityNew.setRole(UserRole.ROLE_USER);
        userRepository.save(userEntityNew);
    }
}
