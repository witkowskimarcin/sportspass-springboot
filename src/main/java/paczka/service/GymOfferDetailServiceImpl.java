package paczka.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import paczka.controller.HomeController;
import paczka.dto.GymOfferDetailDto;
import paczka.dto.GymOfferDto;
import paczka.entity.GymOffer;
import paczka.entity.GymOfferDetail;
import paczka.repository.GymOfferDetailRepository;
import paczka.repository.GymOfferRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GymOfferDetailServiceImpl implements GymOfferDetailService
{
    private GymOfferDetailRepository gymOfferDetailRepository;
    private GymOfferRepository gymOfferRepository;
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public GymOfferDetailServiceImpl(GymOfferDetailRepository gymOfferDetailRepository, GymOfferRepository gymOfferRepository, ModelMapper modelMapper)
    {
        this.gymOfferDetailRepository = gymOfferDetailRepository;
        this.gymOfferRepository = gymOfferRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<GymOfferDetailDto> getGymOfferDetailListOfOffer(Long id)
    {
        return gymOfferRepository.findById(id).get().getGymOfferDetailList().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GymOfferDetailDto convertToDto(GymOfferDetail gymOfferDetail)
    {
        return modelMapper.map(gymOfferDetail, GymOfferDetailDto.class);
    }

    @Override
    public GymOfferDetail convertToEntity(GymOfferDetailDto gymOfferDetail)
    {
        return modelMapper.map(gymOfferDetail, GymOfferDetail.class);
    }

    @Override
    public GymOfferDetailDto findById(long id)
    {
        return convertToDto(gymOfferDetailRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("GymOfferDetail id:"+id+" does not exist")));
    }

    @Override
    public GymOfferDto getGymOfferOfDetail(GymOfferDetailDto god)
    {
        return modelMapper.map(gymOfferDetailRepository
                .findById(god.getId())
                .orElseThrow(()->new RuntimeException("GymOfferDetail id: "+god.getId()+" does not exist"))
                .getGymOffer(),GymOfferDto.class);
    }

    @Override
    public void save(GymOfferDetailDto gymOfferDetail)
    {
        gymOfferDetailRepository.save(convertToEntity(gymOfferDetail));
    }

    @Override
    public void add(GymOfferDto go, GymOfferDetailDto gymOfferDetail)
    {
        GymOfferDetail god = convertToEntity(gymOfferDetail);
        GymOffer goo = gymOfferRepository.findById(go.getId()).orElseThrow(()->new RuntimeException("GymOffer id:"+go.getId()+" does not exist"));
        god.setGymOffer(goo);
        gymOfferDetailRepository.save(god);
        goo.getGymOfferDetailList().add(god);
        gymOfferRepository.save(goo);
    }

    @Override
    public void edit(Long goid, GymOfferDetailDto gymOfferDetailDto)
    {
        if(!gymOfferDetailRepository.existsById(gymOfferDetailDto.getId())){
            throw new RuntimeException("GymOfferDetail id: "+gymOfferDetailDto.getId()+" does not exist");
        }

        GymOfferDetail god = convertToEntity(gymOfferDetailDto);
        god.setGymOffer(gymOfferRepository.findById(goid).orElseThrow(()->new RuntimeException("GymOffer id: "+goid+" does not exist")));

        gymOfferDetailRepository.save(god);
        logger.info("Edit GymOfferDetail id:"+gymOfferDetailDto.getId());
    }

    @Override
    public void delete(GymOfferDetailDto gymOfferDetail)
    {
        gymOfferDetailRepository.deleteById(gymOfferDetail.getId());
    }

    @Override
    public void delete(Long goid, Long godid)
    {
        gymOfferDetailRepository.deleteById(godid);
    }
}
