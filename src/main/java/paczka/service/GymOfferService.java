package paczka.service;

import paczka.dto.BrandDto;
import paczka.dto.GymOfferDetailDto;
import paczka.dto.GymOfferDto;
import paczka.entity.Brand;
import paczka.entity.GymOffer;
import paczka.entity.GymOfferDetail;

import java.util.List;

public interface GymOfferService {

    void delete(GymOffer go);
    void delete(GymOfferDto go);
    void delete(GymOfferDetail god);
    void delete(GymOfferDetailDto god);
    GymOfferDto convertToDto(GymOffer b);
    GymOffer convertTEntity(GymOfferDto b);
    List<GymOfferDto> findAllByLocation(String city);
    GymOfferDto findById(long goid);
    void save(GymOfferDto gymOffer);
    void save(GymOffer gymOffer);
    void add(Long bid, GymOfferDto gymOffer);
    List<GymOfferDto> findAll();
    void edit(GymOfferDto gymOffer);
    List<GymOfferDetailDto> getGymOfferDetails(GymOfferDto gymOffer);
}
