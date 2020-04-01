package paczka.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import paczka.dto.BrandDto;
import paczka.dto.GymOfferDto;
import paczka.dto.MAOfferDto;
import paczka.dto.UserDto;
import paczka.entity.Brand;
import paczka.entity.GymOffer;
import paczka.entity.MAOffer;
import paczka.entity.User;
import paczka.model.AddBrandForm;
import paczka.repository.BrandRepository;
import paczka.repository.UserRepository;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private JdbcTemplate jdbc;
    private BrandRepository brandRepository;
    private UserRepository userRepository;
    private MAOfferService maOfferService;
    private GymOfferService gymOfferService;
    private ModelMapper modelMapper;

    public BrandServiceImpl(JdbcTemplate jdbc, BrandRepository brandRepository, UserRepository userRepository, MAOfferService maOfferService, GymOfferService gymOfferService, ModelMapper modelMapper)
    {
        this.jdbc = jdbc;
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
        this.maOfferService = maOfferService;
        this.gymOfferService = gymOfferService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void delete(Brand b) {

        for(MAOffer mo : b.getMaOfferList()){
            maOfferService.delete(mo);
        }

        for(GymOffer go : b.getGymOfferList()){
            gymOfferService.delete(go);
        }

        jdbc.execute("DELETE FROM BRAND_GYM_OFFER_LIST WHERE BRAND_ID ="+b.getId());
        jdbc.execute("DELETE FROM BRAND_MA_OFFER_LIST WHERE BRAND_ID ="+b.getId());

        brandRepository.delete(b);
    }

    @Override
    public void delete(BrandDto b)
    {
        brandRepository.deleteById(b.getId());
    }

    @Override
    public BrandDto convertToDto(Brand b)
    {
        return modelMapper.map(b, BrandDto.class);
    }

    @Override
    public Brand convertTEntity(BrandDto b)
    {
        return modelMapper.map(b, Brand.class);
    }

    @Override
    public BrandDto findById(long id)
    {
        return convertToDto(brandRepository.findById(id).orElseThrow(()->new RuntimeException("Brand id: "+id+" does not exist")));
    }

    @Override
    public void save(BrandDto b)
    {
        Brand brand = convertTEntity(b);
        brand.setImageBase64(b.getImage());
        brandRepository.save(brand);
    }

    @Override
    public BrandDto findByOwner(UserDto u)
    {
        return convertToDto(brandRepository.findByOwner(modelMapper.map(u, User.class)));
    }

    @Override
    public List<MAOfferDto> getMAOfferList(BrandDto brand)
    {
        return brandRepository
                .findById(brand.getId())
                .orElseThrow(()->new RuntimeException("Brand id: "+brand.getId()+" does not exist"))
                .getMaOfferList()
                .stream()
                .map(maOfferService::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GymOfferDto> getGymOfferList(BrandDto brand)
    {
        return brandRepository
                .findById(brand.getId())
                .orElseThrow(()->new RuntimeException("Brand id: "+brand.getId()+" does not exist"))
                .getGymOfferList()
                .stream()
                .map(gymOfferService::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BrandDto> findAll()
    {
        List<Brand> brands = brandRepository.findAll();
        List<BrandDto> brandDtos = brands.stream().map(this::convertToDto).collect(Collectors.toList());

//        for (int i = 0; i < brandDtos.size(); i++)
//        {
//            brandDtos.get(i).setImage(brands.get(i).getImage());
//        }

        return brandDtos;
    }

    @Override
    public void edit(BrandDto brand)
    {
        Brand b = brandRepository.findById(brand.getId()).orElseThrow(()->new RuntimeException("Brand id:"+brand.getId()+" does not exist"));
        b.setImageBase64(brand.getImage());
        b.setName(brand.getName());
        brandRepository.save(b);
    }

    @Override
    public BrandDto getBrandByOwner(UserDto owner)
    {
        return convertToDto(brandRepository
                .findByOwner(userRepository
                .findById(owner.getId()).orElseThrow(()->new RuntimeException("User id:"+owner.getId()+" does not exist"))));
    }

}
