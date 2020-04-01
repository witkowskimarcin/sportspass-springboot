package paczka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import paczka.dto.*;
import paczka.entity.*;
import paczka.service.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/*")
public class HomeController {

	private CanceledClassService canceledClassService;
	private UserService userService;
	private RoleServiceImpl roleService;
	private BrandService brandService;
	private GymOfferService gymOfferService;
	private GymOfferDetailServiceImpl gymOfferDetailService;
	private GymPassServiceImpl gymPassService;
	private MAClassService maClassService;
	private MAOfferService maOfferService;
	private MAOfferDetailServiceImpl maOfferDetailService;
	private MAPassService maPassService;
	private OrderServiceImpl orderService;

	@Autowired
	public HomeController(CanceledClassService canceledClassService, UserService userService, RoleServiceImpl roleService, BrandService brandService, GymOfferService gymOfferService, GymOfferDetailServiceImpl gymOfferDetailService, GymPassServiceImpl gymPassService, MAClassService maClassService, MAOfferService maOfferService, MAOfferDetailServiceImpl maOfferDetailService, MAPassService maPassService, OrderServiceImpl orderService)
	{
		this.canceledClassService = canceledClassService;
		this.userService = userService;
		this.roleService = roleService;
		this.brandService = brandService;
		this.gymOfferService = gymOfferService;
		this.gymOfferDetailService = gymOfferDetailService;
		this.gymPassService = gymPassService;
		this.maClassService = maClassService;
		this.maOfferService = maOfferService;
		this.maOfferDetailService = maOfferDetailService;
		this.maPassService = maPassService;
		this.orderService = orderService;
	}

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@GetMapping(value = "/")
	public String home() {

		logger.info("Spring Android Basic Auth");
		return "home";
	}

	@PostMapping("/forgotpassword")
	@ResponseBody
	public ResponseEntity forgotPassword() {
		userService.forgotPassword(userService.getCurrentUserDto());
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping(value = "/register")
	@ResponseBody
	public ResponseEntity register(@Valid @RequestBody UserDto user) {

		userService.saveClient(userService.convertToEntity(user));
		return new ResponseEntity(user, HttpStatus.CREATED);
	}

	@GetMapping(value = "/login",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity login(){

		UserDto currentUser = userService.getCurrentUserDto();
		return ResponseEntity.ok(currentUser);
	}

	@PostMapping(value = "/gymoffers", produces = "application/json")
	public ResponseEntity<List<GymOfferDto>> getGymOfferListByCity(@RequestBody CityDto city){

		return new ResponseEntity<>(gymOfferService.findAllByLocation(city.getCity()),HttpStatus.OK);
	}

	@PostMapping(value = "/maoffers", produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<MAOfferDto>> getMAOfferListByCity(@RequestBody CityDto city){

		return new ResponseEntity<>(maOfferService.findAllByLocation(city.getCity()),HttpStatus.OK);
	}

	@GetMapping(value = "/gymOffer/{id}/details", produces = "application/json")
	@ResponseBody
	public List<GymOfferDetailDto> getGymOfferDetailList(@PathVariable("id") Long id){

		return gymOfferDetailService.getGymOfferDetailListOfOffer(id);
	}

	@GetMapping(value = "/maOffer/{id}/details", produces = "application/json")
	@ResponseBody
	public List<MAOfferDetailDto> getMAOfferDetailList(@PathVariable("id") Long id){

		return maOfferDetailService.getMaOfferDetailListOfOffer(id);
	}
}
