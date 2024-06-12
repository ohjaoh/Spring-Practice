package com.final_exam.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//회원에서 주소, 전화번호, 주문자 아이디 받아오고 
//주문페이지에서 결제금액, 결제방법, 결제 지불 방법 생성
// 배송요구사항 추가
@Entity
@Table(name = "orders")
@Data
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int serial_no;

	@ManyToOne
	@JoinColumn(name = "product_code", referencedColumnName = "productCode")
	@NotNull(message = "Product code is required")
	private Product product_code;

	@Column(name = "delivery_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Delivery date is required")
    @FutureOrPresent(message = "Delivery date must be today or in the future")
    private Date deliveryDate;

	// 수동으로 추가한 getter 및 setter
	public int getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(int serial_no) {
		this.serial_no = serial_no;
	}

	public Product getProduct_code() {
		return product_code;
	}

	public void setProduct_code(Product product_code) {
		this.product_code = product_code;
	}


	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

}
