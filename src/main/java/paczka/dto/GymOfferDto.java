package paczka.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class GymOfferDto
{
    @NotNull
    private long id;

    private BrandDto brand;

    @NotNull
    @Size(min = 3, max = 20)
    private String location;

    @Size(min = 3, max = 40)
    private String street;

    @Size(max = 50)
    private String description;
}
