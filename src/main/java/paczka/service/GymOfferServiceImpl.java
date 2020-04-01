package paczka.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import paczka.controller.HomeController;
import paczka.dto.BrandDto;
import paczka.dto.GymOfferDetailDto;
import paczka.dto.GymOfferDto;
import paczka.entity.Brand;
import paczka.entity.GymOffer;
import paczka.entity.GymOfferDetail;
import paczka.repository.BrandRepository;
import paczka.repository.GymOfferDetailRepository;
import paczka.repository.GymOfferRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GymOfferServiceImpl implements GymOfferService {

    private JdbcTemplate jdbc;
    private GymOfferRepository gymOfferRepository;
    private GymOfferDetailRepository gymOfferDetailRepository;
    private ModelMapper modelMapper;
    private BrandRepository brandRepository;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public GymOfferServiceImpl(JdbcTemplate jdbc, GymOfferRepository gymOfferRepository, GymOfferDetailRepository gymOfferDetailRepository, ModelMapper modelMapper, BrandRepository brandRepository)
    {
        this.jdbc = jdbc;
        this.gymOfferRepository = gymOfferRepository;
        this.gymOfferDetailRepository = gymOfferDetailRepository;
        this.modelMapper = modelMapper;
        this.brandRepository = brandRepository;
    }

    @Override
    public void delete(GymOffer go) {

        for(GymOfferDetail god : go.getGymOfferDetailList()){
            delete(god);
        }

        jdbc.execute("DELETE FROM BRAND_GYM_OFFER_LIST WHERE GYM_OFFER_LIST_ID ="+go.getId());
        gymOfferRepository.delete(go);

    }

    @Override
    public void delete(GymOfferDto go)
    {
        gymOfferRepository.delete(modelMapper.map(go,GymOffer.class));
    }

    @Override
    public void delete(GymOfferDetail god) {

        jdbc.execute("DELETE FROM GYM_OFFER_GYM_OFFER_DETAIL_LIST WHERE GYM_OFFER_DETAIL_LIST_ID ="+god.getId());
        try {
            Map<String, Object> queryMap = jdbc.queryForMap("SELECT ID FROM GYM_PASS WHERE GYM_OFFER_DETAIL_ID =" + god.getId());

            if (queryMap.size() > 0) {
                queryMap.forEach((k, v) -> {
                    jdbc.execute("DELETE FROM USER_GYM_PASSES WHERE GYM_PASSES_ID =" + v);
                });
            }
        } catch (Exception e){

        }
        jdbc.execute("DELETE FROM GYM_PASS WHERE GYM_OFFER_DETAIL_ID ="+god.getId());

        gymOfferDetailRepository.delete(god);

    }

    @Override
    public void delete(GymOfferDetailDto god)
    {
        gymOfferDetailRepository.delete(modelMapper.map(god,GymOfferDetail.class));
    }

    @Override
    public GymOfferDto convertToDto(GymOffer b)
    {
        return modelMapper.map(b, GymOfferDto.class);
    }

    @Override
    public GymOffer convertTEntity(GymOfferDto b)
    {
        return modelMapper.map(b, GymOffer.class);
    }

    @Override
    public List<GymOfferDto> findAllByLocation(String city)
    {
        List<GymOffer> gymOfferList = gymOfferRepository.findAllByLocation(city);
        return gymOfferList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GymOfferDto findById(long goid)
    {
        return convertToDto(gymOfferRepository.findById(goid).orElseThrow(()->new RuntimeException("GymOffer id: "+goid+" does not exist")));
    }

    @Override
    public void save(GymOfferDto gymOffer)
    {
        gymOfferRepository.save(convertTEntity(gymOffer));
    }

    @Override
    public void save(GymOffer gymOffer)
    {
        gymOfferRepository.save(gymOffer);
    }

    @Override
    public void add(Long bid, GymOfferDto gymOffer)
    {
        Brand b = brandRepository.findById(bid).orElseThrow(()->new RuntimeException("Brand id:"+bid+" does not exists"));
        GymOffer go = convertTEntity(gymOffer);
        go.setBrand(b);
        gymOfferRepository.save(go);
        b.getGymOfferList().add(go);
        brandRepository.save(b);
        logger.info("Create GymOffer id:"+go.getId());
    }

    @Override
    public List<GymOfferDto> findAll()
    {
        return gymOfferRepository
                .findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void edit(GymOfferDto gymOffer)
    {
        GymOffer go = gymOfferRepository.findById(gymOffer.getId()).orElseThrow(()->new RuntimeException("GymOffer id: "+gymOffer.getId()+" does not exist"));
        go.setDescription(gymOffer.getDescription());
        go.setLocation(gymOffer.getLocation());
        go.setStreet(gymOffer.getStreet());
        gymOfferRepository.save(go);
        logger.info("Edit GymOffer id: "+gymOffer.getId());
    }

    @Override
    public List<GymOfferDetailDto> getGymOfferDetails(GymOfferDto gymOffer)
    {
        return gymOfferRepository
                .findById(gymOffer.getId())
                .orElseThrow(()->new RuntimeException("GymOffer id: "+gymOffer.getId()+" does not exist"))
                .getGymOfferDetailList()
                .stream()
                .map(this::convertGodEntityToDto)
                .collect(Collectors.toList());
    }

    public GymOfferDetailDto convertGodEntityToDto(GymOfferDetail gymOfferDetail){
        return modelMapper.map(gymOfferDetail, GymOfferDetailDto.class);
    }
}
