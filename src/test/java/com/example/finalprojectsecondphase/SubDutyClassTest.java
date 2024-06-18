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
}
