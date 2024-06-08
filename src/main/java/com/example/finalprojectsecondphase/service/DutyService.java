package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.repository.DutyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DutyService {
    private final DutyRepository dutyRepository;
}
