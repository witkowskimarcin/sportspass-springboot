package paczka.exception;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(String email)
    {
        super(email+" user not found");
    }
}
