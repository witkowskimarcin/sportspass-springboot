package paczka.service;

import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import paczka.dto.*;
import paczka.entity.*;
import paczka.model.AttendanceList;
import paczka.repository.MAClassRepository;
import paczka.repository.MAOfferRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MAClassServiceImpl implements MAClassService {

    private JdbcTemplate jdbc;
    private MAClassRepository maClassRepository;
    private ModelMapper modelMapper;
    private UserService userService;
    private MAOfferDetailService maOfferDetailService;
    private MAPassService maPassService;
    private MAOfferRepository maOfferRepository;

    public MAClassServiceImpl(JdbcTemplate jdbc, MAClassRepository maClassRepository, ModelMapper modelMapper, UserService userService, MAOfferDetailService maOfferDetailService, MAPassService maPassService, MAOfferRepository maOfferRepository)
    {
        this.jdbc = jdbc;
        this.maClassRepository = maClassRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.maOfferDetailService = maOfferDetailService;
        this.maPassService = maPassService;
        this.maOfferRepository = maOfferRepository;
    }

    @Override
    public void delete(MAClassDto maClass)
    {
        maClassRepository.delete(convertTEntity(maClass));
    }

    @Override
    public void deleteEntity(MAClass maClass) {
        maClassRepository.delete(maClass);
    }

    @Override
    public MAClassDto convertToDto(MAClass b)
    {
        return modelMapper.map(b, MAClassDto.class);
    }

    @Override
    public MAClass convertTEntity(MAClassDto b)
    {
        return modelMapper.map(b, MAClass.class);
    }

    @Override
    public List<MAClassDto> findAllByMaOffer(MAOfferDto mo)
    {
//        MAOffer maOffer = maOfferRepository.findById(mo.getId()).get();
        return mo.getClasses();//.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<MAClassDto> findAllByCoach(User currentUser)
    {
        return maClassRepository.findAllByCoach(currentUser).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public MAClassDto findById(long id)
    {
        return convertToDto(maClassRepository.findById(id).orElseThrow(()->new RuntimeException("MAClass id: "+id+" does not exist")));
    }

    @Override
    public void save(MAClassDto maClass)
    {
        maClassRepository.save(convertTEntity(maClass));
    }

    @Override
    public void saveEntity(MAClass maClass)
    {
        maClassRepository.save(maClass);
    }

    @Override
    public void add(MAClassDto maClass)
    {
        maClass.setCoach(userService.getCurrentUserDto());
        save(maClass);
    }

    @Override
    public void edit(MAClassDto maClass)
    {
        save(maClass);
    }

    @Override
    public void deleteById(long id)
    {
        maClassRepository.deleteById(id);
    }

    @Override
    public List<UserDto> attendanceList(MAClassDto maClass)
    {
        return maClass.getMaOffer().getClients();
    }

    @Override
    public void checkAttendanceList(MAClassDto mc, AttendanceList list)
    {
        Scanner s = new Scanner(list.getList());

        int i=0;
        int j=0;

        while (s.hasNextLong()) {
            ++i;
            Long l = s.nextLong();

            for (MAPassDto mp : userService.findById(l).getMaPasses()) {
                if(maOfferDetailService.getMaOfferOfDetail(mp.getMaOfferDetail()).getId()==mc.getMaOffer().getId()){
                    mp.setEntries(mp.getEntries()-1);

                    if(mp.getEntries()<=0){
                        maPassService.delete(mp);
                    }
                    else{
                        maPassService.save(mp);
                    }
                    ++j;
                }
            }
        }
    }

    @Override
    public List<UserDto> coachesList(UserDto user)
    {
//        User u = userService.convertToEntity(user);
        List<MAPassDto> maPasses = user.getMaPasses();
        List<UserDto> coaches = new ArrayList<>();

        for(MAPassDto mp : maPasses) {
            for (MAClassDto mc : findAllByMaOffer(maPassService.getMAOffer(mp))) {
                coaches.add(mc.getCoach());
            }
        }
        return coaches;

//        return null;
    }

    @Override
    public List<Object> getPlan(UserDto user)
    {
        List<MAPassDto> maPasses = user.getMaPasses();
        List<MAClassDto> maClasses = new ArrayList<>();
        Map<String,Object> plan = new HashMap<>();
        List<Object> plans = new ArrayList<>();
        Map<String,Object> maoffer = new HashMap<>();

        for(MAPassDto mp : maPasses){

            for(MAClassDto mc : findAllByMaOffer(maPassService.getMAOffer(mp))){
                plan.clear();
                maoffer.clear();

                maoffer.put("id",mc.getMaOffer().getId());
                maoffer.put("description",mc.getMaOffer().getDescription());
                maoffer.put("location",mc.getMaOffer().getLocation());
                maoffer.put("street",mc.getMaOffer().getStreet());
                maoffer.put("brandID",mc.getMaOffer().getBrand().getId());
                maoffer.put("brandName",mc.getMaOffer().getBrand().getName());
                maoffer.put("brandImage",mc.getMaOffer().getBrand().getImage());

                plan.put("id",mc.getId());
                plan.put("description",mc.getDescription());
                plan.put("numberOfDay",mc.getNumberOfDay());
                plan.put("time",mc.getTime());
                plan.put("maoffer",maoffer);

                maClasses.add(mc);
                plans.add(plan);
            }
        }
        return plans;
    }
}
