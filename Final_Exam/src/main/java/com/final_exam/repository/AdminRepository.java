package com.final_exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.final_exam.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    // 로그인 시 사용하는 메서드
    Admin findByAdminIdAndAdminPassword(String adminId, String adminPassword);

    // ID 중복 체크를 위한 메서드
    boolean existsByAdminId(String adminId);

    // 관리자 ID로 관리자 찾기 메서드
    Admin findByAdminId(String adminId);

    // 관리자 검색 메서드
    @Query("SELECT a FROM Admin a WHERE a.adminName LIKE %:keyword% OR a.adminPosition LIKE %:keyword% OR a.adminPhoneNumber LIKE %:keyword%")
    List<Admin> searchByKeyword(@Param("keyword") String keyword);
}
