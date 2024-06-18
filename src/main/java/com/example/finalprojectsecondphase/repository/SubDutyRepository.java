package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Duty;
import com.example.finalprojectsecondphase.entity.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubDutyRepository extends JpaRepository<SubDuty,Integer> {
    Optional<SubDuty> findBySubDutyName(String name);
}
