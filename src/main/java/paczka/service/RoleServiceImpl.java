package paczka.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import paczka.dto.RoleDto;
import paczka.entity.Role;
import paczka.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService
{
    private RoleRepository roleRepository;
    private ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper)
    {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(RoleDto role)
    {
        roleRepository.save(convertToEntity(role));
    }

    @Override
    public RoleDto findByRole(String role)
    {
        return convertToDto(roleRepository.findByRole(role));
    }

    @Override
    public RoleDto convertToDto(Role role)
    {
        return modelMapper.map(role,RoleDto.class);
    }

    @Override
    public Role convertToEntity(RoleDto role)
    {
        return modelMapper.map(role,Role.class);
    }

    @Override
    public RoleDto findById(Integer id)
    {
        return convertToDto(roleRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Role id: "+id+" does not exist")));
    }

    @Override
    public List<RoleDto> findAll()
    {
        return roleRepository
                .findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
