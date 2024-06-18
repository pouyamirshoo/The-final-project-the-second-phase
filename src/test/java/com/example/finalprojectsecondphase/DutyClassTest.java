package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.Duty;
import com.example.finalprojectsecondphase.exception.DuplicateInformationException;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.service.DutyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class DutyClassTest {

    @Autowired
    DutyService dutyService;

    private static Duty correctDuty;
    private static Duty correctSecondDuty;
    private static Duty invalidInfoTypeDuty;

    @BeforeAll
    public static void makeDuty(){

        correctDuty = Duty.builder()
                .dutyName("NEZAFAT")
                .build();

        correctSecondDuty = Duty.builder()
                .dutyName("TASISAT")
                .build();

        invalidInfoTypeDuty = Duty.builder()
                .dutyName("@#$hg")
                .build();
    }

    @DisplayName("test for save a duty")
    @Order(1)
    @Test
    public void saveDuty() {
        dutyService.saveDuty(correctDuty);
        Assertions.assertEquals(dutyService.findById(1).getDutyName(), correctDuty.getDutyName());
    }

    @DisplayName("test for not save a duplicate name duty")
    @Order(2)
    @Test
    public void dontSaveDuplicateNameDuty() {
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> dutyService.saveDuty(correctDuty));
        Assertions.assertEquals("duplicate duty name can not insert", exception.getMessage());
    }
}
