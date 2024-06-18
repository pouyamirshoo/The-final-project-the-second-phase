package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.entity.Customer;
import com.example.finalprojectsecondphase.entity.Offer;
import com.example.finalprojectsecondphase.entity.Order;
import com.example.finalprojectsecondphase.entity.SubDuty;
import com.example.finalprojectsecondphase.entity.enums.OfferCondition;
import com.example.finalprojectsecondphase.entity.enums.OrderCondition;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.exception.WrongDateInsertException;
import com.example.finalprojectsecondphase.exception.WrongInputPriceException;
import com.example.finalprojectsecondphase.repository.OrderRepository;
import com.example.finalprojectsecondphase.util.validation.CreatAndValidationDate;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final CreatAndValidationDate creatAndValidationDate;
    private final OfferService offerService;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public void validate(Order order) {
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (violations.isEmpty()) {
            orderRepository.save(order);
            log.info("order saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Order> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new InvalidInputInformationException("some of inputs are not valid");
        }
    }

    public boolean checkOrderPrice(int price, SubDuty subDuty) {
        int subDutyPrice = subDuty.getPrice();
        if (price >= subDutyPrice)
            return true;
        else throw new WrongInputPriceException("order price can not be less than default price");
    }

    public void saveOrder(Order order) {
        if (checkOrderPrice(order.getOrderPrice(), order.getSubDuty())
                && creatAndValidationDate.checkNotPastTime(order.getNeedExpert())) {
            validate(order);
        }
    }

    public List<Order> findCustomerOrders(Customer customer) {
        List<Order> orders = orderRepository.findByCustomer(customer);
        if (orders.size() > 0)
            return orders;
        else
            throw new NullPointerException("no order for this customer");
    }

    public Order findById(int id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException("order with id " + id + " not founded"));
    }

    @Transactional
    public List<Order> findByOrderCondition(OrderCondition orderCondition) {
        List<Order> orders = orderRepository.findByOrderCondition(orderCondition);
        if (orders.size() > 0)
            return orders;
        else
            throw new NullPointerException("there is no order with " + orderCondition + " now");
    }

    public List<Order> findSubDutyOrders(SubDuty subDuty) {
        List<Order> orders = orderRepository.findBySubDuty(subDuty);
        if (orders.size() > 0)
            return orders;
        else
            throw new NullPointerException("this subDuty do not have any order yet");
    }

    public void updateOrderCondition(OrderCondition orderCondition, Order order) {
        order.setOrderCondition(orderCondition);
        validate(order);
    }

    public void makeOrderConditionWaitForAccept(Order order) {
        List<Offer> offers = offerService.findOrderOffers(order);
        if (offers.get(0) != null) {
            updateOrderCondition(OrderCondition.WAIT_FOR_ACCEPT, order);
        }
    }

    public void makeOrderOngoing(Order order) {
        Offer offer = offerService.findByOrderAndOfferCondition(order, OfferCondition.ACCEPTED);
        if (order.getNeedExpert().isEqualNow() || order.getNeedExpert().isBeforeNow()) {
            updateOrderCondition(OrderCondition.ONGOING, order);
            offerService.updateOfferCondition(OfferCondition.ONGOING, offer);
        } else
            throw new WrongDateInsertException("order can not be ongoing before need expert time");
    }
}
