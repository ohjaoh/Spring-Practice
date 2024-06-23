package com.final_exam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.final_exam.entity.Admin;
import com.final_exam.repository.AdminRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class AdminRepositoryTests {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void testCreateAdmin() {
        Admin admin = new Admin();
        admin.setAdminId("admin1");
        admin.setAdminPassword("Password1!");
        admin.setAdminName("Admin Name");
        admin.setAdminPosition("Manager");
        admin.setAdminPhoneNumber("010-1234-5678");

        Admin savedAdmin = adminRepository.save(admin);

        // 디버그 출력
		System.out.println("관리자가 저장되었습니다. 저장된 테스트용 관리자: " + savedAdmin);

        // 저장된 엔티티의 필드 값을 확인
        System.out.println(savedAdmin.getAdminId());
        System.out.println(savedAdmin.getAdminName());
        System.out.println(savedAdmin.getAdminNo());
        System.out.println(savedAdmin.getAdminPassword());
        System.out.println(savedAdmin.getAdminPhoneNumber());
        System.out.println(savedAdmin.getAdminPosition());
    }
}
