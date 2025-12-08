package com.ecommerce.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice// it will intercept any exception thrown by controller
public class MyGlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)// this tells to invoke this function in case of MethodArgumentNotValidException is thrown
    //and this is the error thrown by @Valid annotation
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, String> response = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(e-> {
            String fieldName = ((FieldError)e).getField();
            String message = e.getDefaultMessage();
             response.put(fieldName, message);
        });
        //return response; //by desfult it will return 200 status code
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> myResourceNotFoundException(ResourceNotFoundException e){
      String message = e.getMessage();
      return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> myAPIException(APIException e){
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
