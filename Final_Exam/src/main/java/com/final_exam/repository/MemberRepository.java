package com.final_exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.final_exam.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    // 이메일로 회원을 찾는 메서드
    Member findByEmail(String email);

    // 로그인 시 사용하는 메서드
    Member findByIdAndPassword(String id, String password);

    // mypage 방문 시 본인의 정보를 확인하는 메서드
    Member findById(String id);

    // ID 중복 체크를 위한 메서드
    boolean existsById(String id);

    // 리스트인 이유 여러회원이 검색될 수 있음 지금은 실명, 이메일주소, 전화번호 등으로 검색이 되는 중인데 나중에 수정해도 됨
    @Query("SELECT m FROM Member m WHERE m.realName LIKE %:keyword% OR m.email LIKE %:keyword% OR m.phoneNumber LIKE %:keyword%")
    List<Member> searchByKeyword(@Param("keyword") String keyword);
}
