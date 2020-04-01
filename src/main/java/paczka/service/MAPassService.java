package paczka.service;

import paczka.dto.MAOfferDto;
import paczka.dto.MAPassDto;
import paczka.entity.MAOffer;
import paczka.entity.MAPass;

public interface MAPassService {

    void delete(MAPassDto maPass);
    void deleteEntity(MAPass maPass);
    MAPassDto convertToDto(MAPass b);
    MAPass convertTEntity(MAPassDto b);

    void save(MAPassDto mp);
    void saveEntity(MAPass mp);
    MAOfferDto getMAOffer(MAPassDto mp);
}
