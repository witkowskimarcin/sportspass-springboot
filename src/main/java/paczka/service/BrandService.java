package paczka.service;

import paczka.dto.BrandDto;
import paczka.dto.GymOfferDto;
import paczka.dto.MAOfferDto;
import paczka.dto.UserDto;
import paczka.entity.Brand;
import paczka.model.AddBrandForm;

import java.util.List;

public interface BrandService {

    void delete(Brand b);
    void delete(BrandDto b);
    BrandDto convertToDto(Brand b);
    Brand convertTEntity(BrandDto b);
    BrandDto findById(long id);
    void save(BrandDto b);
    BrandDto findByOwner(UserDto u);
    List<MAOfferDto> getMAOfferList(BrandDto brand);
    List<GymOfferDto> getGymOfferList(BrandDto brand);
    List<BrandDto> findAll();
    void edit(BrandDto brand);
    BrandDto getBrandByOwner(UserDto owner);
}
