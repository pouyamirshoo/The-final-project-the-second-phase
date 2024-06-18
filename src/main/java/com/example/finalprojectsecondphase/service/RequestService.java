package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.Request;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.repository.RequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {

    private final RequestRepository requestRepository;

    @Transactional
    public void saveRequests(Request request) {
        requestRepository.save(request);
    }

    @Transactional
    public Request findByExpert(Expert expert) {
        return requestRepository.findByExpert(expert).orElseThrow(() ->
                new NotFoundException("request for expert with id " + expert.getId() + " not founded"));
    }

    public Request findById(int id) {
        return requestRepository.findById(id).orElseThrow(() ->
                new NotFoundException("request with id " + id + " not founded"));
    }
}
