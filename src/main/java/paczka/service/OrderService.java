package paczka.service;

import paczka.dto.OrderDto;
import paczka.dto.UserDto;
import paczka.entity.Order;
import paczka.model.ChargeRequest;

public interface OrderService
{
    void save(OrderDto order);
    OrderDto convertToDto(Order o);
    Order convertToEntity(OrderDto o);
    Boolean makeOrder(ChargeRequest chargeRequest, UserDto userDto);
}
