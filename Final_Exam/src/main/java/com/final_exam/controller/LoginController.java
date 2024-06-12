package com.final_exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.final_exam.entity.Member;
import com.final_exam.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Member member = memberService.authenticate(username, password);
        if (member != null) {
            session.setAttribute("user", new User(member.getUsername()));
            return "redirect:/";
        } else {
            model.addAttribute("error", "잘못된 정보를 입력하셨습니다.");
            return "login";
        }
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
