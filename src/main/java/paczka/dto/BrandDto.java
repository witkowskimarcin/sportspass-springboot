package paczka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto
{
    @NotNull
    private long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    private String image;

//    private UserDto owner;
//
//    private List<GymOfferDto> gymOfferList;
//
//    private List<MAOfferDto> maOfferList;

//    public String getImage() {
//
//        return Base64.getEncoder().encodeToString(image);
//    }
}
