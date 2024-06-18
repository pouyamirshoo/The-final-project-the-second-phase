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



    public boolean checkOrderPrice(int price, SubDuty subDuty) {
        int subDutyPrice = subDuty.getPrice();
        if (price >= subDutyPrice)
            return true;
        else throw new WrongInputPriceException("order price can not be less than default price");
    }
}
