package com.final_exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.final_exam.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // 추가적인 쿼리 메소드를 정의할 수 있습니다.
	@Query("SELECT p FROM Product p WHERE (:keywordInt IS NULL OR p.productCode = :keywordInt) AND (:keyword IS NULL OR p.productName LIKE :keyword)")
	List<Product> searchByKeyword(@Param("keyword") String keyword, @Param("keywordInt") Integer keywordInt);

}
