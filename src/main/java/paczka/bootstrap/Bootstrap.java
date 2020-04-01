package paczka.bootstrap;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import paczka.entity.*;
import paczka.repository.*;
import paczka.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class Bootstrap {
	
	private final static String sqlCreatePersistanceTable = "CREATE TABLE IF NOT EXISTS  `persistent_logins` (\r\n" + 
			"  `username` varchar(64) NOT NULL,\r\n" + 
			"  `series` varchar(64) NOT NULL,\r\n" + 
			"  `token` varchar(64) NOT NULL,\r\n" + 
			"  `last_used` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,\r\n" + 
			"  PRIMARY KEY  (`series`)\r\n" + 
			") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private CanceledClassRepository canceledClassRepository;

	@Autowired
	private GymOfferRepository gymOfferRepository;

	@Autowired
	private GymOfferDetailRepository gymOfferDetailRepository;

	@Autowired
	private GymPassRepository gymPassRepository;

	@Autowired
	private MAClassRepository maClassRepository;

	@Autowired private MAOfferRepository maOfferRepository;

	@Autowired private MAOfferDetailRepository maOfferDetailRepository;

	@Autowired private MAPassRepository maPassRepository;

	//@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@PostConstruct
	public void init(){

		boolean addData = true;
		
		// dodawanie roli uzytkownikow
		Role roleAdmin = new Role();
		roleAdmin.setRole("ADMIN");
		
		Role roleUser = new Role();
		roleUser.setRole("CLIENT");

		Role roleCoach = new Role();
		roleCoach.setRole("COACH");

		Role roleOwner = new Role();
		roleOwner.setRole("OWNER");
		

		if(roleRepository.findByRole("ADMIN")==null) {

			roleRepository.save(roleAdmin);
			roleRepository.save(roleUser);
			roleRepository.save(roleCoach);
			roleRepository.save(roleOwner);
			System.out.println("Dodano role do bazy");
		}
		else {
			System.out.println("Istnieja role");
		}
		
		// dodanie tabeli Persistance
		//Map<String, Object> paramMap = new HashMap<String, Object>();
		// namedParameterJdbcTemplate.update(sqlCreatePersistanceTable, paramMap);
		
		// dodawanie admina
		User admin = new User();
		admin.setEmail("admin@admin.pl");
		admin.setFirstname("admin");
		admin.setLastname("admin");
		admin.setPassword("qwerty");
		admin.setActive(1);

		// dodawanie ownera
		User owner = new User();
		owner.setEmail("owner@owner.pl");
		owner.setFirstname("owner");
		owner.setLastname("owner");
		owner.setPassword("qwerty");

		// dodawanie ownera
		User owner2 = new User();
		owner2.setEmail("owner2@owner.pl");
		owner2.setFirstname("owner2");
		owner2.setLastname("owner2");
		owner2.setPassword("qwerty");

		// dodawanie coacha
		User coach = new User();
		coach.setEmail("coach@coach.pl");
		coach.setFirstname("coach");
		coach.setLastname("coach");
		coach.setPassword("qwerty");
		coach.setActive(1);

		// dodawanie admina
		User client = new User();
		client.setEmail("client@client.pl");
		client.setFirstname("client");
		client.setLastname("client");
		client.setPassword("qwerty");
		client.setActive(1);
		
		if(userRepository.findByEmail("admin@admin.pl")==null) {
			userService.saveAdmin(admin);
			if(addData) {
				userService.saveOwner(owner);
				userService.saveOwner(owner2);
				userService.saveCoach(coach);
				userService.saveClient(client);
			}
			System.out.println("Admin nie istnieje, utworzono nowego admina");
		}
		else {
			System.out.println("Admin istnieje");
		}

		if(addData) {

			Brand b1 = new Brand();
			b1.setName("Fit Fabric");

			byte[] fileContent = null;
			try {
				ClassPathResource imgFile = new ClassPathResource("images/fitfabric.jpg");
				fileContent = StreamUtils.copyToByteArray(imgFile.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			b1.setImage(fileContent);
			b1.setOwner(owner);

			Brand b2 = new Brand();
			b2.setName("Brutals");

			fileContent = null;
			try {
				ClassPathResource imgFile = new ClassPathResource("images/brutals.jpg");
				fileContent = StreamUtils.copyToByteArray(imgFile.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			b2.setImage(fileContent);
			b2.setOwner(owner2);

			Brand b3 = new Brand();
			b3.setName("McFit");

			fileContent = null;
			try {
				ClassPathResource imgFile = new ClassPathResource("images/mcfit.jpg");
				fileContent = StreamUtils.copyToByteArray(imgFile.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			b3.setImage(fileContent);
			b3.setOwner(owner2);

			GymOfferDetail god1 = new GymOfferDetail();
			god1.setPrice(20.0);
			god1.setText("1 month pass");
			god1.setMonths(1);

			GymOfferDetail god2 = new GymOfferDetail();
			god2.setPrice(50.0);
			god2.setText("3 month pass");
			god2.setMonths(3);

			GymOfferDetail god3 = new GymOfferDetail();
			god3.setPrice(80.0);
			god3.setText("6 month pass");
			god3.setMonths(6);


			GymOfferDetail god4 = new GymOfferDetail();
			god4.setPrice(150.0);
			god4.setText("12 month pass");
			god4.setMonths(12);

			GymOffer go1 = new GymOffer();
			go1.setBrand(b1);
			go1.setDescription("Fit Fabric profesjonalna siłownia");
			go1.setLocation("Łódź");
			go1.setStreet("Zielona 11/1");
			go1.setGymOfferDetailList(new ArrayList<>());

			GymOffer go2 = new GymOffer();
			go2.setBrand(b3);
			go2.setDescription("McFit profesjonalna siłownia");
			go2.setLocation("Łódź");
			go2.setStreet("Gdańska 19/2");
			go2.setGymOfferDetailList(new ArrayList<>());

			MAOfferDetail mod1 = new MAOfferDetail();
			mod1.setText("1 entry pass");
			mod1.setPrice(5.0);
			mod1.setDescription("Wejście jednorazowe");
			mod1.setEntries(1);

			MAOfferDetail mod2 = new MAOfferDetail();
			mod2.setText("4 entry pass");
			mod2.setPrice(10.0);
			mod2.setDescription("4 wejścia");
			mod2.setEntries(4);

			MAOfferDetail mod3 = new MAOfferDetail();
			mod3.setText("12 entry pass");
			mod3.setPrice(20.0);
			mod3.setDescription("12 wejścia");
			mod3.setEntries(12);

			MAOfferDetail mod4 = new MAOfferDetail();
			mod4.setText("OPEN pass");
			mod4.setPrice(30.0);
			mod4.setDescription("Karnet OPEN upoważnia do uczęszczania na dowolne zajęcia");
			mod4.setEntries(9999);

			MAOffer mo1 = new MAOffer();
			mo1.setBrand(b2);
			mo1.setLocation("Łódź");
			mo1.setStreet("Legionów 20/2");
			mo1.setDescription("Sporty walki");
			mo1.setMaOfferDetailList(new ArrayList<>());
			mo1.setCoaches(new ArrayList<>());
			mo1.setClients(new ArrayList<>());

			MAClass mc1 = new MAClass();
			mc1.setMaOffer(mo1);
			mc1.setNumberOfDay(1);
			Time t = new Time(0);
			t.setHours(18);
			mc1.setTime(t);
			mc1.setDescription("Kickboxing");
			mc1.setCoach(coach);

			MAClass mc2 = new MAClass();
			mc2.setMaOffer(mo1);
			mc2.setNumberOfDay(1);
			Time t2 = new Time(0);
			t2.setHours(20);
			mc2.setTime(t2);
			mc2.setDescription("Boks");
			mc2.setCoach(coach);

			mo1.setCoaches(new ArrayList<>());
			mo1.getCoaches().add(coach);

			GymPass gp = new GymPass();
			Date date = new Date();
			gp.setStartDate(new Date());
			date.setMonth(date.getMonth() + god1.getMonths());
			gp.setEndDate(date);
			gp.setGymOfferDetail(god2);
			if (client.getGymPasses() == null) client.setGymPasses(new ArrayList<>());
			client.getGymPasses().add(gp);

			MAPass mp = new MAPass();
			mp.setStartDate(new Date());
			date.setMonth(date.getMonth() + god1.getMonths());
			mp.setEndDate(date);
			mp.setEntries(mod3.getEntries());
			mp.setMaOfferDetail(mod3);
			if (client.getMaPasses() == null) client.setMaPasses(new ArrayList<>());
			client.getMaPasses().add(mp);


			if (brandRepository.findAll().isEmpty()) {

				brandRepository.save(b1);
				brandRepository.save(b2);
				brandRepository.save(b3);

				//-------------

				gymOfferRepository.save(go1);
				gymOfferRepository.save(go2);

				god1.setGymOffer(go1);
				god2.setGymOffer(go2);
				god3.setGymOffer(go1);
				god4.setGymOffer(go2);

				gymOfferDetailRepository.save(god1);
				gymOfferDetailRepository.save(god2);
				gymOfferDetailRepository.save(god3);
				gymOfferDetailRepository.save(god4);

				go1.getGymOfferDetailList().add(god1);
				go1.getGymOfferDetailList().add(god3);

				go2.getGymOfferDetailList().add(god2);
				go2.getGymOfferDetailList().add(god4);

				gymOfferRepository.save(go1);
				gymOfferRepository.save(go2);


				//--------------

				maOfferRepository.save(mo1);

				mod1.setMaOffer(mo1);
				mod2.setMaOffer(mo1);
				mod3.setMaOffer(mo1);
				mod4.setMaOffer(mo1);

				maOfferDetailRepository.save(mod1);
				maOfferDetailRepository.save(mod2);
				maOfferDetailRepository.save(mod3);
				maOfferDetailRepository.save(mod4);

				mo1.getMaOfferDetailList().add(mod1);
				mo1.getMaOfferDetailList().add(mod2);
				mo1.getMaOfferDetailList().add(mod3);
				mo1.getMaOfferDetailList().add(mod4);

				mo1.getClients().add(client);

				maOfferRepository.save(mo1);

				maClassRepository.save(mc1);
				maClassRepository.save(mc2);

				gymPassRepository.save(gp);
				maPassRepository.save(mp);

				userRepository.save(client);

				//mo1.getClients().add(client);
				//maOfferRepository.save(mo1);

				b1.setGymOfferList(new ArrayList<>());
				b1.setMaOfferList(new ArrayList<>());
				b1.getGymOfferList().add(go1);

				b3.setGymOfferList(new ArrayList<>());
				b3.setMaOfferList(new ArrayList<>());
				b3.getGymOfferList().add(go2);

				b2.setGymOfferList(new ArrayList<>());
				b2.setMaOfferList(new ArrayList<>());
				b2.getMaOfferList().add(mo1);

				brandRepository.save(b1);
				brandRepository.save(b2);
				brandRepository.save(b3);
			}

			System.out.println("Zapisano dane do bazy");

//		System.out.println(mod1.getMaOffer().getId());

		}
		}
}
