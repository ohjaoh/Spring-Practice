package com.final_exam;
//
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.final_exam.entity.Member;
import com.final_exam.repository.MemberRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class MemberRepositoryTests {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void testCreateMember() {
		Member member = new Member();
		member.setId("member1");
		member.setRealName("Member Name");
		member.setPassword("Password1!");
		member.setAddress("123 Main St");
		member.setPhoneNumber("010-1234-5678");
		member.setEmail("member@example.com");
		member.setBirthdate(LocalDate.of(1990, 1, 1));
		member.setPoints(100);

		Member savedMember = memberRepository.save(member);

		// 디버그 출력
		System.out.println("회원이 저장되었습니다. 저장된 테스트용 회원: " + savedMember);
	}
}
