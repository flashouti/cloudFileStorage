package yurkov.cloudFileStorage.util;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import yurkov.cloudFileStorage.adapter.repository.UserRepository;
import yurkov.cloudFileStorage.adapter.web.dto.request.UserRegistrationRequest;
import yurkov.cloudFileStorage.domain.storage.user.UserEntity;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationRequest request = (UserRegistrationRequest) target;
        if (userRepository.findByUsername(request.username()).isPresent()) {
            errors.rejectValue("username", "", "User with the same name already exists");
        }


    }
}
