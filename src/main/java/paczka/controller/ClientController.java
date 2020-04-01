package paczka.controller;

import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import paczka.dto.*;
import paczka.entity.*;
import paczka.exception.OrderIncorrectException;
import paczka.exception.PassAlreadyExistsException;
import paczka.model.ChargeRequest;
import paczka.model.PaymentForm;
import paczka.repository.*;
import paczka.service.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.*;

@RestController
@RequestMapping("/client/*")
public class ClientController {

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
    private StripeService stripeService;

    @Autowired
    public ClientController(CanceledClassService canceledClassService, UserService userService, RoleService roleService, BrandService brandService, GymOfferService gymOfferService, GymOfferDetailService gymOfferDetailService, GymPassService gymPassService, MAClassService maClassService, MAOfferService maOfferService, MAOfferDetailService maOfferDetailService, MAPassService maPassService, OrderService orderService, StripeService stripeService)
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
        this.stripeService = stripeService;
    }

    @GetMapping(value = "/brand/{id}", produces = "application/json")
    public BrandDto getBrandById(@PathVariable(value = "id", required = true) long id){

        return brandService.findById(id);
    }

    @PostMapping(value = "/offer/buy", produces = "application/json")
    public ResponseEntity buyGymPass(@Valid @RequestBody ChargeRequest chargeRequest) {

        orderService.makeOrder(chargeRequest, userService.getCurrentUserDto());
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/yourgympasses", produces = "application/json")
    public ResponseEntity getGymPasses(){

        UserDto user = userService.getCurrentUserDto();
        return new ResponseEntity(user.getGymPasses(), HttpStatus.OK);
    }

    @GetMapping(value = "/yourmapasses", produces = "application/json")
    public ResponseEntity getMAPasses(){

        UserDto user = userService.getCurrentUserDto();
        return new ResponseEntity(user.getMaPasses(), HttpStatus.OK);
    }

    @GetMapping(value = "/plan", produces = "application/json")
    public ResponseEntity getPlan() {

        UserDto user = userService.getCurrentUserDto();
        return new ResponseEntity(maClassService.getPlan(user), HttpStatus.OK);
    }

    @GetMapping(value = "/account", produces = "application/json")
    public ResponseEntity account(){

        return new ResponseEntity(userService.getCurrentUser(), HttpStatus.OK);
    }

    @PostMapping(value = {"/account/edit"}, produces = "application/json")
    public ResponseEntity editAccount(@Valid UserDto user) {

        userService.editUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/coaches", produces = "application/json")
    public ResponseEntity coaches(){

        return new ResponseEntity(maClassService.coachesList(userService.getCurrentUserDto()), HttpStatus.OK);
    }

    @PostMapping(value = "/account/delete", produces = "application/json")
    public ResponseEntity deleteAccount(){

        userService.disActiveAccount(userService.getCurrentUserDto());
        return new ResponseEntity(HttpStatus.OK);
    }
}
