package com.final_exam.repository;

import com.final_exam.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
