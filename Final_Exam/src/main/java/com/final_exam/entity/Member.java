package com.final_exam.entity;

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
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "members")
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 회원 아이디 (기본 키)
    private int id;

    @NotBlank(message = "Username is mandatory")
    // 사용자 이름 (공백이 아닌 값을 입력해야 함)
    private String username;

    @NotBlank(message = "Password is mandatory")
    // 비밀번호 (공백이 아닌 값을 입력해야 함)
    private String password;

    @NotBlank(message = "Address is mandatory")
    // 주소 (공백이 아닌 값을 입력해야 함)
    private String address;

    @NotBlank(message = "Phone number is mandatory")
    // 전화번호 (공백이 아닌 값을 입력해야 함)
    private String phoneNumber;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    // 이메일 (유효한 이메일 형식이어야 함)
    private String email;

    @NotNull(message = "Birthdate is mandatory")
    @Past(message = "Birthdate must be in the past")
    // 생일 (과거 날짜여야 함)
    private LocalDate birthdate;

    @Min(value = 0, message = "Points must be greater than or equal to 0")
    // 포인트 (0 이상이어야 함)
    private int points;

    @Min(value = 0, message = "Total payment amount must be greater than or equal to 0")
    // 누적 결제 금액 (0 이상이어야 함)
    private BigDecimal totalPaymentAmount;

    // @Data가 정상적으로 동작하지 않아, 직접 작성한 getter와 setter 메서드들
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
