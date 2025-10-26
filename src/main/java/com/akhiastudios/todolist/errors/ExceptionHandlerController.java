package com.akhiastudios.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // ao ocorrer uma excessao, vai passar por essa classe, porque ela se torna global no momento que uma excessao é ativada
public class ExceptionHandlerController {
    
    @ExceptionHandler(HttpMessageNotReadableException.class) // get the class to handle the exception
    public ResponseEntity<String> handleHttpMessageNotReadbleException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage()); // ex: pega a mensagem de excessao criada no TaskModel "O campo title deve conter no máximo 50 caracteres"
    }

}
