package paczka.service;

import org.springframework.stereotype.Service;
import paczka.dto.GymOfferDetailDto;
import paczka.dto.GymOfferDto;
import paczka.entity.GymOfferDetail;

import java.util.List;

@Service
public interface GymOfferDetailService
{
    List<GymOfferDetailDto> getGymOfferDetailListOfOffer(Long id);
    GymOfferDetailDto convertToDto(GymOfferDetail gymOfferDetail);
    GymOfferDetail convertToEntity(GymOfferDetailDto gymOfferDetail);
    GymOfferDetailDto findById(long id);
    GymOfferDto getGymOfferOfDetail(GymOfferDetailDto god);
    void save(GymOfferDetailDto gymOfferDetail);
    void add(GymOfferDto go, GymOfferDetailDto gymOfferDetail);
    void edit(Long goid, GymOfferDetailDto gymOfferDetailDto);
    void delete(GymOfferDetailDto gymOfferDetail);
    void delete(Long goid, Long godid);
}
