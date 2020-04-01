package paczka.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import paczka.dto.MAClassDto;
import paczka.dto.MAOfferDto;
import paczka.dto.RoleDto;
import paczka.dto.UserDto;
import paczka.entity.*;
import paczka.repository.BrandRepository;
import paczka.repository.MAOfferDetailRepository;
import paczka.repository.MAOfferRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MAOfferServiceImpl implements  MAOfferService{

    private JdbcTemplate jdbc;
    private MAOfferRepository maOfferRepository;
    private MAOfferDetailRepository maOfferDetailRepository;
    private ModelMapper modelMapper;
    private BrandRepository brandRepository;
    private UserService userService;

    public MAOfferServiceImpl(JdbcTemplate jdbc, MAOfferRepository maOfferRepository, MAOfferDetailRepository maOfferDetailRepository, ModelMapper modelMapper, BrandRepository brandRepository, UserService userService)
    {
        this.jdbc = jdbc;
        this.maOfferRepository = maOfferRepository;
        this.maOfferDetailRepository = maOfferDetailRepository;
        this.modelMapper = modelMapper;
        this.brandRepository = brandRepository;
        this.userService = userService;
    }

    @Override
    public void delete(MAOffer mo) {

        for(MAOfferDetail mod : mo.getMaOfferDetailList()){
            delete(mod);
        }

        jdbc.execute("DELETE FROM MACLASS WHERE MA_OFFER_ID ="+mo.getId());
        jdbc.execute("DELETE FROM BRAND_MA_OFFER_LIST WHERE MA_OFFER_LIST_ID ="+mo.getId());
        maOfferRepository.delete(mo);
    }

    @Override
    public void delete(MAOfferDetail mod) {

        jdbc.execute("DELETE FROM MAOFFER_MA_OFFER_DETAIL_LIST WHERE MA_OFFER_DETAIL_LIST_ID ="+mod.getId());

        try {
            Map<String, Object> queryMap = jdbc.queryForMap("SELECT ID FROM MAPASS WHERE MA_OFFER_DETAIL_ID =" + mod.getId());

            if (queryMap.size() > 0) {
                queryMap.forEach((k, v) -> {
                    jdbc.execute("DELETE FROM USER_MA_PASSES WHERE MA_PASSES_ID =" + v);
                });
            }
        } catch (Exception e){

        }
        jdbc.execute("DELETE FROM MAPASS WHERE MA_OFFER_DETAIL_ID ="+mod.getId());
        maOfferDetailRepository.delete(mod);
    }

    @Override
    public MAOfferDto convertToDto(MAOffer b)
    {
        return modelMapper.map(b, MAOfferDto.class);
    }

    @Override
    public MAOffer convertTEntity(MAOfferDto b)
    {
        return modelMapper.map(b, MAOffer.class);
    }

    @Override
    public List<MAOfferDto> findAllByLocation(String city)
    {
        List<MAOffer> list = maOfferRepository.findAllByLocation(city);
        return list.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void save(MAOfferDto mo)
    {
        maOfferRepository.save(convertTEntity(mo));
    }

    @Override
    public void save(MAOffer mo)
    {
        maOfferRepository.save(mo);
    }

    @Override
    public List<MAOfferDto> findAll()
    {
        return maOfferRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public MAOfferDto findById(long moid)
    {
        return convertToDto(maOfferRepository.findById(moid).orElseThrow(()->new RuntimeException("MAOffer id: "+moid+" does not exist")));
    }

    @Override
    public void addNew(long bid, MAOfferDto maOffer)
    {
        Brand b = brandRepository.findById(bid).orElseThrow(()->new RuntimeException("Brand id: "+bid+" does not exist"));
        MAOffer mo = convertTEntity(maOffer);
        mo.setBrand(b);
        maOfferRepository.save(mo);
        b.getMaOfferList().add(mo);
        brandRepository.save(b);
    }

    @Override
    public void edit(MAOffer maOffer)
    {
        maOfferRepository.save(maOffer);
    }

    @Override
    public void addCoach(UserDto user, MAOfferDto maOffer)
    {
        User u = modelMapper.map(user,User.class);
        RoleDto role = new RoleDto();
        role.setRole("COACH");
        userService.addRoleToUser(user,role);
        MAOffer mo = convertTEntity(maOffer);
        mo.getCoaches().add(u);
        maOfferRepository.save(mo);
    }

    @Override
    public void removeCoachFromOffer(UserDto user, MAOfferDto maOffer)
    {
        MAOffer mo = convertTEntity(maOffer);
        mo.getCoaches().removeIf((e)->e.getId()==user.getId());
        User u = modelMapper.map(user,User.class);
//        u.getRoles().removeIf((e)->e.)
    }

    @Override
    public void deleteById(long id)
    {
        maOfferRepository.deleteById(id);
    }

    @Override
    public List<MAOfferDto> getMaOffersOfCoach(UserDto user)
    {
        List<MAOfferDto> all = findAll();
        List<MAOfferDto> maOffers = new ArrayList<>();

        for(MAOfferDto mo : all){
            if(mo.getCoaches().contains(user)) {
                maOffers.add(mo);
            }
        }
        return maOffers;
    }
}
