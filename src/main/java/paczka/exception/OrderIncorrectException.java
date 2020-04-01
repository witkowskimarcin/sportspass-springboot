package paczka.exception;

public class OrderIncorrectException extends RuntimeException
{
    public OrderIncorrectException()
    {
        super("Order incorrect, OfferDetail does not exist");
    }
}
