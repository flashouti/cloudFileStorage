package yurkov.cloudFileStorage.adapter.web.advice;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import yurkov.cloudFileStorage.adapter.web.dto.response.BasicErrorResponse;
import yurkov.cloudFileStorage.adapter.web.errors.AlreadyExistException;
import yurkov.cloudFileStorage.adapter.web.errors.UserNotFoundException;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommonAdvice extends ResponseEntityExceptionHandler {

    static String VALIDATION_ERROR = "validation_error";

    @ExceptionHandler
    public BasicErrorResponse handleNotFound(UserNotFoundException ex) {
        return new BasicErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler
    public BasicErrorResponse handleAlreadyExist(AlreadyExistException ex) {
        return new BasicErrorResponse(ex.getCode(), ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<BasicErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors().stream().map(fieldError ->
                        new BasicErrorResponse(VALIDATION_ERROR, fieldError.getField(), fieldError.getDefaultMessage())).
                toList();
        return ResponseEntity.badRequest()
                .body(errors);
    }
}