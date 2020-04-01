package paczka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paczka.dto.*;
import paczka.model.AttendanceList;
import paczka.service.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "/coach")
public class CoachController {

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
    public CoachController(CanceledClassService canceledClassService, UserService userService, RoleServiceImpl roleService, BrandService brandService, GymOfferService gymOfferService, GymOfferDetailServiceImpl gymOfferDetailService, GymPassServiceImpl gymPassService, MAClassService maClassService, MAOfferService maOfferService, MAOfferDetailServiceImpl maOfferDetailService, MAPassService maPassService, OrderServiceImpl orderService)
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

    @GetMapping(value = "/classes", produces = "application/json")
    public ResponseEntity getClasses(){

        List<MAClassDto> maClassList = maClassService.findAllByCoach(userService.getCurrentUser());
        return new ResponseEntity(maClassList, HttpStatus.OK);
    }

    @GetMapping(value = "/maoffers", produces = "application/json")
    public ResponseEntity getMAOffers(){

        return new ResponseEntity(maOfferService.getMaOffersOfCoach(userService.getCurrentUserDto()), HttpStatus.OK);
    }

    @GetMapping(value = "/class/{id}/clients", produces = "application/json")
    public ResponseEntity getClientListByClass(@PathVariable(value = "id", required = true) long id){

        MAClassDto mc = maClassService.findById(id);
        return new ResponseEntity(mc.getMaOffer().getClients(), HttpStatus.OK);
    }

    @PostMapping(value = "/class/add", produces = "application/json")
    public ResponseEntity addClass(@RequestBody @Valid MAClassDto maClass){

        maClassService.add(maClass);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/class/edit", produces = "application/json")
    public ResponseEntity editClass(@RequestBody @Valid MAClassDto maClass){

        maClassService.edit(maClass);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/class/{id}/delete", produces = "application/json")
    public ResponseEntity deleteClass(@PathVariable(value = "id", required = true) long id){

        maClassService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/class/{id}/cancel", produces = "application/json")
    public ResponseEntity cancelClass(@PathVariable(value = "id", required = true) long id, @RequestBody @Valid CancelDate date){

        MAClassDto mc = maClassService.findById(id);
        canceledClassService.cancelClass(mc, date.getDate());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/class/{id}/attendancelist", produces = "application/json")
    public ResponseEntity attendanceList(@PathVariable(value = "id", required = true) long id){

        return new ResponseEntity(maClassService.attendanceList(maClassService.findById(id)),HttpStatus.OK);
    }

    @PostMapping(value = "/class/{id}/attendancelist", produces = "application/json")
    public ResponseEntity attendancelistPost(@PathVariable(value = "id", required = true) long id, @RequestBody @Valid AttendanceList list){

        maClassService.checkAttendanceList(maClassService.findById(id), list);
        return new ResponseEntity(HttpStatus.OK);
    }
}
