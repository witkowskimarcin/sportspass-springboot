package paczka.service;

import paczka.dto.GymPassDto;
import paczka.dto.UserDto;
import paczka.entity.GymPass;

import java.util.List;

public interface GymPassService
{
    void save(GymPass gp);
    void save(GymPassDto gp);
    GymPassDto convertToDto(GymPass gp);
    GymPass convertToEntity(GymPassDto gp);
    List<GymPassDto> getGymPassesOfUser(UserDto user);
    GymPassDto findById(Long id);
    void delete(Long uid, Long gpid);
    void addPassToUser(UserDto user, GymPassDto gymPass);
}
