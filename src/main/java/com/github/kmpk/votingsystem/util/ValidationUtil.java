package com.github.kmpk.votingsystem.util;

import com.github.kmpk.votingsystem.exception.EntityNotFoundException;
import com.github.kmpk.votingsystem.exception.IllegalRequestDataException;
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

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new EntityNotFoundException(String.format("Entity with %s is not found", msg));
        }
    }

    public static void assureIdConsistent(BaseTo baseTo, int id) {
        if (baseTo.isNew()) {
            baseTo.setId(id);
        } else if (baseTo.getId() != id) {
            throw new IllegalRequestDataException(String.format("%s must be with id=%d", baseTo, id));
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}
