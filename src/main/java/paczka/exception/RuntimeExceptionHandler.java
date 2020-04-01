package paczka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import paczka.model.ErrorMessage;

@ControllerAdvice
public class RuntimeExceptionHandler
{
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handler(RuntimeException e){
        return new ResponseEntity(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
