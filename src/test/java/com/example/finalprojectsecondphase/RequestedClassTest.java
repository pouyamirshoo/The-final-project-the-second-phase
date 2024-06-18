package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.Request;
import com.example.finalprojectsecondphase.entity.SubDuty;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.service.ExpertService;
import com.example.finalprojectsecondphase.service.RequestService;
import com.example.finalprojectsecondphase.service.SubDutyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class RequestedClassTest {

    @Autowired
    RequestService requestService;
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    ExpertService expertService;

    private static Request correctRequest;

    @BeforeAll
    public static void makeRequest() {
        correctRequest = Request.builder()
                .build();
    }

    @DisplayName("test for save a request")
    @Order(1)
    @Test()
    public void saveOneRequest() {
        List<SubDuty> requestSubDuties = new ArrayList<>();
        SubDuty firstSubDuty = subDutyService.findById(1);
        SubDuty secondSubDuty = subDutyService.findById(2);
        requestSubDuties.add(firstSubDuty);
        requestSubDuties.add(secondSubDuty);
        Expert expert = expertService.findById(1);
        correctRequest.setExpert(expert);
        correctRequest.setSubDuties(requestSubDuties);
        requestService.saveRequests(correctRequest);
        int id = correctRequest.getId();
        Assertions.assertEquals(requestService.findById(id).getExpert().getUsername(), expert.getUsername());
    }
}
