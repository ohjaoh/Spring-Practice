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

	// 모든 CustomOrder를 조회하는 메서드
	public List<CustomOrder> getAllCustomOrders() {
		return customOrderRepository.findAll();
	}

	// ID로 특정 CustomOrder를 조회하는 메서드
	public CustomOrder getCustomOrderById(int id) {
		return customOrderRepository.findById(id).orElse(null);
	}

	// 새로운 CustomOrder를 저장하는 메서드
	public void saveCustomOrder(CustomOrder customOrder) {
		customOrderRepository.save(customOrder);
	}

	// 기존 CustomOrder를 업데이트하는 메서드
	public void updateCustomOrder(int id, CustomOrder customOrder) {
		CustomOrder existingOrder = customOrderRepository.findById(id).orElse(null);
		if (existingOrder != null) {
			// 필요한 필드를 업데이트
			existingOrder.setOrderProductName(customOrder.getOrderProductName());
			existingOrder.setCustomizationDetails(customOrder.getCustomizationDetails());
			existingOrder.setDeliveryDate(customOrder.getDeliveryDate());
			existingOrder.setSpecialInstructions(customOrder.getSpecialInstructions());
			customOrderRepository.save(existingOrder); // 업데이트된 엔티티 저장
		}
	}

	// ID로 특정 CustomOrder를 삭제하는 메서드
	public void deleteCustomOrder(int id) {
		customOrderRepository.deleteById(id);
	}

	// 키워드를 이용해 CustomOrder를 검색하는 메서드
	public List<CustomOrder> searchMembers(String keyword) {
		return customOrderRepository.searchByKeyword(keyword);
	}
}
