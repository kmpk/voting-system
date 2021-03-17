package com.github.kmpk.votingsystem.service;

import com.github.kmpk.votingsystem.AuthorizedUser;
import com.github.kmpk.votingsystem.model.User;
import com.github.kmpk.votingsystem.repository.UserRepository;
import com.github.kmpk.votingsystem.to.UserTo;
import com.github.kmpk.votingsystem.util.ValidationUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.github.kmpk.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        Assert.notNull(user, "User must not be null");
        return repository.save(prepareToSave(user));
    }

    private User prepareToSave(User user) {
        String password = user.getPassword();
        user.setPassword(ObjectUtils.isEmpty(password) ? password : passwordEncoder.encode(password));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    @Override
    public User get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(prepareToSave(user)), user.getId());
    }

    @Override
    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        repository.save(prepareToSave(user));
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) == 1, id);
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = repository.getByEmail(email.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", email)));
        return new AuthorizedUser(user);
    }

    @Override
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return ValidationUtil.checkNotFound(repository.getByEmail(email).orElse(null), "email=" + email);
    }

    @Override
    @Transactional
    public void enable(int id, boolean enable) {
        User user = get(id);
        user.setEnabled(enable);
    }
}
