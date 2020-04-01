package paczka.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paczka.dto.CanceledClassDto;
import paczka.dto.MAClassDto;
import paczka.entity.CanceledClass;
import paczka.repository.CanceledClassRepository;

import java.util.Date;

@Service
public class CanceledClassServiceImpl implements CanceledClassService
{

    private CanceledClassRepository canceledClassRepository;
    private ModelMapper modelMapper;

    public CanceledClassServiceImpl(CanceledClassRepository canceledClassRepository, ModelMapper modelMapper)
    {
        this.canceledClassRepository = canceledClassRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(CanceledClassDto canceledClassDto)
    {
        canceledClassRepository.save(convertToEntity(canceledClassDto));
    }

    @Override
    public void saveEntity(CanceledClass canceledClass)
    {
        canceledClassRepository.save(canceledClass);
    }

    @Override
    public CanceledClassDto convertToDto(CanceledClass canceledClass)
    {
        return modelMapper.map(canceledClass,CanceledClassDto.class);
    }

    @Override
    public CanceledClass convertToEntity(CanceledClassDto canceledClass)
    {
        return modelMapper.map(canceledClass,CanceledClass.class);
    }

    @Override
    public void cancelClass(MAClassDto mc, String date)
    {
        CanceledClassDto cc = new CanceledClassDto();
        cc.setMaClass(mc);
        cc.setDate(new Date(date));
        save(cc);
    }
}
