package com.github.kmpk.votingsystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class MessageUtil {
    private final MessageSource messageSource;

    private static final Map<String, String> CONSTRAINS_MESSAGES = Map.of(
            "users_unique_email_idx", "User with this email already exists",
            "restaurants_address_idx", "Restaurant with this address already exists",
            "menus_unique_date_restaurant_idx", "This restaurant already have menu for this date",
            "votes_unique_user_datetime_idx", "User can't vote twice in the same time"
    );

    @Autowired
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(MessageSourceResolvable resolvable, String field) {
        String message = messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
        return String.format("%s: %s", field, message);
    }

    public Optional<String> getConstrainMessage(String exceptionMessage){
        return CONSTRAINS_MESSAGES.entrySet()
                .stream()
                .filter(entry -> exceptionMessage.contains(entry.getKey()))
                .findAny()
                .map(Map.Entry::getValue);
    }
}
