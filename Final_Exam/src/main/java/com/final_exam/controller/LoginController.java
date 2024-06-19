package com.final_exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.final_exam.entity.Member;
import com.final_exam.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String loginForm(Model model, HttpSession session, HttpServletResponse response) {
		// 캐시 제어 헤더 설정
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
    	// 이미 로그인해서 user라는 세션이 있으면 index로 리다이렉션
    	if(session.getAttribute("user")!=null) {
            return "redirect:/";
    	}
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password, HttpSession session, Model model, HttpServletResponse response) {
        Member member = memberService.authenticate(id, password);
		// 캐시 제어 헤더 설정
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
        if (member != null) {
            session.setAttribute("user", new User(member.getId()));
            return "redirect:/";
        } else {
            model.addAttribute("error", "잘못된 정보를 입력하셨습니다.");
            return "login";
        }
    }

    static class User {
        private String id;

        public User(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
