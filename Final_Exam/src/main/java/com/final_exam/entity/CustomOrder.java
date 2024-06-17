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

@Entity
@Table(name = "custom_orders")
@Data
public class CustomOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int serial_no;

	@ManyToOne
	@JoinColumn(name = "productCode", referencedColumnName = "productCode")
	private Product product;

	@Column(name = "order_product_code")
	private String orderProductCode;

	@Column(name = "order_product_name")
	private String orderProductName;

	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "user_no")
	private Member member;

	@Column(name = "customization_details")
	private String customizationDetails;

	@Column(name = "delivery_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Delivery date is required")
	@FutureOrPresent(message = "Delivery date must be today or in the future")
	private Date deliveryDate;

	@Column(name = "special_instructions")
	private String specialInstructions;

	@Column(name = "order_name")
	private String orderName;

	@Column(name = "order_phone_number")
	private String orderPhoneNumber;

	@Column(name = "order_address")
	private String orderAddress;

	// 수동으로 추가한 getter 및 setter
	public int getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(int serial_no) {
		this.serial_no = serial_no;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		if (product != null) {
			this.orderProductName = product.getProductName(); // 상품명을 받아온다

		} else {
			this.orderProductName = null;

		}
	}

	public String getOrderProductCode() {
		return orderProductCode;
	}

	public void setOrderProductCode(String orderProductCode) {
		this.orderProductCode = orderProductCode;
	}

	public String getOrderProductName() {
		return orderProductName;
	}

	public void setOrderProductName(String orderProductName) {
		this.orderProductName = orderProductName;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
		if (member != null) {
			this.orderName = member.getRealName(); // 회원의 이름을 주문자 이름으로 설정
			this.orderPhoneNumber = member.getPhoneNumber(); // 회원의 전화번호를 주문자 전화번호로 설정
			this.orderAddress = member.getAddress(); // 회원의 주소를 주문자 주소로 설정
		} else {
			this.orderName = null;
			this.orderPhoneNumber = null;
			this.orderAddress = null;
		}
	}

	public String getCustomizationDetails() {
		return customizationDetails;
	}

	public void setCustomizationDetails(String customizationDetails) {
		this.customizationDetails = customizationDetails;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderPhoneNumber() {
		return orderPhoneNumber;
	}

	public void setOrderPhoneNumber(String orderPhoneNumber) {
		this.orderPhoneNumber = orderPhoneNumber;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}
}
