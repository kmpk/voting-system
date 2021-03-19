package com.github.kmpk.votingsystem;

import com.github.kmpk.votingsystem.model.Role;
import com.github.kmpk.votingsystem.model.User;
import com.github.kmpk.votingsystem.web.TestUtil;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static com.github.kmpk.votingsystem.model.AbstractBaseEntity.START_SEQ;
import static com.github.kmpk.votingsystem.web.JsonUtil.readListFromJsonMvcResult;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "user", "user@user.ru", "password", true, Instant.now(), Set.of(Role.ROLE_USER));
    public static final User ADMIN = new User(ADMIN_ID, "admin", "admin@admin.com", "admin", true, Instant.now(), Set.of(Role.ROLE_USER, Role.ROLE_ADMIN));

    public static void assertMatch(User actual, User expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("registered", "password", "votes")
                .isEqualTo(expected);
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "password").isEqualTo(expected);
    }

    public static ResultMatcher getUserMatcher(User expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, User.class), expected);
    }

    public static ResultMatcher getUserMatcher(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }
}
