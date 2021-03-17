package com.github.kmpk.votingsystem.util;

import com.github.kmpk.votingsystem.to.BaseTo;

public class ValidationUtil {

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String arg) {
        if (!found) {
            throw new RuntimeException(arg);
        }
    }

    public static void assureIdConsistent(BaseTo baseTo, int id) {
        if (baseTo.isNew()) {
            baseTo.setId(id);
        } else if (baseTo.getId() != id) {
            throw new RuntimeException(baseTo + " must be with id=" + id);
        }
    }
}
