package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Optional<Admin> findByUsernameAndPassword(String username, String password);
}
