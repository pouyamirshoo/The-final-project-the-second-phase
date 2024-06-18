package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.enums.ExpertCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpertRepository extends JpaRepository<Expert,Integer> {
    Optional<Expert> findByUsername(String username);
    Optional<Expert> findByEmail(String email);
    Optional<Expert> findByPhoneNumber(String phoneNumber);
    Optional<Expert> findByPostalCode(String postalCode);
    Optional<Expert> findByNationalCode(String nationalCode);

}
