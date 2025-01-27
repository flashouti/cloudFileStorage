package yurkov.cloudFileStorage.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yurkov.cloudFileStorage.adapter.repository.UserRepository;
import yurkov.cloudFileStorage.adapter.web.dto.request.UserRegistrationRequest;
import yurkov.cloudFileStorage.adapter.web.errors.AlreadyExistException;
import yurkov.cloudFileStorage.domain.user.UserEntity;
import yurkov.cloudFileStorage.domain.user.UserRole;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class RegistrationService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;


    public void registerUser(UserRegistrationRequest registrationRequest) {
        String encodePass = passwordEncoder.encode(registrationRequest.password());
        Optional<UserEntity> checkUsername = userRepository.findByUsername(registrationRequest.username());
        if (checkUsername.isPresent()) {
            throw new AlreadyExistException("User");
        }

        UserEntity userEntityNew = new UserEntity(
                registrationRequest.username(),
                encodePass);

        userEntityNew.setRole(UserRole.ROLE_USER);
        userRepository.save(userEntityNew);
    }
}
