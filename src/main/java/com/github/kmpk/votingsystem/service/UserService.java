package com.github.kmpk.votingsystem.service;

import com.github.kmpk.votingsystem.model.User;
import com.github.kmpk.votingsystem.to.UserAdminTo;
import com.github.kmpk.votingsystem.to.UserTo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    User create(User user);

    User get(int id);

    @Transactional
    void update(UserTo userTo);

    @Transactional
    void update(UserAdminTo userTo);

    void delete(int id);

    List<User> getAll();

    User getByEmail(String email);

    void enable(int id, boolean enable);
}
