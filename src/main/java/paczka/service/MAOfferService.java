package paczka.service;

import paczka.dto.MAClassDto;
import paczka.dto.MAOfferDto;
import paczka.dto.UserDto;
import paczka.entity.MAClass;
import paczka.entity.MAOffer;
import paczka.entity.MAOfferDetail;

import java.util.List;
import java.util.Optional;

public interface MAOfferService {

    void delete(MAOffer mo);
    void delete(MAOfferDetail mod);
    MAOfferDto convertToDto(MAOffer b);
    MAOffer convertTEntity(MAOfferDto b);
    List<MAOfferDto> findAllByLocation(String city);
    void save(MAOfferDto mo);
    void save(MAOffer mo);
    List<MAOfferDto> findAll();
    MAOfferDto findById(long moid);
    void addNew(long bid, MAOfferDto maOffer);
    void edit(MAOffer maOffer);
    void addCoach(UserDto user, MAOfferDto maOffer);
    void removeCoachFromOffer(UserDto user, MAOfferDto maOffer);
    void deleteById(long id);
    List<MAOfferDto> getMaOffersOfCoach(UserDto currentUserDto);
}
