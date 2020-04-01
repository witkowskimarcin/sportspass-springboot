package paczka.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CanceledClassDto
{
    @NotNull
    private int id;

    @NotNull
    private Date date;

    private MAClassDto maClass;
}
