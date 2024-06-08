package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Requested;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Requested,Integer> {
}
