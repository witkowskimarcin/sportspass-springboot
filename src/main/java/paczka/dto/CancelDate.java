package paczka.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CancelDate
{
    @NotNull
    String date;
}
