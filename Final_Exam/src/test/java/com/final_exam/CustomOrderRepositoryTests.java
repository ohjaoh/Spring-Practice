package com.final_exam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.final_exam.entity.CustomOrder;
import com.final_exam.repository.CustomOrderRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class CustomOrderRepositoryTests {

	@Autowired
	private CustomOrderRepository customOrderRepository;

	@Test
	public void testCreateCustomOrder() {
		CustomOrder order = new CustomOrder(); // 배송일 유효성때문에 현재 날짜에 5일 추가

		LocalDate localDate = LocalDate.now().plusDays(5);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		order.setDeliveryDate(date);
		order.setOrderName("John Doe");
		order.setOrderPhoneNumber("123456789");
		order.setOrderAddress("123 Main St");
		order.setCustomizationDetails("Extra cheese");
		order.setSpecialInstructions("Leave at the door");

		CustomOrder savedOrder = customOrderRepository.save(order);

		// 디버그 출력
		System.out.println("주문이 저장되었습니다. 저장된 테스트용 주문: " + savedOrder);

	}
}
