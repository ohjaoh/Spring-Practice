package com.final_exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.final_exam.entity.Admin;
import com.final_exam.service.AdminService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminLoginController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/adminLogin")
    public String adminLoginForm(Model model, HttpSession session, HttpServletResponse response) {
        // 캐시 제어 헤더 설정
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        // 이미 로그인해서 admin이라는 세션이 있으면 adminDashboard로 리다이렉션
        if(session.getAttribute("admin") != null) {
            return "redirect:/";
        }
        return "adminLogin";
    }

    @PostMapping("/adminLogin")
    public String adminLogin(@RequestParam String id, @RequestParam String password, HttpSession session, Model model, HttpServletResponse response) {
        Admin admin = adminService.authenticate(id, password);
        // 캐시 제어 헤더 설정
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        if (admin != null) {
            session.setAttribute("admin", new AdminUser(admin.getAdminId()));
            return "redirect:/";
        } else {
            model.addAttribute("error", "잘못된 정보를 입력하셨습니다.");
            return "adminLogin";
        }
    }

    static class AdminUser {
        private String id;

        public AdminUser(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
