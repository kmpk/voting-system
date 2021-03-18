package com.github.kmpk.votingsystem.exception;

import java.util.Arrays;

public class ExceptionInfo {
    private final String url;
    private final String message;
    private final String[] details;

    public ExceptionInfo(CharSequence url, String message, String... details) {
        this.url = url.toString();
        this.message = message;
        this.details = details;
    }

    @Override
    public String toString() {
        return "ExceptionInfo{" +
                "url='" + url + '\'' +
                ", message='" + message + '\'' +
                ", details=" + Arrays.toString(details) +
                '}';
    }
}