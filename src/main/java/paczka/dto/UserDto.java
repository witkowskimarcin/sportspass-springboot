package paczka.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserDto
{
    @NotNull
    private long id;

    @Size(min = 8, max = 30)
    @NotNull
    @Email
    private String email;

    @Size(min = 4, max = 20)
    private String firstname;

    @Size(min = 4, max = 20)
    private String lastname;

    @Size(min = 4, max = 20)
    private String password;

    @Size(min = 4, max = 20)
    private String locality;

    @Size(min = 4, max = 20)
    private String street;

    @Size(min = 4, max = 20)
    private String zipcode;

    @Size(min = 4, max = 20)
    private String phone;

    private int active;

    private List<RoleDto> roles;

    private List<GymPassDto> gymPasses;

    private List<MAPassDto> maPasses;
}
