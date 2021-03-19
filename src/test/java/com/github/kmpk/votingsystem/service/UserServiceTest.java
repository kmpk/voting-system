package com.github.kmpk.votingsystem.service;

import com.github.kmpk.votingsystem.model.Role;
import com.github.kmpk.votingsystem.model.User;
import com.github.kmpk.votingsystem.to.UserAdminTo;
import com.github.kmpk.votingsystem.to.UserTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.github.kmpk.votingsystem.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest extends AbstractServiceTest {
    @Autowired
    private UserService service;

    @Test
    void get() {
        User admin = service.get(ADMIN_ID);
        assertMatch(admin, ADMIN);
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(RuntimeException.class, () -> service.get(0));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail("admin@admin.com");
        assertMatch(user, ADMIN);
    }

    @Test
    void updateUser() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setEmail("new@email.ru");
        updated.setPassword("newPassword");
        service.update(new UserTo(updated.getId(), updated.getName(), updated.getEmail(), updated.getPassword()));
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void updateAdmin() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setEmail("new@email.ru");
        updated.setPassword("newPassword");
        updated.setEnabled(false);
        updated.setRoles(Set.of(Role.ROLE_USER, Role.ROLE_ADMIN));
        service.update(new UserAdminTo(updated.getId(), updated.getName(), updated.getEmail(), updated.getPassword(), updated.isEnabled(), updated.getRoles()));
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertMatch(service.getAll(), ADMIN);
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(RuntimeException.class, () -> service.delete(0));
    }

    @Test
    void createDuplicateEmail() {
        Assertions.assertThrows(DataAccessException.class,
                () -> service.create(new User(null, "Duplicate", USER.getEmail(), "pass", true, Instant.now(), Set.of(Role.ROLE_USER))));
    }

    @Test
    void create() {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", false, Instant.now(), Collections.singleton(Role.ROLE_USER));
        User created = service.create(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(service.getAll(), USER, ADMIN, newUser);
    }

    @Test
    void getAll() {
        List<User> all = service.getAll();
        assertMatch(all, USER, ADMIN);
    }

    @Test
    void enable() {
        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }
}