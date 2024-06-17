package com.final_exam.service;

import com.final_exam.entity.CustomOrder;
import com.final_exam.repository.CustomOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomOrderService {

    @Autowired
    private CustomOrderRepository customOrderRepository;

    public List<CustomOrder> getAllCustomOrders() {
        return customOrderRepository.findAll();
    }

    public CustomOrder getCustomOrderById(int id) {
        return customOrderRepository.findById(id).orElse(null);
    }

    public void saveCustomOrder(CustomOrder customOrder) {
        customOrderRepository.save(customOrder);
    }

    public void updateCustomOrder(int id, CustomOrder customOrder) {
        CustomOrder existingOrder = customOrderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
        	existingOrder.setOrderProductCode(customOrder.getOrderProductCode());
        	existingOrder.setOrderProductName(customOrder.getOrderProductName());;
            existingOrder.setCustomizationDetails(customOrder.getCustomizationDetails());
            existingOrder.setDeliveryDate(customOrder.getDeliveryDate());
            existingOrder.setSpecialInstructions(customOrder.getSpecialInstructions());
            customOrderRepository.save(existingOrder);
        }
    }

    public void deleteCustomOrder(int id) {
        customOrderRepository.deleteById(id);
    }
}
