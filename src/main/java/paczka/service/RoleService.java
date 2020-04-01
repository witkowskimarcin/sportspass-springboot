package paczka.service;

import paczka.dto.RoleDto;
import paczka.entity.Role;

import java.util.List;

public interface RoleService
{
    void save(RoleDto role);
    RoleDto findByRole(String role);
    RoleDto convertToDto(Role role);
    Role convertToEntity(RoleDto role);
    RoleDto findById(Integer id);
    List<RoleDto> findAll();
}
