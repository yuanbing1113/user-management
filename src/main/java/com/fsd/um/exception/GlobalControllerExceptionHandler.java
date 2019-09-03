package com.fsd.um.exception;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fsd.um.response.HttpResponse;

/**
 * Global Exception Handler.
 * 
 * <pre>
 * 
 * 1. Handle Bad Request Exceptions, see https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 * 2. Business Exception
 * 3. Unexpected Error.
 * 
 * </pre>
 * 
 */
@ControllerAdvice
@RestController
class GlobalControllerExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @Autowired
    protected MessageSource messageSource;

    /**
     * Handle Bean Validation(JSR 303) Errors. (code: 400).
     * 
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public HttpResponse handleValidationErrors(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return new HttpResponse(HttpStatus.BAD_REQUEST.value(), errors);
    }

    /**
     * ConstrainViolationException: This exception reports the result of constraint
     * violations. (code: 400).
     * 
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { ConstraintViolationException.class })
    public HttpResponse handleConstraintViolation(ConstraintViolationException e) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
                    + violation.getMessage());
        }

        return new HttpResponse(HttpStatus.BAD_REQUEST.value(), errors);
    }

    /**
     * MethodArgumentTypeMismatchException: This exception is thrown when method
     * argument is not the expected type. (code: 400).
     * 
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
    public HttpResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        String error = e.getName() + " should be of type " + e.getRequiredType().getName();

        return new HttpResponse(HttpStatus.BAD_REQUEST.value(), error);
    }

    /**
     * Business exceptions (code: 400).
     * 
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { CustomException.class, ServletException.class })
    public HttpResponse handleClientError(CustomException e) {
        logger.error("global exception: {}", e);
        return new HttpResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * 
     * Unexpected Error (code: 500).
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public HttpResponse handleInternalServerError(Exception e) {
        logger.error(e.getMessage(), e);
        return new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

}
