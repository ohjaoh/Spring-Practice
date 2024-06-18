package com.final_exam.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "members")
@Data
public class Member {
	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public BigDecimal getTotalPaymentAmount() {
		return totalPaymentAmount;
	}

	public void setTotalPaymentAmount(BigDecimal totalPaymentAmount) {
		this.totalPaymentAmount = totalPaymentAmount;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_no; // 회원 아이디 (기본 키)

	@NotBlank(message = "아이디가 비었습니다.")
	private String id; // 로그인 아이디

	@NotBlank(message = "실명이 비었습니다.")
	@Size(min = 2, message = "실명은 2자 이상이어야 합니다.")
	private String realName; // 실명 필드 추가

	@NotBlank(message = "Password is mandatory")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d`~!@#$%^&*()-_=+]{6,}$", message = "비밀번호는 6자리 이상이어야 하며, 숫자와 영문자를 포함해야 합니다.\n 특수문자는 키보드의 `부터=까지의 것으로 가능합니다.")
	private String password; // 비밀번호

	@NotBlank(message = "Address is mandatory")
	private String address; // 주소

	@NotBlank(message = "Phone number is mandatory")
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
	private String phoneNumber;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	@Pattern(regexp = ".+@.+\\..+", message = "Email 형식이 올바르지 않습니다.")
	private String email; // 이메일

	@NotNull(message = "Birthdate is mandatory")
	@Past(message = "Birthdate must be in the past")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthdate; // 생일

	@Min(value = 0, message = "Points must be greater than or equal to 0")
	private int points; // 포인트

	@Min(value = 0, message = "Total payment amount must be greater than or equal to 0")
	private BigDecimal totalPaymentAmount; // 누적 결제 금액
}
