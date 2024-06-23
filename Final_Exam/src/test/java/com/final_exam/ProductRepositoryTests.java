package com.final_exam;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.final_exam.entity.Product;
import com.final_exam.repository.ProductRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void testCreateProduct() {
		Product product = new Product();
		product.setProductName("Product Name");
		product.setProductCost(new BigDecimal("100.00"));
		product.setProductCategory("Category");
		product.setProductColor("Red");
		product.setSalesRate(10);

		Product savedProduct = productRepository.save(product);

		// 디버그 출력
		System.out.println("상품이 저장되었습니다. 저장된 테스트용 상품: " + savedProduct);
	}
}
