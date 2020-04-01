package paczka.service;

import paczka.dto.CanceledClassDto;
import paczka.dto.MAClassDto;
import paczka.entity.CanceledClass;

public interface CanceledClassService
{
    void save(CanceledClassDto canceledClassDto);
    void saveEntity(CanceledClass canceledClassDto);
    CanceledClassDto convertToDto(CanceledClass canceledClass);
    CanceledClass convertToEntity(CanceledClassDto canceledClassDto);
    void cancelClass(MAClassDto mc, String date);
}
