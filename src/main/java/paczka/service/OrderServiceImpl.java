package paczka.service;

import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import paczka.dto.*;
import paczka.entity.MAPass;
import paczka.entity.Order;
import paczka.entity.User;
import paczka.exception.PassAlreadyExistsException;
import paczka.model.ChargeRequest;
import paczka.repository.GymOfferDetailRepository;
import paczka.repository.OrderRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService
{
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private GymOfferDetailService gymOfferDetailService;
    private UserService userService;
    private StripeService stripeService;
    private GymPassService gymPassService;
    private MAOfferDetailService maOfferDetailService;
    private MAPassService maPassService;
    private MAOfferService maOfferService;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, GymOfferDetailService gymOfferDetailService, UserService userService, StripeService stripeService, GymPassService gymPassService, MAOfferDetailService maOfferDetailService, MAPassService maPassService, MAOfferService maOfferService)
    {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.gymOfferDetailService = gymOfferDetailService;
        this.userService = userService;
        this.stripeService = stripeService;
        this.gymPassService = gymPassService;
        this.maOfferDetailService = maOfferDetailService;
        this.maPassService = maPassService;
        this.maOfferService = maOfferService;
    }

    @Override
    public void save(OrderDto order)
    {
        orderRepository.save(convertToEntity(order));
    }

    @Override
    public OrderDto convertToDto(Order o)
    {
        return modelMapper.map(o,OrderDto.class);
    }

    @Override
    public Order convertToEntity(OrderDto o)
    {
        return modelMapper.map(o,Order.class);
    }

    @Override
    public Boolean makeOrder(ChargeRequest chargeRequest, UserDto user)
    {
        if(chargeRequest.getId()>0) {

            if (chargeRequest.getType().equals("gym")) {

                GymOfferDetailDto god = gymOfferDetailService.findById(chargeRequest.getId());

                GymPassDto gp = new GymPassDto();
                Date date = new Date();
                gp.setStartDate(new Date());
                date.setMonth(date.getMonth() + god.getMonths());
                gp.setEndDate(date);
                gp.setGymOfferDetail(god);

                if (user.getGymPasses() == null) user.setGymPasses(new ArrayList<>());
                if (userService.checkIfUserHasGymPass(god,user)) throw new PassAlreadyExistsException();

                Map json = new HashMap();

                chargeRequest.setDescription(gymOfferDetailService.getGymOfferOfDetail(god).getBrand().getName()+", "+god.getText()+", type: gym");
                chargeRequest.setCurrency(ChargeRequest.Currency.PLN);
                chargeRequest.setAmount((int) (god.getPrice()*100));
                chargeRequest.setStripeEmail(user.getEmail());
                Charge charge = null;
                try
                {
                    charge = stripeService.charge(chargeRequest);
                } catch (AuthenticationException e)
                {
                    e.printStackTrace();
                } catch (InvalidRequestException e)
                {
                    e.printStackTrace();
                } catch (APIConnectionException e)
                {
                    e.printStackTrace();
                } catch (CardException e)
                {
                    e.printStackTrace();
                } catch (APIException e)
                {
                    e.printStackTrace();
                }
                json.put("status", charge.getStatus());
                json.put("chargeId", charge.getId());
                json.put("balance_transaction", charge.getBalanceTransaction());

                if(charge.getStatus().equals("succeeded")) {
                    user.getGymPasses().add(gp);
                    gymPassService.save(gp);
                    userService.editUser(user);

                    OrderDto order = new OrderDto();
                    order.setType("gym");
                    order.setUser(user);
                    order.setOfferId(god.getId());
                    order.setPrice(god.getPrice());
                    order.setOrderDate(new Date());
                    save(order);

                    json.put("success", true);
                }

//                return new ResponseEntity(json, HttpStatus.OK);

            } else if (chargeRequest.getType().equals("ma")) {

                MAOfferDetailDto mod = maOfferDetailService.findById(chargeRequest.getId());
//                } catch (Exception e) {
//                    throw new OrderIncorrectException();
//                }

//                MAOfferDto mo = mod.getMaOffer();
                MAOfferDto mo = maOfferDetailService.getMaOfferOfDetail(mod);
                mo.getClients().add(user);

                maOfferService.save(mo);

                MAPassDto mp = new MAPassDto();
                Date date = new Date();
                mp.setStartDate(new Date());
                date.setMonth(date.getMonth() + 1);
                mp.setEndDate(date);
                mp.setMaOfferDetail(mod);
                mp.setEntries(mod.getEntries());

                if (user.getMaPasses() == null) user.setMaPasses(new ArrayList<>());

                for (MAPassDto item : user.getMaPasses()) {

                    if (maOfferDetailService.getMaOfferOfDetail(item.getMaOfferDetail())
                            .equals(maOfferDetailService.getMaOfferOfDetail(mp.getMaOfferDetail()))) {
                        throw new PassAlreadyExistsException();
                    }
                }

                Map json = new HashMap();

                chargeRequest.setDescription(maOfferDetailService.getMaOfferOfDetail(mod).getBrand().getName()+", "+mod.getText()+", type: ma");
                chargeRequest.setCurrency(ChargeRequest.Currency.PLN);
                chargeRequest.setAmount((int) (mod.getPrice()*100));
                chargeRequest.setStripeEmail(user.getEmail());
                Charge charge = null;
                try
                {
                    charge = stripeService.charge(chargeRequest);
                } catch (AuthenticationException e)
                {
                    e.printStackTrace();
                } catch (InvalidRequestException e)
                {
                    e.printStackTrace();
                } catch (APIConnectionException e)
                {
                    e.printStackTrace();
                } catch (CardException e)
                {
                    e.printStackTrace();
                } catch (APIException e)
                {
                    e.printStackTrace();
                }
                json.put("status", charge.getStatus());
                json.put("chargeId", charge.getId());
                json.put("balance_transaction", charge.getBalanceTransaction());

                if(charge.getStatus().equals("succeeded")) {

                    user.getMaPasses().add(mp);

                    maPassService.save(mp);
                    userService.editUser(user);

                    OrderDto order = new OrderDto();
                    order.setType("ma");
                    order.setUser(user);
                    order.setOfferId(mod.getId());
                    order.setPrice(mod.getPrice());
                    order.setOrderDate(new Date());
                    save(order);

                    return true;
                }
            }
        }
        return false;
    }
}
