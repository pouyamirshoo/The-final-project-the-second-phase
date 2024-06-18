package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request,Integer> {
    Optional<Request> findByExpert(Expert expert);
}
