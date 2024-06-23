package com.final_exam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "admins")
@Data
public class Admin {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminNo; // 관리자 번호 (기본 키, 자동 증가)

    @NotBlank(message = "아이디가 비었습니다.")
    @Size(min = 4, message = "아이디는 4자 이상이어야 합니다.")
    private String adminId; // 관리자 아이디

    @NotBlank(message = "비밀번호가 비었습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d`~!@#$%^&*()-_=+]{6,}$", message = "비밀번호는 6자리 이상이어야 하며, 숫자와 영문자를 포함해야 합니다.\n 특수문자는 키보드의 `부터=까지의 것으로 가능합니다.")
    private String adminPassword; // 관리자 비밀번호

    @NotBlank(message = "이름이 비었습니다.")
    @Size(min = 2, message = "이름은 2자 이상이어야 합니다.")
    private String adminName; // 관리자 이름

    @NotBlank(message = "직책이 비었습니다.")
    @Size(min = 2, message = "직책은 2자 이상이어야 합니다.")
    private String adminPosition; // 관리자 직책

    @NotBlank(message = "전화번호가 비었습니다.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String adminPhoneNumber; // 관리자 전화번호

	public int getAdminNo() {
		return adminNo;
	}

	public void setAdminNo(int adminNo) {
		this.adminNo = adminNo;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPosition() {
		return adminPosition;
	}

	public void setAdminPosition(String adminPosition) {
		this.adminPosition = adminPosition;
	}

	public String getAdminPhoneNumber() {
		return adminPhoneNumber;
	}

	public void setAdminPhoneNumber(String adminPhoneNumber) {
		this.adminPhoneNumber = adminPhoneNumber;
	}
    
}

