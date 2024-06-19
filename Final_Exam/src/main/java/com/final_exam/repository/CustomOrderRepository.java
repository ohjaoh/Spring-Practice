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
	

    // 리스트인 이유 여러회원이 검색될 수 있음 지금은 실명, 이메일주소, 전화번호 등으로 검색이 되는 중인데 나중에 수정해도 됨
	@Query("SELECT o FROM CustomOrder o WHERE o.orderName LIKE %:keyword% OR o.orderProductCode LIKE %:keyword% OR o.orderPhoneNumber LIKE %:keyword%")
	List<CustomOrder> searchByKeyword(@Param("keyword") String keyword);

}
