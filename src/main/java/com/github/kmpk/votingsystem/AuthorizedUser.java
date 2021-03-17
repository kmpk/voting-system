package com.github.kmpk.votingsystem;

import com.github.kmpk.votingsystem.model.User;
import com.github.kmpk.votingsystem.to.UserTo;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        userTo = new UserTo(user.getId(), user.getName(),user.getEmail(),user.getPassword());
    }

    public int getId() {
        return userTo.getId();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}
