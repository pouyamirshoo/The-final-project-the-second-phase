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

    public void validate(Offer offer) {
        Set<ConstraintViolation<Offer>> violations = validator.validate(offer);
        if (violations.isEmpty()) {
            offerRepository.save(offer);
            log.info("subDuty saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Offer> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
    }

    @Transactional
    public void saveOffer(Offer offer) {
        if (checkOfferPrice(offer.getOfferPrice(), offer.getOrder().getSubDuty()) ||
                checkOrderCondition(offer.getOrder())) {
            checkDuplicateOffer(offer);
        }
    }

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

    public boolean checkOrderCondition(Order order) {
        if (order.getOrderCondition() == OrderCondition.RECEIVING_OFFERS ||
                order.getOrderCondition() == OrderCondition.WAIT_FOR_ACCEPT) {
            return true;
        } else throw new WrongConditionException("can not send offer for this order");
    }

    @Transactional
    public List<Offer> findExpertOffers(Expert expert) {
        List<Offer> offers = offerRepository.findByExpert(expert);
        if (offers.size() > 0)
            return offers;
        else
            throw new NullPointerException();
    }

    @Transactional
    public List<Offer> findOrderOffers(Order order) {
        List<Offer> offers = offerRepository.findByOrder(order);
        if (offers.size() > 0)
            return offers;
        else
            throw new NullPointerException();
    }

    @Transactional
    public List<Offer> setOffersByExpertRate(Order order) {
        List<Offer> offers = findOrderOffers(order);
        return offers.stream().sorted(Comparator.comparing(a -> a.getExpert().getRate())).collect(Collectors.toList());
    }

    @Transactional
    public List<Offer> setOffersByPrice(Order order) {
        List<Offer> offers = findOrderOffers(order);
        return offers.stream().sorted(Comparator.comparingInt(Offer::getOfferPrice)).collect(Collectors.toList());
    }

    @Transactional
    public List<Offer> findByOfferCondition(OfferCondition offerCondition) {
        List<Offer> offers = offerRepository.findByOfferCondition(offerCondition);
        if (offers.size() > 0)
            return offers;
        else
            throw new NullPointerException();
    }

    public void updateOfferCondition(OfferCondition offerCondition, Offer offer) {
        offer.setOfferCondition(offerCondition);
        validate(offer);
    }
}
