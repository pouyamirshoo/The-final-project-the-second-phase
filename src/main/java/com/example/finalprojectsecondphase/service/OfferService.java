package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.Offer;
import com.example.finalprojectsecondphase.entity.Order;
import com.example.finalprojectsecondphase.entity.SubDuty;
import com.example.finalprojectsecondphase.entity.enums.OfferCondition;
import com.example.finalprojectsecondphase.entity.enums.OrderCondition;
import com.example.finalprojectsecondphase.exception.DuplicateInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.exception.WrongConditionException;
import com.example.finalprojectsecondphase.exception.WrongInputPriceException;
import com.example.finalprojectsecondphase.repository.OfferRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfferService {

    private final OfferRepository offerRepository;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public boolean checkOfferPrice(int price, SubDuty subDuty) {
        int subDutyPrice = subDuty.getPrice();
        if (price >= subDutyPrice)
            return true;
        else throw new WrongInputPriceException("offer price can not be less than default price");
    }

    @Transactional
    public void checkDuplicateOffer(Offer offer) {
        if (offerRepository.findByOrderAndExpert(offer.getOrder(), offer.getExpert()).isPresent()) {
            throw new DuplicateInformationException("an order is exist by this expert for this order");
        } else
            validate(offer);
    }
}
