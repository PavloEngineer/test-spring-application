package com.application.test.businessLogic.exeptions;

import com.application.test.businessLogic.exeptions.type.EmailBusyException;
import com.application.test.businessLogic.exeptions.type.RequestValidationException;
import com.application.test.businessLogic.exeptions.type.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerExceptionHandler {

    /**
     * Handles MethodArgumentTypeMismatchException by returning a BAD_REQUEST response.
     * @param ex The MethodArgumentTypeMismatchException that occurred.
     * @return ResponseEntity with error message and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MissingPathVariableException by returning a BAD_REQUEST response.
     * @param ex The MissingPathVariableException that occurred.
     * @return ResponseEntity with error message and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(org.springframework.web.bind.MissingPathVariableException.class)
    public ResponseEntity<String> handleMissingPathVariable(org.springframework.web.bind.MissingPathVariableException ex) {
        String error = ex.getMessage();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MissingServletRequestParameterException by returning a BAD_REQUEST response.
     * @param ex The MissingServletRequestParameterException that occurred.
     * @return ResponseEntity with error message and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameter(org.springframework.web.bind.MissingServletRequestParameterException ex) {
        String error = ex.getMessage();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles HttpRequestMethodNotSupportedException by returning a METHOD_NOT_ALLOWED response.
     * @param ex The HttpRequestMethodNotSupportedException that occurred.
     * @return ResponseEntity with error message and HTTP status METHOD_NOT_ALLOWED.
     */
    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupported(org.springframework.web.HttpRequestMethodNotSupportedException ex) {
        String error = ex.getMessage();
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Handles UserNotFoundException by returning a NOT_FOUND response.
     * @param ex The UserNotFoundException that occurred.
     * @return ResponseEntity with error message and HTTP status NOT_FOUND.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>("User not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles RequestValidationException by returning a BAD_REQUEST response.
     * @param ex The RequestValidationException that occurred.
     * @return ResponseEntity with error message and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<String> handleRequestValidationException(RequestValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles EmailBusyException by returning a BAD_REQUEST response.
     * @param ex The EmailBusyException that occurred.
     * @return ResponseEntity with error message and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(EmailBusyException.class)
    public ResponseEntity<String> handleEmailBusyException(EmailBusyException ex) {
        return new ResponseEntity<>("Email is already in use: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any other exception by returning an INTERNAL_SERVER_ERROR response.
     * @param ex The exception that occurred.
     * @return ResponseEntity with error message and HTTP status INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
