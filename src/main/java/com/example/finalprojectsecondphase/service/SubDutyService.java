package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.repository.SubDutyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubDutyService {
    private final SubDutyRepository subDutyRepository;
}
