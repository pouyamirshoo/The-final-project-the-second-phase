package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
}
