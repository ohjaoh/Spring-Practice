package com.final_exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.final_exam.entity.CustomOrder;

@Repository
public interface CustomOrderRepository extends JpaRepository<CustomOrder, Integer> {

}
