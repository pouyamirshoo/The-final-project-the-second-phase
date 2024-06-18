package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.Offer;
import com.example.finalprojectsecondphase.entity.Order;
import com.example.finalprojectsecondphase.entity.enums.OfferCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer,Integer> {
    List<Offer> findByExpert(Expert expert);
}
