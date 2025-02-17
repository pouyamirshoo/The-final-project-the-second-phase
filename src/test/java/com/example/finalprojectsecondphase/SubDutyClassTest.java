package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.Duty;
import com.example.finalprojectsecondphase.entity.SubDuty;
import com.example.finalprojectsecondphase.exception.DuplicateInformationException;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.service.DutyService;
import com.example.finalprojectsecondphase.service.SubDutyService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class SubDutyClassTest {

    @Autowired
    SubDutyService subDutyService;
    @Autowired
    DutyService dutyService;

    private static SubDuty correctSubDuty;
    private static SubDuty secondCorrectSubDuty;
    private static SubDuty deleteSubDuty;
    private static SubDuty invalidInfoTypeSubDuty;

    @BeforeAll
    public static void makeSubDuty(){

        correctSubDuty = SubDuty.builder()
                .subDutyName("manzel")
                .price(100000)
                .description("this price is for an hour")
                .build();

        secondCorrectSubDuty = SubDuty.builder()
                .subDutyName("Mashin")
                .price(70000)
                .description("this price is for an hour")
                .build();

        deleteSubDuty = SubDuty.builder()
                .subDutyName("sakhteman")
                .price(200000)
                .description("this price is for an hour")
                .build();

        invalidInfoTypeSubDuty = SubDuty.builder()
                .subDutyName("!#$jgd")
                .price(70000)
                .build();
    }

    @DisplayName("test for save a subDuty")
    @Order(1)
    @Test
    public void saveSubDuty() {
        Duty duty = dutyService.findById(1);
        correctSubDuty.setDuty(duty);
        subDutyService.saveSubDuty(correctSubDuty);
        Assertions.assertEquals(subDutyService.findById(1).getSubDutyName(), correctSubDuty.getSubDutyName());
    }

    @DisplayName("test for save second subDuty")
    @Order(2)
    @Test
    public void saveSecondSubDuty() {
        Duty duty = dutyService.findById(1);
        secondCorrectSubDuty.setDuty(duty);
        subDutyService.saveSubDuty(secondCorrectSubDuty);
        Assertions.assertEquals(subDutyService.findById(2).getSubDutyName(), secondCorrectSubDuty.getSubDutyName());
    }

    @DisplayName("test for not save a duplicate name subDuty")
    @Order(3)
    @Test
    public void dontSaveDuplicateNameSubDuty() {
        Duty duty = dutyService.findById(1);
        correctSubDuty.setDuty(duty);
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> subDutyService.saveSubDuty(correctSubDuty));
        Assertions.assertEquals("duplicate subDuty name can not insert", exception.getMessage());
    }

    @DisplayName("test for can not find by subDuty id")
    @Order(4)
    @Test()
    public void canNotFindBySubDutyId() {
        int id = 3;
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> subDutyService.findById(id));
        Assertions.assertEquals("subDuty with id " + id + " not founded", exception.getMessage());
    }

    @DisplayName("test for invalid input subDuty")
    @Order(5)
    @Test()
    public void doNotSaveInvalidSubDutyInputInformation() {
        Duty duty = dutyService.findById(1);
        invalidInfoTypeSubDuty.setDuty(duty);
        Throwable exception = Assertions.assertThrows(InvalidInputInformationException.class,
                () -> subDutyService.validate(invalidInfoTypeSubDuty));
        Assertions.assertEquals("some of inputs are not valid", exception.getMessage());
    }

    @DisplayName("test for update a subDuty's price")
    @Order(6)
    @Test()
    public void updateSubDutyPrice() {
        int newPrice = 150000;
        int id = 1;
        subDutyService.updateSubDutyPrice(newPrice, id);
        Assertions.assertEquals(subDutyService.findById(id).getPrice(), newPrice);
    }

    @DisplayName("test for update a subDuty's description")
    @Order(7)
    @Test()
    public void updateSubDutyDescription() {
        String newDescription = "this price is for two hours";
        int id = 1;
        subDutyService.updateSubDutyDescription(newDescription, id);
        Assertions.assertEquals(subDutyService.findById(id).getDescription(), newDescription);
    }

    @DisplayName("test for find all subDuties of one duty")
    @Order(8)
    @Transactional
    @Test()
    public void takeSubDutiesOfOneDuty() {
        int expect = dutyService.findById(1).getSubDuties().size();
        List<SubDuty> subDutiesFounded = subDutyService.findByDuty(dutyService.findById(1));
        Assertions.assertEquals(expect,subDutiesFounded.size());
    }

    @DisplayName("test for remove a subDuty")
    @Order(9)
    @Test
    public void removeOneSubDuty() {
        Duty duty = dutyService.findById(1);
        deleteSubDuty.setDuty(duty);
        subDutyService.saveSubDuty(deleteSubDuty);
        int id = deleteSubDuty.getId();
        subDutyService.removeSubDuty(deleteSubDuty);
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> subDutyService.findById(id));
        Assertions.assertEquals("subDuty with id " + id + " not founded", exception.getMessage());
    }
}
