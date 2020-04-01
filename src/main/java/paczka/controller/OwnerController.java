package paczka.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import paczka.dto.*;
import paczka.service.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value ="/owner")
public class OwnerController {

    private CanceledClassService canceledClassService;
    private UserService userService;
    private RoleService roleService;
    private BrandService brandService;
    private GymOfferService gymOfferService;
    private GymOfferDetailService gymOfferDetailService;
    private GymPassService gymPassService;
    private MAClassService maClassService;
    private MAOfferService maOfferService;
    private MAOfferDetailService maOfferDetailService;
    private MAPassService maPassService;
    private OrderService orderService;

    public OwnerController(CanceledClassService canceledClassService, UserService userService, RoleService roleService, BrandService brandService, GymOfferService gymOfferService, GymOfferDetailService gymOfferDetailService, GymPassService gymPassService, MAClassService maClassService, MAOfferService maOfferService, MAOfferDetailService maOfferDetailService, MAPassService maPassService, OrderService orderService)
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

    @GetMapping(value = "role/{id}/users")
    public ResponseEntity<List<UserDto>> admins(@PathVariable("id") Integer id) {

        RoleDto roleAdmin = roleService.findById(id);
        List<UserDto> admins = userService.findUsersByRole(roleAdmin);

        return new ResponseEntity(admins, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}/brands", produces = "application/json")
    @ResponseBody
    public ResponseEntity getBrandByOwner(@PathVariable("id") Long id){

        UserDto owner = userService.findById(id);
        BrandDto brand = brandService.getBrandByOwner(owner);
        return new ResponseEntity(brand, HttpStatus.OK);
    }

    // !USERS

    // GYMOFFERS

    @GetMapping(value = "/brand/gymoffers", produces = "application/json")
    @ResponseBody
    public ResponseEntity getGymOfferListByBrandId(){

        BrandDto brand = brandService.getBrandByOwner(userService.getCurrentUserDto());
        return new ResponseEntity(brandService.getGymOfferList(brandService.findById(brand.getId())), HttpStatus.OK);
    }

    @GetMapping(value = "/gymoffer/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity getGymOffer(@PathVariable("id") Long id){

        return new ResponseEntity(gymOfferService.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "brand/gymoffer/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity addGymOffer(@Valid @RequestBody GymOfferDto gymOffer){

        BrandDto brand = brandService.getBrandByOwner(userService.getCurrentUserDto());
        gymOfferService.add(brand.getId(), gymOffer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "gymoffer/edit", produces = "application/json")
    @ResponseBody
    public ResponseEntity editGymOffer(@Valid @RequestBody GymOfferDto gymOffer){

        gymOfferService.edit(gymOffer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/gymoffer/{id}/delete", produces = "application/json")
    @ResponseBody
    public ResponseEntity deleteGymOffer(@PathVariable("id") Long id){

        GymOfferDto gymOffer = gymOfferService.findById(id);
        gymOfferService.delete(gymOffer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/gymoffer/{id}/details", produces = "application/json")
    @ResponseBody
    public ResponseEntity getGymOfferDetailsByGymOffer(@PathVariable("id") Long id){

        GymOfferDto gymOffer = gymOfferService.findById(id);
        return new ResponseEntity(gymOfferService.getGymOfferDetails(gymOffer), HttpStatus.OK);
    }

    @GetMapping(value = "/gymofferdetail/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity getGymOfferDetail(@PathVariable("id") Long id){

        GymOfferDetailDto gymOfferDetail = gymOfferDetailService.findById(id);
        return new ResponseEntity(gymOfferDetail, HttpStatus.OK);
    }

    @PostMapping(value = "gymoffer/{id}/gymofferdetail/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity addGymOfferDetail(@PathVariable("bid") Long id, @Valid @RequestBody GymOfferDetailDto gymOfferDetailDto){

        GymOfferDto gymOffer = gymOfferService.findById(id);
        gymOfferDetailService.add(gymOffer, gymOfferDetailDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "gymoffer/{goid}/gymofferdetail/{id}/edit", produces = "application/json")
    @ResponseBody
    public ResponseEntity editGymOfferDetail(@PathVariable("goid") Long goid, @Valid @RequestBody GymOfferDetailDto gymOfferDetail){

        gymOfferDetailService.edit(goid, gymOfferDetail);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "gymoffer/{goid}/detail/{godid}/delete", produces = "application/json")
    @ResponseBody
    public ResponseEntity deleteGymOfferDetail(@PathVariable("goid") Long goid, @PathVariable("bid") Long godid){

        gymOfferDetailService.delete(goid, godid);
        return new ResponseEntity(HttpStatus.OK);
    }

    // !GYMOFFERS

    // GYMPASSES

    @GetMapping(value = "/user/{id}/gympasses", produces = "application/json")
    @ResponseBody
    public ResponseEntity getGymPassesOfUser(@PathVariable("id") Long id){

        UserDto user = userService.findById(id);
        return new ResponseEntity(gymPassService.getGymPassesOfUser(user), HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/{uid}/gympass/{gpid}/delete", produces = "application/json")
    @ResponseBody
    public ResponseEntity deleteGymPassFromUser(@PathVariable("uid") Long uid, @PathVariable("gpid") Long gpid){

        gymPassService.delete(uid,gpid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/gympass/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity getGymPass(@PathVariable("id") Long id){

        GymPassDto gymPass = gymPassService.findById(id);
        return new ResponseEntity(gymPass, HttpStatus.OK);
    }

    @PostMapping(value = "user/{uid}/gympass/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity addGymPassToUser(@PathVariable("uid") Long uid,
                                           @Valid @RequestBody GymPassDto gymPass){

        gymPassService.addPassToUser(userService.findById(uid), gymPass);
        return new ResponseEntity(HttpStatus.OK);
    }

    // !GYMPASSES
}
