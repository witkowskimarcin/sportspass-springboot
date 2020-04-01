package paczka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import paczka.dto.*;
import paczka.entity.*;
import paczka.model.AddBrandForm;
import paczka.repository.*;
import paczka.service.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

    @Autowired private RoleService roleService;
    @Autowired private UserService userService;
    @Autowired private BrandService brandService;
    @Autowired private GymOfferService gymOfferService;
    @Autowired private MAOfferService maOfferService;
    @Autowired private GymOfferDetailService gymOfferDetailService;
    @Autowired private GymPassService gymPassService;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


    // ROLES

    @GetMapping(value = "/roles")
    public ResponseEntity<List<RoleDto>> roles() {

        return new ResponseEntity(roleService.findAll(), HttpStatus.OK);
    }

    // !ROLES

    // USERS

    @PostMapping(value = "/user/{id}/role/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity addRoleToUser(@PathVariable("id") Long id, @Valid @RequestBody RoleDto role) {

        userService.addRoleToUser(userService.findById(id), role);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/{uid}/role/{rid}/delete", produces = "application/json")
    @ResponseBody
    public ResponseEntity deleteRoleFromUser(@PathVariable("uid") Long uid, @PathVariable("rid") Integer rid) {

        userService.deleteRoleFromUser(uid, rid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/admins")
    public ResponseEntity<List<UserDto>> admins() {

        RoleDto roleAdmin = roleService.findByRole("ADMIN");
        List<UserDto> admins = userService.findUsersByRole(roleAdmin);

        return new ResponseEntity(admins, HttpStatus.OK);
    }

    @GetMapping(value = "role/{id}/users")
    public ResponseEntity<List<UserDto>> admins(@PathVariable("id") Integer id) {

        RoleDto roleAdmin = roleService.findById(id);
        List<UserDto> admins = userService.findUsersByRole(roleAdmin);

        return new ResponseEntity(admins, HttpStatus.OK);
    }

    @PutMapping(value = "/user/{id}/disactive", produces = "application/json")
    @ResponseBody
    public ResponseEntity disactiveUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto user) {

        userService.disActiveAccount(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}/brands", produces = "application/json")
    @ResponseBody
    public ResponseEntity getBrandByOwner(@PathVariable("id") Long id){

        UserDto owner = userService.findById(id);
        BrandDto brand = brandService.getBrandByOwner(owner);
        return new ResponseEntity(brand, HttpStatus.OK);
    }

    // !USERS

    // BRANDS

    @GetMapping(value = "/brands", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<BrandDto>> getBrands(){

        List<BrandDto> brands = brandService.findAll();
        return new ResponseEntity(brands, HttpStatus.OK);
    }

    @PostMapping(value = "/brand/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity addBrand(@Valid @RequestBody BrandDto brand){

        brandService.save(brand);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/brand/{id}/delete", produces = "application/json")
    @ResponseBody
    public ResponseEntity deleteBrand(@PathVariable("id") Long id){

        brandService.delete(brandService.findById(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/brand/edit", produces = "application/json")
    @ResponseBody
    public ResponseEntity editBrand(@Valid @RequestBody BrandDto brand){

        brandService.edit(brand);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/brand/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity getBrand(@PathVariable("id") Long id){

        return new ResponseEntity(brandService.findById(id), HttpStatus.OK);
    }

    // GYMOFFERS

    @GetMapping(value = "/brand/{id}/gymoffers", produces = "application/json")
    @ResponseBody
    public ResponseEntity getGymOfferListByBrandId(@PathVariable("id") Long id){

        return new ResponseEntity(brandService.getGymOfferList(brandService.findById(id)), HttpStatus.OK);
    }

    @GetMapping(value = "/gymoffer/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity getGymOffer(@PathVariable("id") Long id){

        return new ResponseEntity(gymOfferService.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "brand/{bid}/gymoffer/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity addGymOffer(@PathVariable("bid") Long id, @Valid @RequestBody GymOfferDto gymOffer){

        System.out.println("Tutaj: "+gymOffer.getDescription());
        logger.info("Tutaj: "+gymOffer.getDescription());
        gymOfferService.add(id, gymOffer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "gymoffer/edit", produces = "application/json")
    @ResponseBody
    public ResponseEntity editGymOffer(@Valid @RequestBody GymOfferDto gymOffer){

        gymOfferService.edit(gymOffer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "gymoffer/{id}/delete", produces = "application/json")
    @ResponseBody
    public ResponseEntity deleteGymOffer(@PathVariable("id") Long id){

        GymOfferDto gymOffer = gymOfferService.findById(id);
        gymOfferService.delete(gymOffer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "gymoffer/{id}/details", produces = "application/json")
    @ResponseBody
    public ResponseEntity getGymOfferDetailsByGymOffer(@PathVariable("id") Long id){

        GymOfferDto gymOffer = gymOfferService.findById(id);
        return new ResponseEntity(gymOfferService.getGymOfferDetails(gymOffer), HttpStatus.OK);
    }

    @GetMapping(value = "gymofferdetail/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity getGymOfferDetail(@PathVariable("id") Long id){

        GymOfferDetailDto gymOfferDetail = gymOfferDetailService.findById(id);
        return new ResponseEntity(gymOfferDetail, HttpStatus.OK);
    }

    @PostMapping(value = "gymoffer/{id}/gymofferdetail/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity addGymOfferDetail(@PathVariable("id") Long id, @Valid @RequestBody GymOfferDetailDto gymOfferDetailDto){

        GymOfferDto gymOffer = gymOfferService.findById(id);
        gymOfferDetailService.add(gymOffer, gymOfferDetailDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "gymoffer/{goid}/gymofferdetail/{id}/edit", produces = "application/json")
    @ResponseBody
    public ResponseEntity editGymOfferDetail(@PathVariable("goid") Long goid,
                                             @PathVariable("id") Long id,
                                             @Valid @RequestBody GymOfferDetailDto gymOfferDetail){

        gymOfferDetail.setId(id);
        gymOfferDetailService.edit(goid, gymOfferDetail);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "gymoffer/{goid}/detail/{godid}/delete", produces = "application/json")
    @ResponseBody
    public ResponseEntity deleteGymOfferDetail(@PathVariable("goid") Long goid, @PathVariable("godid") Long godid){

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
