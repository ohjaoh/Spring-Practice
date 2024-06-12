package com.final_exam.repository;

import com.final_exam.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    // 이메일로 회원을 찾는 메소드
    Member findByEmail(String email);
}
