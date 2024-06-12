package com.final_exam.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.final_exam.entity.Member;
import com.final_exam.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 모든 요청에 대해 캐시를 비활성화하는 헤더를 설정합니다.
    @ModelAttribute
    public void setNoCacheHeaders(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    // 회원 관리 페이지를 반환합니다.
    @GetMapping("/member-management")
    public String viewMemberManagementPage() {
        return "member-management";
    }

    // 회원 입력 폼을 보여줍니다.
    @GetMapping("/member-form")
    public String showMemberForm(Model model, HttpSession session) {
        model.addAttribute("member", new Member());
        session.setAttribute("visitedMemberForm", true);
        return "member-form";
    }

    // 새로운 회원을 저장합니다.
    @PostMapping("/members")
    public String saveMember(@Valid @ModelAttribute("member") Member member, BindingResult result, Model model,
                             HttpSession session) {
        // 유효성 검사에 오류가 있는 경우 폼을 다시 반환합니다.
        if (result.hasErrors()) {
            return "member-form";
        }
        // 유효성 검사가 통과되면 회원을 저장합니다.
        memberService.saveMember(member);
        session.setAttribute("memberSaved", true);
        session.removeAttribute("visitedMemberForm");
        return "redirect:/member-list";
    }

    // 회원 목록을 보여줍니다.
    @GetMapping("/member-list")
    public String viewMemberList(Model model, HttpSession session) {
        session.removeAttribute("memberSaved");
        List<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "member-list";
    }

    // 회원 수정 폼을 보여줍니다.
    @GetMapping("/member-edit/{id}")
    public String showEditMemberForm(@PathVariable("id") int id, Model model, HttpSession session) {
        Member member = memberService.getMemberById(id); // 여기서 항상 최신 데이터를 가져옵니다.
        model.addAttribute("member", member);
        session.setAttribute("visitedEditForm", true);
        return "member-edit";
    }

    // 회원 정보를 업데이트합니다.
    @PutMapping("/members/{id}")
    @ResponseBody
    public ResponseEntity<String> updateMember(@PathVariable("id") int id, @Valid @RequestBody Member memberDetails,
                                               BindingResult result, HttpSession session) {
        // 유효성 검사 오류가 있는 경우, 400 Bad Request 응답을 반환합니다.
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors occurred");
        }
        
        // 주어진 ID를 사용하여 회원을 검색합니다.
        Member member = memberService.getMemberById(id);
        
        // 회원이 존재하는 경우
        if (member != null) {
            // 회원의 각 필드를 업데이트합니다.
            member.setUsername(memberDetails.getUsername());
            member.setPassword(memberDetails.getPassword());
            member.setAddress(memberDetails.getAddress());
            member.setPhoneNumber(memberDetails.getPhoneNumber());
            member.setEmail(memberDetails.getEmail());
            member.setBirthdate(memberDetails.getBirthdate());
            member.setPoints(memberDetails.getPoints());
            member.setTotalPaymentAmount(memberDetails.getTotalPaymentAmount());
            
            // 업데이트된 회원 정보를 저장합니다.
            memberService.saveMember(member);
            session.setAttribute("memberUpdated", true);
            session.removeAttribute("visitedEditForm");
            
            // 200 OK 응답을 반환하고, 업데이트가 성공적으로 완료되었음을 알립니다.
            return ResponseEntity.ok("Member updated successfully");
        }
        
        // 회원을 찾을 수 없는 경우, 404 Not Found 응답을 반환합니다.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
    }

    // 회원을 삭제합니다.
    @DeleteMapping("/members/{id}")
    public void deleteMember(@PathVariable("id") int id, HttpSession session, HttpServletResponse response)
            throws IOException {
        memberService.deleteMember(id);
        session.setAttribute("memberDeleted", true);
        response.sendRedirect("/member-list");
    }
}
