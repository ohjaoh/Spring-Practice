package com.final_exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // 인증 로직 (이 예제에서는 단순히 사용자를 세션에 저장)
        // 실제로는 사용자 인증을 추가해야 함
        User user = authenticate(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            return "redirect:/login?error";
        }
    }

    private User authenticate(String username, String password) {
        // 실제 인증 로직 구현
        // 예제에서는 단순히 사용자 객체를 반환
        if ("admin".equals(username) && "password".equals(password)) {
            return new User(username);
        }
        return null;
    }

    static class User {
        private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
