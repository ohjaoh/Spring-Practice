package com.final_exam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.final_exam.controller.HashUtil;
import com.final_exam.entity.Admin;
import com.final_exam.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // 관리자 등록 메서드
    public Admin registerAdmin(Admin admin) {
        // 비밀번호 해싱
        if (!isPasswordHashed(admin.getAdminPassword())) {
            String hashedPassword = HashUtil.hashPassword(admin.getAdminPassword());
            admin.setAdminPassword(hashedPassword);
        }

        // 디버그: 비밀번호가 해시된 상태인지 확인
        if (isPasswordHashed(admin.getAdminPassword())) {
            System.out.println("비밀번호가 정상적으로 해시되었습니다.");
        } else {
            System.out.println("비밀번호 해시에 실패했습니다.");
        }

        // 관리자 저장
        return adminRepository.save(admin);
    }

    // 모든 관리자 가져오기 메서드
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // 관리자 번호로 관리자 찾기 메서드
    public Admin getAdminByAdminNo(int adminNo) {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminNo);
        return optionalAdmin.orElse(null);
    }

    // 관리자 정보 저장(수정) 메서드
    public Admin saveAdmin(Admin admin) {
        // 비밀번호가 해시되지 않은 경우에만 해시
        if (!isPasswordHashed(admin.getAdminPassword())) {
            String hashedPassword = HashUtil.hashPassword(admin.getAdminPassword());
            admin.setAdminPassword(hashedPassword);
        }

        // 디버그: 비밀번호가 해시된 상태인지 확인
        if (isPasswordHashed(admin.getAdminPassword())) {
            System.out.println("비밀번호가 정상적으로 해시되었습니다.");
        } else {
            System.out.println("비밀번호 해시에 실패했습니다.");
        }

        return adminRepository.save(admin);
    }

    // 관리자 삭제 메서드
    public void deleteAdmin(int adminNo) {
        adminRepository.deleteById(adminNo);
    }

    // 인증 메서드
    public Admin authenticate(String adminId, String password) {
        Admin admin = adminRepository.findByAdminId(adminId);
        if (admin != null) {
            // 입력한 비밀번호를 해시하여 저장된 해시와 비교
            String hashedPassword = HashUtil.hashPassword(password);
            if (admin.getAdminPassword().equals(hashedPassword)) {
                return admin;
            }
        }
        return null;
    }

    public boolean isAdminIdTaken(String adminId) {
        return adminRepository.existsByAdminId(adminId);
    }
    
    // 검색 메서드
    public List<Admin> searchAdmins(String keyword) {
        return adminRepository.searchByKeyword(keyword);
    }

    // 비밀번호가 해시된 상태인지 확인하는 메서드
    private boolean isPasswordHashed(String password) {
        // SHA-256 해시의 경우 64자의 길이를 가지므로 이를 통해 해시 여부를 간단히 확인
        return password.length() == 64;
    }
}
