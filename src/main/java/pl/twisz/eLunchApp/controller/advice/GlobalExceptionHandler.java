package pl.twisz.eLunchApp.controller.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleSQLIntegrityConstraintViolationException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Cannot delete object, because of existing database relation");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Invalid data.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Validation error: " + ex.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error: " + ex.getReason());
    }
}
