package paczka.dto;

import lombok.Data;
import paczka.entity.MAOfferDetail;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class MAPassDto
{
    @NotNull
    private long id;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private int entries;

    @NotNull
    private MAOfferDetailDto maOfferDetail;
}
