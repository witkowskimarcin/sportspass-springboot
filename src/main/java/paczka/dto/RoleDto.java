package paczka.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import paczka.entity.User;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

public class RoleDto
{
    @Getter
    @Setter
    @NotNull
    private int id;

    @Getter
    @Setter
    @NotNull
    private String role;

    private List<UserDto> users;
}
