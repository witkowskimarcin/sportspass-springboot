package paczka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import paczka.model.ErrorMessage;

@ControllerAdvice
public class UserEmailAlreadyExistsExceptionHandler
{
    @ResponseBody
    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity handler(UserEmailAlreadyExistsException e){
        return new ResponseEntity(new ErrorMessage(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
