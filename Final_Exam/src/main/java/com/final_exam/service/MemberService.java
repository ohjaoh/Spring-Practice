package com.final_exam.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.final_exam.controller.HashUtil;
import com.final_exam.entity.Member;
import com.final_exam.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 회원 등록 메서드
    public Member registerMember(Member member) {
        // 포인트와 누적 결제 금액의 기본값 설정
        member.setPoints(50);
        member.setTotalPaymentAmount(BigDecimal.ZERO);

        // 비밀번호 해싱
        if (!isPasswordHashed(member.getPassword())) {
            String hashedPassword = HashUtil.hashPassword(member.getPassword());
            member.setPassword(hashedPassword);
        }

        // 디버그: 비밀번호가 해시된 상태인지 확인
        if (isPasswordHashed(member.getPassword())) {
            System.out.println("비밀번호가 정상적으로 해시되었습니다.");
        } else {
            System.out.println("비밀번호 해시에 실패했습니다.");
        }

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

    // user_no로 회원 찾기 메서드
    public Member getMemberByUserNo(int userNo) {
        Optional<Member> optionalMember = memberRepository.findById(userNo);
        return optionalMember.orElse(null);
    }

    // 회원 정보 저장(수정) 메서드
    public Member saveMember(Member member) {
        // 비밀번호가 해시되지 않은 경우에만 해시
        if (!isPasswordHashed(member.getPassword())) {
            String hashedPassword = HashUtil.hashPassword(member.getPassword());
            member.setPassword(hashedPassword);
        }

        // 디버그: 비밀번호가 해시된 상태인지 확인
        if (isPasswordHashed(member.getPassword())) {
            System.out.println("비밀번호가 정상적으로 해시되었습니다.");
        } else {
            System.out.println("비밀번호 해시에 실패했습니다.");
        }

        return memberRepository.save(member);
    }

    // 회원 삭제 메서드
    public void deleteMember(int userNo) {
        memberRepository.deleteById(userNo);
    }

    // 인증 메서드
    public Member authenticate(String id, String password) {
        Member member = memberRepository.findById(id);
        if (member != null) {
            // 입력한 비밀번호를 해시하여 저장된 해시와 비교
            String hashedPassword = HashUtil.hashPassword(password);
            if (member.getPassword().equals(hashedPassword)) {
                return member;
            }
        }
        return null;
    }

    public Member findById(String id) {
        return memberRepository.findById(id);
    }

    public boolean isIdTaken(String id) {
        return memberRepository.existsById(id);
    }

    // 검색 메서드
    public List<Member> searchMembers(String keyword) {
        return memberRepository.searchByKeyword(keyword);
    }

    // 비밀번호가 해시된 상태인지 확인하는 메서드
    private boolean isPasswordHashed(String password) {
        // SHA-256 해시의 경우 64자의 길이를 가지므로 이를 통해 해시 여부를 간단히 확인
        return password.length() == 64;
    }
}
