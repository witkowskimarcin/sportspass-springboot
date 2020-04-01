package paczka.service;

import paczka.dto.MAClassDto;
import paczka.dto.MAOfferDto;
import paczka.dto.UserDto;
import paczka.entity.*;
import paczka.model.AttendanceList;

import java.util.List;

public interface MAClassService {

    void delete(MAClassDto maClass);
    void deleteEntity(MAClass maClass);
    MAClassDto convertToDto(MAClass b);
    MAClass convertTEntity(MAClassDto b);
    List<MAClassDto> findAllByMaOffer(MAOfferDto maOffer);
    List<MAClassDto> findAllByCoach(User currentUser);
    MAClassDto findById(long id);
    void save(MAClassDto maClass);
    void saveEntity(MAClass maClass);
    void add(MAClassDto maClass);
    void edit(MAClassDto maClass);
    void deleteById(long id);
    List<UserDto> attendanceList(MAClassDto maClass);
    void checkAttendanceList(MAClassDto byId, AttendanceList list);
    List<UserDto> coachesList(UserDto user);
    List<Object> getPlan(UserDto u);
}
