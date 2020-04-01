package paczka.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paczka.dto.GymPassDto;
import paczka.dto.UserDto;
import paczka.entity.GymPass;
import paczka.entity.User;
import paczka.repository.GymPassRepository;
import paczka.repository.UserRepository;

import java.util.List;

@Service
public class GymPassServiceImpl implements GymPassService
{
    private GymPassRepository gymPassRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;

    public GymPassServiceImpl(GymPassRepository gymPassRepository, ModelMapper modelMapper, UserRepository userRepository)
    {
        this.gymPassRepository = gymPassRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void save(GymPass gp)
    {
        gymPassRepository.save(gp);
    }

    @Override
    public void save(GymPassDto gp)
    {
        gymPassRepository.save(convertToEntity(gp));
    }

    @Override
    public GymPassDto convertToDto(GymPass gp)
    {
        return modelMapper.map(gp, GymPassDto.class);
    }

    @Override
    public GymPass convertToEntity(GymPassDto gp)
    {
        return modelMapper.map(gp, GymPass.class);
    }

    @Override
    public List<GymPassDto> getGymPassesOfUser(UserDto user)
    {
        return null;
    }

    @Override
    public GymPassDto findById(Long id)
    {
        return convertToDto(gymPassRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("GymPass id: "+id+" does not exist")));
    }

    @Override
    public void delete(Long uid, Long gpid)
    {
        User user = userRepository
                .findById(uid)
                .orElseThrow(()->new RuntimeException("User id: "+uid+" does not exist"));

        user.getGymPasses().removeIf( (e)-> e.getId()==gpid );

        userRepository.save(user);
        gymPassRepository.deleteById(gpid);
    }

    @Override
    public void addPassToUser(UserDto user, GymPassDto gymPass)
    {
        User u = userRepository
                .findById(user.getId())
                .orElseThrow(()->new RuntimeException("User id: "+user.getId()+" does not exist"));

        GymPass gp = convertToEntity(gymPass);
        gymPassRepository.save(gp);

        u.getGymPasses().add(gp);
        userRepository.save(u);
    }
}
