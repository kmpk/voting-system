package com.github.kmpk.votingsystem.exception;

public class IllegalVoteChangeTime extends RuntimeException {
    public IllegalVoteChangeTime(String message) {
        super(message);
    }
}
