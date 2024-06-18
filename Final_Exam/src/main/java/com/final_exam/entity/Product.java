package com.final_exam.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productCode;
	@NotBlank(message = "상품이름이 비었습니다.")
	// @NotBlank: 공백이 아닌 값을 입력해야 함을 검사
	private String productName;

	@NotNull(message = "상품가격이 비었습니다.")
	@Min(value = 0, message = "상품가격은 0보다 커야한다.")
	// @NotNull: null 값이 아닌 값을 입력해야 함을 검사
	// @Min: 입력 값이 최소 0 이상이어야 함을 검사
	private BigDecimal productCost;

	@NotBlank(message = "상품카테고리가 비었습니다.")
	// @NotBlank: 공백이 아닌 값을 입력해야 함을 검사
	private String productCategory;

	@NotBlank(message = "상품색상이 없습니다.")
	private String productColor;

	@Min(value = 0, message = "판매량은 당연히 0개부터시작")
	private int SalesRate;

	// Getters and Setters 이유는 모르겠으나 @Data가 정상적인 상태가 아니라서 여기에 없으면 작동을 안함
	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductCost() {
		return productCost;
	}

	public String getProductColor() {
		return productColor;
	}

	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	public int getSalesRate() {
		return SalesRate;
	}

	public void setSalesRate(int salesRate) {
		SalesRate = salesRate;
	}

	public void setProductCost(BigDecimal productCost) {
		this.productCost = productCost;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
}
