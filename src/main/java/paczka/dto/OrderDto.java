package paczka.dto;

import lombok.Data;
import paczka.entity.User;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class OrderDto
{
    @NotNull
    private long id;

    @NotNull
    private UserDto user;

    @NotNull
    private String type;

    @NotNull
    private long offerId;

    @NotNull
    private double price;

    @NotNull
    private Date orderDate;
}
