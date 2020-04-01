package paczka.service;

import paczka.dto.MAOfferDetailDto;
import paczka.dto.MAOfferDto;
import paczka.entity.MAOffer;
import paczka.entity.MAOfferDetail;

import java.util.List;

public interface MAOfferDetailService
{
    List<MAOfferDetailDto> getMaOfferDetailListOfOffer(Long id);
    MAOfferDetail convertToEntity(MAOfferDetailDto maOfferDetail);
    MAOfferDetailDto convertToDto(MAOfferDetail maOfferDetail);
    MAOfferDetailDto findById(long id);
    List<MAOfferDetailDto> findAllByMaOffer(MAOffer maOffer);
    MAOfferDto getMaOfferOfDetail(MAOfferDetailDto maOfferDetailDto);
    void save(MAOfferDetailDto maOfferDetail);
    void add(MAOfferDto maOffer, MAOfferDetailDto maOfferDetail);
    void delete(MAOfferDetailDto go);
    void delete(MAOfferDetail go);
}
