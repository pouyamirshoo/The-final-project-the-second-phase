package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.*;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class AdminClassTest {

    @Autowired
    AdminService adminService;
    @Autowired
    ExpertService expertService;
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    DutyService dutyService;
    @Autowired
    RequestService requestService;

    private static Admin admin;

    private static SubDuty thirdCorrectSubDuty;

    @BeforeAll
    public static void makeAdmin() {
        admin = Admin.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya123")
                .password("aaAA12!@")
                .email("pouya@gmail.com")
                .phoneNumber("+989124184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1395613373")
                .build();

        thirdCorrectSubDuty = SubDuty.builder()
                .subDutyName("daftar")
                .price(110000)
                .description("this price is for an hour")
                .build();
    }

    @DisplayName("test for Save And SignIn Admin")
    @Order(1)
    @Test()
    public void voidSaveAndSignInAdmin() {
        adminService.saveAdmin(admin);
        adminService.adminSignIn(admin.getUsername(), admin.getPassword());
        Assertions.assertEquals(1, admin.getId());
    }
}
