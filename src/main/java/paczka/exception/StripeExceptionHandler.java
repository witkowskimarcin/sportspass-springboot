package paczka.exception;

import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import paczka.model.ErrorMessage;

@ControllerAdvice
public class StripeExceptionHandler
{
    @ResponseBody
    @ExceptionHandler(StripeException.class)
    public ResponseEntity handle(StripeException ex) {
        return new ResponseEntity(new ErrorMessage(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
