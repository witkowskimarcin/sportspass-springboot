package paczka.exception;

import paczka.dto.UserDto;

public class UserEmailAlreadyExistsException extends RuntimeException
{
    public UserEmailAlreadyExistsException(String email)
    {
        super(email+" email already exists");
    }
}
