package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.Customer;
import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.Offer;
import com.example.finalprojectsecondphase.entity.Order;
import com.example.finalprojectsecondphase.entity.SubDuty;
import com.example.finalprojectsecondphase.entity.enums.BestTime;
import com.example.finalprojectsecondphase.entity.enums.OfferCondition;
import com.example.finalprojectsecondphase.entity.enums.OrderCondition;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.exception.WrongDateInsertException;
import com.example.finalprojectsecondphase.exception.WrongInputPriceException;
import com.example.finalprojectsecondphase.service.*;
import com.example.finalprojectsecondphase.util.validation.CreatAndValidationDate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class OrderClassTest {

    private static com.example.finalprojectsecondphase.entity.Order sampleOrder;
    private static com.example.finalprojectsecondphase.entity.Order forceOrder;

    private static Offer firstOffer;

    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    RequestService requestService;
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    OfferService offerService;
    @Autowired
    ExpertService expertService;
    @Autowired
    CreatAndValidationDate creatAndValidationDate;

    @BeforeAll
    public static void makeOrderAndOffer() {

        sampleOrder = com.example.finalprojectsecondphase.entity.Order.builder()
                .description("need a very good person")
                .bestTime(BestTime.MORNING)
                .build();

        forceOrder = com.example.finalprojectsecondphase.entity.Order.builder()
                .description("need a very good person")
                .bestTime(BestTime.MORNING)
                .build();

        firstOffer = Offer.builder()
                .offerPrice(100000)
                .takeLong(1)
                .build();
    }

    @DisplayName("test for save a correct order")
    @org.junit.jupiter.api.Order(1)
    @Test
    public void saveAnOrder() {
        Customer customer = customerService.findById(1);
        SubDuty subDuty = subDutyService.findById(1);
        sampleOrder.setCustomer(customer);
        sampleOrder.setSubDuty(subDuty);
        sampleOrder.setDateCreatOrder(creatAndValidationDate.currentTime());
        sampleOrder.setOrderPrice(155000);
        sampleOrder.setNeedExpert(creatAndValidationDate.insertDate("2024-11-13"));
        orderService.saveOrder(sampleOrder);
        Assertions.assertEquals(orderService.findById(1).getCustomer().getId(), customer.getId());
        Assertions.assertEquals(orderService.findById(1).getSubDuty().getId(), subDuty.getId());
    }

    @DisplayName("test for do not save an order with a price less than default price")
    @org.junit.jupiter.api.Order(2)
    @Test
    public void dontSaveWrongPriceOrder() {
        Customer customer = customerService.findById(1);
        SubDuty subDuty = subDutyService.findById(1);
        sampleOrder.setCustomer(customer);
        sampleOrder.setSubDuty(subDuty);
        sampleOrder.setDateCreatOrder(creatAndValidationDate.currentTime());
        sampleOrder.setOrderPrice(90000);
        sampleOrder.setNeedExpert(creatAndValidationDate.insertDate("2024-10-13"));
        Throwable exception = Assertions.assertThrows(WrongInputPriceException.class,
                () -> orderService.saveOrder(sampleOrder));
        Assertions.assertEquals("order price can not be less than default price", exception.getMessage());
    }

    @DisplayName("test for do not save an order with a past date for need an expert")
    @org.junit.jupiter.api.Order(3)
    @Test
    public void dontSavePastDateForNeedExpertOrder() {
        Customer customer = customerService.findById(1);
        SubDuty subDuty = subDutyService.findById(1);
        sampleOrder.setCustomer(customer);
        sampleOrder.setSubDuty(subDuty);
        sampleOrder.setDateCreatOrder(creatAndValidationDate.currentTime());
        sampleOrder.setOrderPrice(155000);
        sampleOrder.setNeedExpert(creatAndValidationDate.insertDate("2024-05-13"));
        Throwable exception = Assertions.assertThrows(WrongDateInsertException.class,
                () -> orderService.saveOrder(sampleOrder));
        Assertions.assertEquals("date can not be before today", exception.getMessage());
    }

    @DisplayName("test for find all orders of one customer")
    @org.junit.jupiter.api.Order(4)
    @Test
    @Transactional
    public void takeAllOrdersOfOneCustomer() {
        int expect = customerService.findById(1).getOrders().size();
        List<com.example.finalprojectsecondphase.entity.Order> orders =
                orderService.findCustomerOrders(customerService.findById(1));
        Assertions.assertEquals(expect, orders.size());
    }

    @DisplayName("test for a customer that has no order")
    @org.junit.jupiter.api.Order(5)
    @Test
    public void customerWithNoOrder() {
        Customer customer = customerService.findById(2);
        Throwable exception = Assertions.assertThrows(NullPointerException.class,
                () -> orderService.findCustomerOrders(customer));
        Assertions.assertEquals("no order for this customer", exception.getMessage());
    }

    @DisplayName("test for can not find by order id")
    @org.junit.jupiter.api.Order(6)
    @Test()
    public void canNotFindByOrderId() {
        int id = 3;
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> orderService.findById(id));
        Assertions.assertEquals("order with id " + id + " not founded", exception.getMessage());
    }

    @DisplayName("test for find all orders by order condition")
    @org.junit.jupiter.api.Order(7)
    @Test
    public void findAllOrdersByOrderCondition() {
        List<com.example.finalprojectsecondphase.entity.Order> orders =
                orderService.findByOrderCondition(OrderCondition.RECEIVING_OFFERS);
        Assertions.assertEquals(1, orders.size());
    }

    @DisplayName("test for a no order by this order condition")
    @org.junit.jupiter.api.Order(8)
    @Test
    public void findNoOrdersByOrderCondition() {
        Throwable exception = Assertions.assertThrows(NullPointerException.class,
                () -> orderService.findByOrderCondition(OrderCondition.WAIT_FOR_ACCEPT));
        Assertions.assertEquals("there is no order with " + "WAIT_FOR_ACCEPT" + " now", exception.getMessage());
    }

    @DisplayName("test for find all orders of one subDuty")
    @org.junit.jupiter.api.Order(9)
    @Test
    @Transactional
    public void findAllOrdersOfOneSubDuty() {
        SubDuty subDuty = subDutyService.findById(1);
        int expect = subDuty.getOrders().size();
        List<com.example.finalprojectsecondphase.entity.Order> orders =
                orderService.findSubDutyOrders(subDuty);
        Assertions.assertEquals(expect, orders.size());
    }

    @DisplayName("test for a subDuty that has no order")
    @org.junit.jupiter.api.Order(10)
    @Test
    public void subDutyThatHasNoOrder() {
        SubDuty subDuty = subDutyService.findById(2);
        Throwable exception = Assertions.assertThrows(NullPointerException.class,
                () -> orderService.findSubDutyOrders(subDuty));
        Assertions.assertEquals("this subDuty do not have any order yet", exception.getMessage());
    }


    // TODO: 6/17/2024 RUN AT SECOND PART ********


    @DisplayName("test for make an order Wait For Accept")
    @Test
    public void makeAnOrderWaitForAccept() {
        com.example.finalprojectsecondphase.entity.Order order = orderService.findById(1);
        orderService.makeOrderConditionWaitForAccept(order);
        Assertions.assertEquals(order.getOrderCondition(), OrderCondition.WAIT_FOR_ACCEPT);
    }


    // TODO: 6/17/2024 CHECK DB FIRST **************


    @DisplayName("test for make an order ongoing")
    @org.junit.jupiter.api.Order(1)
    @Test
    public void makeOrderOngoing() {
        Customer customer = customerService.findById(1);
        SubDuty subDuty = subDutyService.findById(1);
        forceOrder.setCustomer(customer);
        forceOrder.setSubDuty(subDuty);
        forceOrder.setDateCreatOrder(creatAndValidationDate.insertDate("2024-06-15"));
        forceOrder.setOrderPrice(155000);
        forceOrder.setNeedExpert(creatAndValidationDate.insertDate("2024-06-15"));
        orderService.forcedSave(forceOrder);
        int orderId = forceOrder.getId();

        Expert expert = expertService.findById(1);
        com.example.finalprojectsecondphase.entity.Order order = orderService.findById(orderId);
        firstOffer.setExpert(expert);
        firstOffer.setOrder(order);
        offerService.forcedSave(firstOffer);
        int offerId = firstOffer.getId();

        Offer offer = offerService.findById(offerId);
        offerService.updateOfferCondition(OfferCondition.ACCEPTED, offer);
        orderService.updateOrderCondition(OrderCondition.ACCEPTED, order);

        orderService.makeOrderOngoing(order);
        Assertions.assertEquals(order.getOrderCondition(), OrderCondition.ONGOING);
    }


    // TODO: 6/17/2024 RUN AFTER MAKE ORDER ACCEPTED *******


    @DisplayName("test for can not make order ongoing")
    @Test
    public void canNotMakeOrderOngoing() {
        com.example.finalprojectsecondphase.entity.Order order = orderService.findById(1);
        Throwable exception = Assertions.assertThrows(WrongDateInsertException.class,
                () -> orderService.makeOrderOngoing(order));
        Assertions.assertEquals("order can not be ongoing before need expert time",
                exception.getMessage());
    }


    // TODO: 6/17/2024 CHECK DB FIRST **************


    @DisplayName("test for make order done")
    @Test
    public void makeOrderDone() {
        List<com.example.finalprojectsecondphase.entity.Order> orders = orderService.findByOrderCondition(OrderCondition.ONGOING);
        com.example.finalprojectsecondphase.entity.Order order = orders.get(0);
        orderService.makeOrderDone(order);
        Assertions.assertEquals(order.getOrderCondition(), OrderCondition.DONE);
    }

    @DisplayName("test for delete an order")
    @Test
    public void removeOrder() {
        List<com.example.finalprojectsecondphase.entity.Order> orders = orderService.findByOrderCondition(OrderCondition.DONE);
        Order order = orders.get(0);
        int id = order.getId();
        orderService.removeOrder(id);
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> orderService.findById(id));
        Assertions.assertEquals("order with id " + id + " not founded", exception.getMessage());
    }
}
