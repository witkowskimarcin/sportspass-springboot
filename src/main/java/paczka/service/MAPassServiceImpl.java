package paczka.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import paczka.dto.MAOfferDto;
import paczka.dto.MAPassDto;
import paczka.entity.MAOffer;
import paczka.entity.MAOfferDetail;
import paczka.entity.MAPass;
import paczka.repository.MAOfferDetailRepository;
import paczka.repository.MAOfferRepository;
import paczka.repository.MAPassRepository;

@Service
public class MAPassServiceImpl implements MAPassService {

    private MAPassRepository maPassRepository;
    private MAOfferRepository maOfferRepository;
    private MAOfferDetailRepository maOfferDetailRepository;
    private JdbcTemplate jdbc;
    private ModelMapper modelMapper;

    public MAPassServiceImpl(MAPassRepository maPassRepository, MAOfferRepository maOfferRepository, MAOfferDetailRepository maOfferDetailRepository, JdbcTemplate jdbc, ModelMapper modelMapper)
    {
        this.maPassRepository = maPassRepository;
        this.maOfferRepository = maOfferRepository;
        this.maOfferDetailRepository = maOfferDetailRepository;
        this.jdbc = jdbc;
        this.modelMapper = modelMapper;
    }

    @Override
    public void delete(MAPassDto maPass)
    {
        jdbc.execute("DELETE FROM USER_MA_PASSES WHERE MA_PASSES_ID ="+maPass.getId());
        maPassRepository.delete(convertTEntity(maPass));
    }

    public void deleteEntity(MAPass maPass){

        jdbc.execute("DELETE FROM USER_MA_PASSES WHERE MA_PASSES_ID ="+maPass.getId());
        maPassRepository.delete(maPass);
    }

    @Override
    public MAPassDto convertToDto(MAPass b)
    {
        return modelMapper.map(b, MAPassDto.class);
    }

    @Override
    public MAPass convertTEntity(MAPassDto b)
    {
        return modelMapper.map(b, MAPass.class);
    }

    @Override
    public void save(MAPassDto mp)
    {
        maPassRepository.save(convertTEntity(mp));
    }

    @Override
    public void saveEntity(MAPass mp)
    {
        maPassRepository.save(mp);
    }

    @Override
    public MAOfferDto getMAOffer(MAPassDto mp)
    {
        long id = mp
                .getMaOfferDetail()
                .getId();
        MAOfferDetail mod = maOfferDetailRepository.findById(id).orElseThrow(()->new RuntimeException("MAOfferDetail id: "+id+" does not exist"));
        return modelMapper.map(maOfferRepository
                .findById(mod.getMaOffer().getId())
                .orElseThrow(()->new RuntimeException("MAOffer id: "+mp.getMaOfferDetail().getId()+ " does not exist")), MAOfferDto.class);
    }
}
