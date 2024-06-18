package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Duty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DutyRepository extends JpaRepository<Duty,Integer> {
    Optional<Duty> findByDutyName(String name);
}
