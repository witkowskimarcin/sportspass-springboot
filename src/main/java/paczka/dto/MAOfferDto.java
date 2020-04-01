package paczka.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MAOfferDto
{
    @NotNull
    private long id;

    @NotNull
    private BrandDto brand;

    @NotNull
    private String location;

    @NotNull
    @Size(min = 4, max=50)
    private String street;

    @Size(max = 50)
    private String description;

    private List<MAOfferDetailDto> maOfferDetailList;

    private List<UserDto> clients;

    private List<UserDto> coaches;

    private List<MAClassDto> classes;
}
