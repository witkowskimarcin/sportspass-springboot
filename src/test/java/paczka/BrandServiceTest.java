//package paczka;
//
//import org.hamcrest.Matchers;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import paczka.dto.BrandDto;
//import paczka.entity.Brand;
//import paczka.repository.BrandRepository;
//import paczka.service.BrandService;
//import paczka.service.BrandServiceImpl;
//import paczka.service.GymOfferService;
//import paczka.service.MAOfferService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
////@ExtendWith(MockitoExtension.class)
////@RunWith(JUnitPlatform.class)
//@SpringBootTest
//public class BrandServiceTest
//{
//    @Mock private JdbcTemplate jdbc;
//    @Mock private BrandRepository brandRepository;
//    @Mock private MAOfferService maOfferService;
//    @Mock private GymOfferService gymOfferService;
//    @Mock private ModelMapper modelMapper;
//
//    @InjectMocks
//    BrandService brandService = new BrandServiceImpl(jdbc, brandRepository, maOfferService, gymOfferService, modelMapper);
//
//    @Before
//    public void init(){
//        // empty
//    }
//
//    public List<Brand> prepareMockData(){
//
//        Brand b1 = new Brand();
//        b1.setId(1);
//        b1.setName("Brand1");
//        Brand b2 = new Brand();
//        b2.setId(2);
//        b2.setName("Brand2");
//        Brand b3 = new Brand();
//        b3.setId(3);
//        b3.setName("Brand3");
//
//        List<Brand> brands = new ArrayList<>();
//        brands.add(b1);
//        brands.add(b2);
//        brands.add(b3);
//
//        return brands;
//    }
//
//    @Test
//    public void findAll(){
//
//        //when
//        when(brandRepository.findAll()).thenReturn(prepareMockData());
//        List<BrandDto> brands = brandService.findAll();
//
//        //then
//        Assert.assertThat(brands,Matchers.hasSize(3));
//    }
//}
