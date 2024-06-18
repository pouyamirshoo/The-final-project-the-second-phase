package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.enums.ExpertCondition;
import com.example.finalprojectsecondphase.exception.DuplicateInformationException;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.service.ExpertService;
import com.example.finalprojectsecondphase.service.SubDutyService;
import com.example.finalprojectsecondphase.util.validation.TakeAndCheckImage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ExpertClassTest {

    @Autowired
    ExpertService expertService;
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    TakeAndCheckImage takeAndCheckImage;

    String correctPath = "F:\\Maktab\\FinalProjectFirstPhase\\src\\main\\java\\images\\15639454.jpg";

    private static Expert correctExpert;
    private static Expert deleteExpert;
    private static Expert duplicateUsernameExpert;
    private static Expert duplicateEmailExpert;
    private static Expert duplicatePhoneNumberExpert;
    private static Expert duplicatePostalCodeExpert;
    private static Expert duplicateNationalCodeExpert;
    private static Expert invalidInfoTypeExpert;

    @BeforeAll
    public static void makeExpert() {

        correctExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya123")
                .password("aaAA12!@")
                .email("pouya@gmail.com")
                .phoneNumber("+989124184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1395613373")
                .nationalCode("0440186412")
                .build();

        deleteExpert = Expert.builder()
                .firstname("mohammd")
                .lastname("mir")
                .username("moh123")
                .password("aaAA12!@")
                .email("moh@gmail.com")
                .phoneNumber("+989128544434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1665743471")
                .nationalCode("5126973215")
                .build();

        duplicateUsernameExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya123")
                .password("aaAA12!@")
                .email("pouyaaa@gmail.com")
                .phoneNumber("+989125184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1417635471")
                .nationalCode("2509203669")
                .build();

        duplicateEmailExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya1234")
                .password("aaAA12!@")
                .email("pouya@gmail.com")
                .phoneNumber("+989127184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("4654190118")
                .nationalCode("7829168131")
                .build();

        duplicatePhoneNumberExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya1235")
                .password("aaAA12!@")
                .email("pouyaaa@gmail.com")
                .phoneNumber("+989124184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1665743471")
                .nationalCode("8583552363")
                .build();

        duplicatePostalCodeExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya1236")
                .password("aaAA12!@")
                .email("pouysaa@gmail.com")
                .phoneNumber("+989127184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1395613373")
                .nationalCode("1964576288")
                .build();

        duplicateNationalCodeExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya1239")
                .password("aaAA12!@")
                .email("pouyyyya@gmail.com")
                .phoneNumber("+989124294434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("3336131927")
                .nationalCode("0440186412")
                .build();

        invalidInfoTypeExpert = Expert.builder()
                .firstname("pouya@#")
                .lastname("mir")
                .username("pouya1236")
                .password("aaA1!@")
                .email("pouysaadfagmail.com")
                .phoneNumber("+989127184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("135613373")
                .nationalCode("15464818971")
                .build();
    }

    @DisplayName("test for save a correct expert")
    @Order(1)
    @Test()
    public void saveCorrectImage() {
        byte[] image = takeAndCheckImage.expertImage(correctPath);
        correctExpert.setExpertImage(image);
        expertService.saveExpert(correctExpert);
        Assertions.assertEquals(expertService.findById(1).getUsername(), correctExpert.getUsername());
    }

    @DisplayName("test for not save expert for duplicate username")
    @Order(2)
    @Test()
    public void doNotSaveDuplicateExpertUsername() {
        byte[] image = takeAndCheckImage.expertImage(correctPath);
        duplicateUsernameExpert.setExpertImage(image);
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> expertService.saveExpert(duplicateUsernameExpert));
        Assertions.assertEquals("duplicate username can not insert", exception.getMessage());
    }

    @DisplayName("test for not save expert for duplicate email")
    @Order(3)
    @Test()
    public void doNotSaveDuplicateExpertEmail() {
        byte[] image = takeAndCheckImage.expertImage(correctPath);
        duplicateEmailExpert.setExpertImage(image);
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> expertService.saveExpert(duplicateEmailExpert));
        Assertions.assertEquals("duplicate email can not insert", exception.getMessage());
    }

    @DisplayName("test for not save expert for duplicate phoneNumber")
    @Order(4)
    @Test()
    public void doNotSaveDuplicateExpertPhoneNumber() {
        byte[] image = takeAndCheckImage.expertImage(correctPath);
        duplicatePhoneNumberExpert.setExpertImage(image);
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> expertService.saveExpert(duplicatePhoneNumberExpert));
        Assertions.assertEquals("duplicate phoneNumber can not insert", exception.getMessage());
    }

    @DisplayName("test for not save expert for duplicate postalCode")
    @Order(5)
    @Test()
    public void doNotSaveDuplicateExpertPostalCode() {
        byte[] image = takeAndCheckImage.expertImage(correctPath);
        duplicatePostalCodeExpert.setExpertImage(image);
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> expertService.saveExpert(duplicatePostalCodeExpert));
        Assertions.assertEquals("duplicate postalCode can not insert", exception.getMessage());
    }

    @DisplayName("test for not save expert for duplicate nationalCode")
    @Order(6)
    @Test()
    public void doNotSaveDuplicateExpertNationalCode() {
        byte[] image = takeAndCheckImage.expertImage(correctPath);
        duplicateNationalCodeExpert.setExpertImage(image);
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> expertService.saveExpert(duplicateNationalCodeExpert));
        Assertions.assertEquals("duplicate nationalCode can not insert", exception.getMessage());
    }

    @DisplayName("test for invalid input image expert")
    @Order(7)
    @Test()
    public void doNotSaveInvalidExpertInputInformation() {
        byte[] image = takeAndCheckImage.expertImage("F:\\Maktab\\FinalProjectFirstPhase\\src\\main\\java\\images\\Sample-jpg-image-10mb.jpg");
        invalidInfoTypeExpert.setExpertImage(image);
        Throwable exception = Assertions.assertThrows(InvalidInputInformationException.class,
                () -> expertService.validate(invalidInfoTypeExpert));
        Assertions.assertEquals("some of inputs are not valid", exception.getMessage());
    }

    @DisplayName("test for signIn expert")
    @Order(8)
    @Test()
    public void signInExpert() {
        String username = correctExpert.getUsername();
        String password = correctExpert.getPassword();
        Expert foundedExpert = expertService.signInExpert(username, password);
        Assertions.assertEquals(foundedExpert.getUsername(), correctExpert.getUsername());
    }

    @DisplayName("test for can not find by expert id")
    @Order(9)
    @Test()
    public void canNotFindByExpertId() {
        int id = 3;
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> expertService.findById(id));
        Assertions.assertEquals("expert with id " + id + " not founded", exception.getMessage());
    }

    @DisplayName("test for wrong signIn expert username")
    @Order(10)
    @Test()
    public void wrongSignInExpertUsername() {
        String username = "123456";
        String password = correctExpert.getPassword();
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> expertService.signInExpert(username, password));
        Assertions.assertEquals("wrong username or password", exception.getMessage());
    }

    @DisplayName("test for wrong signIn expert password")
    @Order(11)
    @Test()
    public void wrongSignInExpertPassword() {
        String username = correctExpert.getUsername();
        String password = "456987";
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> expertService.signInExpert(username, password));
        Assertions.assertEquals("wrong username or password", exception.getMessage());
    }

    @DisplayName("test for change an expert password")
    @Order(12)
    @Test
    public void changeExpertPassword() {
        int id = 1;
        String newPassword = "bbBB12!@";
        expertService.UpdatePassword(newPassword, id);
        Assertions.assertEquals(expertService.findById(id).getPassword(), newPassword);
    }
}
