package yurkov.cloudFileStorage.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yurkov.cloudFileStorage.adapter.repository.UserRepository;
import yurkov.cloudFileStorage.adapter.web.errors.UserNotFoundException;
import yurkov.cloudFileStorage.domain.user.UserEntity;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class PersonDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public PersonDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> person = userRepository.findByUsername(username);

        if (person.isEmpty())
            throw new UserNotFoundException(username);

        return (UserDetails) person.get();
    }
}
