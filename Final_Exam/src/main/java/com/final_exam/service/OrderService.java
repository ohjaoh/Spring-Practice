package com.final_exam.service;

import com.final_exam.entity.Order;
import com.final_exam.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void saveOrder(Order Order) {
    	orderRepository.save(Order);
    }

    public void updateOrder(int id, Order Order) {
    	Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            existingOrder.setProduct_code(Order.getProduct_code());
            existingOrder.setDeliveryDate(Order.getDeliveryDate());
            orderRepository.save(existingOrder);
        }
    }

    public void deleteOrder(int id) {
    	orderRepository.deleteById(id);
    }
}
