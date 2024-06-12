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

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "user_no")
    @NotNull(message = "Member is required")
    private Member member;

    @Column(name = "delivery_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Delivery date is required")
    @FutureOrPresent(message = "Delivery date must be today or in the future")
    private Date deliveryDate;

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

    public Product getProduct_code() {
        return product_code;
    }

    public void setProduct_code(Product product_code) {
        this.product_code = product_code;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
        this.orderName = member.getRealName(); // 회원의 이름을 주문자 이름으로 설정
        this.orderPhoneNumber = member.getPhoneNumber(); // 회원의 전화번호를 주문자 전화번호로 설정
        this.orderAddress = member.getAddress(); // 회원의 주소를 주문자 주소로 설정
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public String getOrderName() {
        return orderName;
    }

    public String getOrderPhoneNumber() {
        return orderPhoneNumber;
    }

    public String getOrderAddress() {
        return orderAddress;
    }
}
