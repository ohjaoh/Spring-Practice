package com.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "custom_orders")
@Data
public class CustomOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int serial_no;
	
	// 외래 키로 상품 코드를 가져옴
	@ManyToOne
	@JoinColumn(name = "product_code", referencedColumnName = "productCode")
	private Product product_code;
	// @JoinColumn: 외래 키 설정
    // name: 이 엔티티의 외래 키 컬럼명을 지정 (여기서는 "product_code")
    // referencedColumnName: 참조할 대상 엔티티의 기본 키 컬럼명을 지정 (여기서는 Product 엔티티의 "productCode")

	//나중에 회원정보도 가져올것 + 자체적인 필드가 필요할듯?

}
