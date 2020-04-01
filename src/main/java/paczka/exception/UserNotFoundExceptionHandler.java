package paczka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import paczka.model.ErrorMessage;

@ControllerAdvice
public class UserNotFoundExceptionHandler
{
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handler(UserNotFoundException e){
        return new ResponseEntity(new ErrorMessage(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
