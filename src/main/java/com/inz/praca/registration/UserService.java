package com.inz.praca.registration;

import com.inz.praca.aspect.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Slf4j
public class UserService {
    private static final int MAX_USERS_ON_PAGE = 20;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Timed
    public User getUserById(Long id) {
        Assert.notNull(id, "Podano puste id uzytkownika");
        Assert.isTrue(id > 0L, "Nie ma uzytkownikow o id mniejszym niz 1");
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Timed
    public User getUserByEmail(String email) {
        Assert.notNull(email, "Nie podano adresu email");
        Assert.hasLength(email, "Podano pusty email");
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Timed
    public Page<User> getAllUsers(Integer page) {
        PageRequest pageRequest = new PageRequest(page, UserService.MAX_USERS_ON_PAGE);
        return userRepository.findAll(pageRequest);
    }

    public User createUserFromDTO(UserDTO form) {
        User user = new UserBuilder().build(form);
        userRepository.save(user);
        UserService.log.info("Stworzono uzytkownika o id {}", user.getId());
        return user;
    }

    public String changeUserActive(Long id, Boolean activity) {
        User user = getUserById(id);
        if (activity)
            user.active();
        else
            user.deactivate();
        userRepository.save(user);
        return String.format(user.isActivated() ? "Aktywowano uzytkownika %s" : "Deaktywowano uzytkownika %s",
                user.getEmail());
    }
}
