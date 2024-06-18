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
}
