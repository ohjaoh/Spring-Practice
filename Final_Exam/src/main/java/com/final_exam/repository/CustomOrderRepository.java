package com.final_exam.repository;

import com.final_exam.entity.CustomOrder;
import com.final_exam.entity.Member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomOrderRepository extends JpaRepository<CustomOrder, Integer> {
	

    // 리스트인 이유 여러주문이 검색될 수 있음 주문자이름, 주문상품명, 주문자전화번호로 검색가능
	@Query("SELECT o FROM CustomOrder o WHERE o.orderName LIKE %:keyword% OR o.orderProductName LIKE %:keyword% OR o.orderPhoneNumber LIKE %:keyword%")
	List<CustomOrder> searchByKeyword(@Param("keyword") String keyword);

}
