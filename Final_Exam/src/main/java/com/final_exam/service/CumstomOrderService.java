package com.final_exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.final_exam.repository.CustomOrderRepository;

@Service
public class CumstomOrderService {

    @Autowired
    private CustomOrderRepository customOrderRepository;

    
}
