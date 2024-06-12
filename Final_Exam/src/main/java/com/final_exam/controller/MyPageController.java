package com.final_exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.final_exam.entity.Member;
import com.final_exam.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/my-page")
    public String myPage(HttpSession session, Model model) {
        LoginController.User user = (LoginController.User) session.getAttribute("user");
        if (user != null) {
            Member member = memberService.findByUsername(user.getName());
            model.addAttribute("member", member);
            return "my-page";
        }
        return "redirect:/login";
    }
}
