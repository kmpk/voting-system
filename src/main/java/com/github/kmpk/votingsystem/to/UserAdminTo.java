package com.github.kmpk.votingsystem.to;

import com.github.kmpk.votingsystem.model.Role;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class UserAdminTo extends UserTo {

    @NotNull
    private boolean enabled = true;

    private Set<Role> roles = Set.of(Role.ROLE_USER);

    public UserAdminTo() {
    }

    public UserAdminTo(Integer id, String name, String email, String password, boolean enabled, Set<Role> roles) {
        super(id, name, email, password);
        this.enabled = enabled;
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserAdminTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
