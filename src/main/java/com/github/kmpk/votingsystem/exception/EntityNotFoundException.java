package com.github.kmpk.votingsystem.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String mesage) {
        super(mesage);
    }
}