package paczka.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class GymPassDto
{
    @NotNull
    private long id;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    private GymOfferDetailDto gymOfferDetail;
}
