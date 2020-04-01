package paczka.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class GymOfferDetailDto
{
    @NotNull
    private long id;

    @NotNull
    @Size(min = 4, max = 20)
    private String text;

    @Size(max = 50)
    private String description;

    @NotNull
    private double price;

    @NotNull
    private int months;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private GymOfferDto gymOffer;
}
