package com.final_exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(HttpSession session) {
		// 회원로그인여부로 했는데 관리자도 추가해야함
    	// 회원 혹은 관리자 로그인 여부 확인
        if (session.getAttribute("user") == null && session.getAttribute("admin") == null) {
            // "user"가 없으면 login으로 리다이렉트합니다.
            return "redirect:/login";
        }
        return "index";
    }
}
