package com.github.kmpk.votingsystem.web;

import com.github.kmpk.votingsystem.exception.EntityNotFoundException;
import com.github.kmpk.votingsystem.exception.ExceptionInfo;
import com.github.kmpk.votingsystem.exception.IllegalRequestDataException;
import com.github.kmpk.votingsystem.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @Autowired
    private MessageUtil messageUtil;

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ExceptionInfo validationError(HttpServletRequest req, Exception e) {
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors();
        String[] details = fieldErrors.stream()
                .map(fe -> messageUtil.getMessage(fe, fe.getField()))
                .toArray(String[]::new);
        ExceptionInfo exceptionInfo = new ExceptionInfo(req.getRequestURL(), "Validation error", details);
        logger.warn("Validation error: {}", exceptionInfo);
        return exceptionInfo;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ExceptionInfo entityNotFoundError(HttpServletRequest req, EntityNotFoundException e) {
        ExceptionInfo exceptionInfo = new ExceptionInfo(req.getRequestURL(), "Entity not found", e.getMessage());
        logger.warn("Entity not found: {}", exceptionInfo);
        return exceptionInfo;
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IllegalRequestDataException.class)
    public ExceptionInfo illegalRequestDataError(HttpServletRequest req, IllegalRequestDataException e) {
        ExceptionInfo exceptionInfo = new ExceptionInfo(req.getRequestURL(), "Illegal request data", e.getMessage());
        logger.warn("Illegal request data: {}", exceptionInfo);
        return exceptionInfo;
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ExceptionInfo conflictDataError(HttpServletRequest req, Exception e) {
        String message = ValidationUtil.getRootCause(e).getMessage().toLowerCase();
        String constrainMessage = messageUtil.getConstrainMessage(message).orElseThrow(() -> new RuntimeException("Data error"));
        ExceptionInfo exceptionInfo = new ExceptionInfo(req.getRequestURL(), "Data conflict error", constrainMessage);
        logger.warn("Data conflict error: {}", exceptionInfo);
        return exceptionInfo;
    }
}
