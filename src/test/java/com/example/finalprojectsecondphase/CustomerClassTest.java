package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.*;
import com.example.finalprojectsecondphase.exception.*;
import com.example.finalprojectsecondphase.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class CustomerClassTest {

    @Autowired
    CustomerService customerService;

    private static Customer correctCustomer;
    private static Customer correctSecondCustomer;
    private static Customer duplicateUsernameCustomer;
    private static Customer duplicteEmailCustomer;
    private static Customer duplicatePhoneNumberCustomer;
    private static Customer duplicatePostalCodeCustomer;
    private static Customer invalidInfoTypeCustomer;

    @BeforeAll
    public static void makeCustomers() {
        correctCustomer = Customer.builder()
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

        correctSecondCustomer = Customer.builder()
                .firstname("ali")
                .lastname("mir")
                .username("ali123")
                .password("aaAA12!@")
                .email("ali@gmail.com")
                .phoneNumber("+989125694434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1393835763")
                .build();

        duplicateUsernameCustomer = Customer.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya123")
                .password("aaAA12!@")
                .email("pouyaaa@gmail.com")
                .phoneNumber("+989125184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1417635471")
                .build();

        duplicteEmailCustomer = Customer.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya1234")
                .password("aaAA12!@")
                .email("pouya@gmail.com")
                .phoneNumber("+989127184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("4654190118")
                .build();

        duplicatePhoneNumberCustomer = Customer.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya1235")
                .password("aaAA12!@")
                .email("pouyaaa@gmail.com")
                .phoneNumber("+989124184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1665743471")
                .build();

        duplicatePostalCodeCustomer = Customer.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya1236")
                .password("aaAA12!@")
                .email("pouysaa@gmail.com")
                .phoneNumber("+989127184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1395613373")
                .build();

        invalidInfoTypeCustomer = Customer.builder()
                .firstname("pouya@#")
                .lastname("mir")
                .username("pouya1236")
                .password("aaA1!@")
                .email("pouysaadfagmail.com")
                .phoneNumber("+989127184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("135613373")
                .build();
    }

    @DisplayName("test for customer save method")
    @Order(1)
    @Test
    public void saveCostumer() {
        customerService.saveCustomer(correctCustomer);
        customerService.saveCustomer(correctSecondCustomer);
        Assertions.assertEquals(correctCustomer.getUsername(), customerService.findById(1).getUsername());
    }

    @DisplayName("test for not save customer for duplicate username")
    @Order(2)
    @Test()
    public void doNotSaveDuplicateCustomerUsername() {
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> customerService.saveCustomer(duplicateUsernameCustomer));
        Assertions.assertEquals("duplicate username can not insert", exception.getMessage());
    }

    @DisplayName("test for not save customer for duplicate email")
    @Order(3)
    @Test()
    public void doNotSaveDuplicateCustomerEmail() {
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> customerService.saveCustomer(duplicteEmailCustomer));
        Assertions.assertEquals("duplicate email can not insert", exception.getMessage());
    }

    @DisplayName("test for not save customer for duplicate phoneNumber")
    @Order(4)
    @Test()
    public void doNotSaveDuplicateCustomerPhoneNumber() {
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> customerService.saveCustomer(duplicatePhoneNumberCustomer));
        Assertions.assertEquals("duplicate phoneNumber can not insert", exception.getMessage());
    }

    @DisplayName("test for not save customer for duplicate postalCode")
    @Order(5)
    @Test()
    public void doNotSaveDuplicateCustomerPostalCode() {
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> customerService.saveCustomer(duplicatePostalCodeCustomer));
        Assertions.assertEquals("duplicate postalCode can not insert", exception.getMessage());
    }

    @DisplayName("test for signIn customer")
    @Order(6)
    @Test()
    public void signInCustomer() {
        String username = correctCustomer.getUsername();
        String password = correctCustomer.getPassword();
        Customer foundedCustomer = customerService.singInCustomer(username, password);
        Assertions.assertEquals(foundedCustomer.getUsername(), correctCustomer.getUsername());
    }
}
