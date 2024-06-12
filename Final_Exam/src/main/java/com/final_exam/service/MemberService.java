package com.final_exam.service;

import com.final_exam.entity.Member;
import com.final_exam.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 회원 등록 메서드
    public Member registerMember(Member member) {
        // 포인트와 누적 결제 금액의 기본값 설정
        member.setPoints(50);
        member.setTotalPaymentAmount(BigDecimal.ZERO);

        // 회원 저장
        return memberRepository.save(member);
    }

    // 이메일로 회원 찾기 메서드
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // 모든 회원 가져오기 메서드
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // ID로 회원 찾기 메서드
    public Member getMemberById(int id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        return optionalMember.orElse(null);
    }

    // 회원 정보 저장(수정) 메서드
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    // 회원 삭제 메서드
    public void deleteMember(int id) {
        memberRepository.deleteById(id);
    }
    
    public Member authenticate(String username, String password) {
        // 실제 인증 로직 구현
        return memberRepository.findByUsernameAndPassword(username, password);
    }
    
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
    

    public boolean isUsernameTaken(String username) {
        return memberRepository.findByUsername(username) != null;
    }
    
}
