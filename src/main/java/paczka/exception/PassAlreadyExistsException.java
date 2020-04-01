package paczka.exception;

public class PassAlreadyExistsException extends RuntimeException
{
    public PassAlreadyExistsException()
    {
        super("You already have this pass");
    }
}
