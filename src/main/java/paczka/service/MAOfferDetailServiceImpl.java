package paczka.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paczka.dto.GymOfferDetailDto;
import paczka.dto.MAOfferDetailDto;
import paczka.dto.MAOfferDto;
import paczka.entity.MAOffer;
import paczka.entity.MAOfferDetail;
import paczka.repository.MAOfferDetailRepository;
import paczka.repository.MAOfferRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MAOfferDetailServiceImpl implements MAOfferDetailService
{
    private MAOfferRepository maOfferRepository;
    private MAOfferDetailRepository maOfferDetailRepository;
    private ModelMapper modelMapper;

    @Autowired
    public MAOfferDetailServiceImpl(MAOfferRepository maOfferRepository, MAOfferDetailRepository maOfferDetailRepository, ModelMapper modelMapper)
    {
        this.maOfferRepository = maOfferRepository;
        this.maOfferDetailRepository = maOfferDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MAOfferDetailDto> getMaOfferDetailListOfOffer(Long id)
    {
        return maOfferRepository.findById(id).get().getMaOfferDetailList().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MAOfferDetail convertToEntity(MAOfferDetailDto maOfferDetail)
    {
        return modelMapper.map(maOfferDetail, MAOfferDetail.class);
    }

    @Override
    public MAOfferDetailDto convertToDto(MAOfferDetail maOfferDetail)
    {
        return modelMapper.map(maOfferDetail, MAOfferDetailDto.class);
    }

    @Override
    public MAOfferDetailDto findById(long id)
    {
        return convertToDto(maOfferDetailRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("MAOfferDetail id: "+id+" does not exist")));
    }

    @Override
    public List<MAOfferDetailDto> findAllByMaOffer(MAOffer maOffer)
    {
        return maOffer
                .getMaOfferDetailList()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MAOfferDto getMaOfferOfDetail(MAOfferDetailDto maOfferDetailDto)
    {
        return modelMapper.map(maOfferDetailRepository
                        .findById(maOfferDetailDto.getId())
                        .orElseThrow(()->new RuntimeException("MAOfferDetail id: "+maOfferDetailDto.getId()+" does not exist"))
                        .getMaOffer(), MAOfferDto.class);
    }

    @Override
    public void save(MAOfferDetailDto maOfferDetail)
    {
        maOfferDetailRepository.save(convertToEntity(maOfferDetail));
    }

    @Override
    public void add(MAOfferDto maOffer, MAOfferDetailDto maOfferDetail)
    {
        MAOfferDetail mod = convertToEntity(maOfferDetail);
        MAOffer mo = modelMapper.map(maOffer,MAOffer.class);
        maOfferDetailRepository.save(mod);
        mo.getMaOfferDetailList().add(mod);
        maOfferRepository.save(mo);
    }

    @Override
    public void delete(MAOfferDetailDto go)
    {
        maOfferDetailRepository.delete(convertToEntity(go));
    }

    @Override
    public void delete(MAOfferDetail go)
    {
        maOfferDetailRepository.delete(go);
    }
}
