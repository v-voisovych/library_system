package com.voisovych.library.myexceptionhandler;

import com.voisovych.library.myexception.MyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    Logger logger = LogManager.getLogger(MyExceptionHandler.class);

    @ExceptionHandler(MyException.class)
    public ResponseEntity<String> myExceptionHandler(MyException e) {
        logger.warn(e.getMessage());
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
