package paczka.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;

@Data
public class MAClassDto
{
    @NotNull
    private long id;

    @Size(max = 50)
    private String description;

    @NotNull
    private MAOfferDto maOffer;

    @NotNull
    private int numberOfDay;

    @NotNull
    private Time time;

    private UserDto coach;
}
