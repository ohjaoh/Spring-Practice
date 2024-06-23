package com.final_exam.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	// ID 중복 체크 엔드포인트 추가
	@GetMapping("/check-id")
	@ResponseBody
	public String checkId(@RequestParam("id") String id) {
		boolean isTaken = memberService.isIdTaken(id);
		return isTaken ? "TAKEN" : "AVAILABLE";
	}

	// 새로운 회원을 저장합니다.
	@PostMapping("/members")
	public String saveMember(@Valid @ModelAttribute("member") Member member, BindingResult result, Model model,
			HttpSession session) {
		// 유효성 검사에 오류가 있는 경우 폼을 다시 반환합니다.
		if (result.hasErrors()) {
			// 유효성 검사 오류 메시지 출력
			result.getAllErrors()
					.forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));

			return "member-form";
		}
		// 새로운 회원의 포인트를 50으로 설정
		member.setPoints(50);
		// ID 중복 체크
		if (memberService.isIdTaken(member.getId())) {
			result.rejectValue("id", "error.member", "이미 존재하는 ID입니다.");
			return "member-form";
		}
		System.out.println("유효성통과");
		// 유효성 검사가 통과되면 회원을 저장합니다.
		
		memberService.saveMember(member);
		session.setAttribute("memberSaved", true);
		session.removeAttribute("visitedMemberForm");
		return "redirect:/member-list";
	}

	// 회원 목록을 보여줍니다.
	@GetMapping("/member-list")
	public String viewMemberList(Model model, HttpSession session) {
		// 세션에서 "user" 속성을 확인합니다. 여기를 관리자로 변경해야함
		if (session.getAttribute("admin") == null) {
			// "user"가 없으면 index.html로 리다이렉트합니다.
			return "redirect:/";
		}
		session.removeAttribute("memberSaved");
		List<Member> members = memberService.getAllMembers();
		model.addAttribute("members", members);
		return "member-list";
	}

	// 회원 수정 폼을 보여줍니다.
	@GetMapping("/member-edit/{userNo}")
	public String showEditMemberForm(@PathVariable("userNo") int userNo, Model model, HttpSession session) {
		Member member = memberService.getMemberByUserNo(userNo);
		if (session.getAttribute("user") == null && session.getAttribute("admin") == null) {
			return "redirect:/";
		}

		// 생일을 yyyy-MM-dd 형식으로 변환
		String birthdateFormatted = member.getBirthdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		System.out.println(birthdateFormatted);
		model.addAttribute("member", member);
		model.addAttribute("birthdateFormatted", birthdateFormatted);
		session.setAttribute("visitedEditForm", true);
		return "member-edit";
	}

	// 회원 정보를 업데이트합니다.
	// 회원 정보를 업데이트합니다.
	@PutMapping("/members/{userNo}")
	public String updateMember(@PathVariable("userNo") int userNo,
	        @Valid @ModelAttribute("member") Member memberDetails, BindingResult result, HttpSession session, Model model) {
	    if (result.hasErrors()) {
	        result.getAllErrors()
	                .forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
	        model.addAttribute("member", memberDetails);
	        return "member-edit";
	    }

	    Member member = memberService.getMemberByUserNo(userNo);

	    if (member != null) {
	        member.setId(memberDetails.getId());
	        member.setRealName(memberDetails.getRealName());
	        member.setPassword(memberDetails.getPassword());
	        member.setAddress(memberDetails.getAddress());
	        member.setPhoneNumber(memberDetails.getPhoneNumber());
	        member.setEmail(memberDetails.getEmail());
	        member.setBirthdate(memberDetails.getBirthdate());
	        member.setPoints(memberDetails.getPoints());
	        memberService.saveMember(member);
	        session.setAttribute("memberUpdated", true);
	        session.removeAttribute("visitedEditForm");

	        if(session.getAttribute("user") != null) {
	        	return "redirect:/my-page";
	        }
	        else if(session.getAttribute("admin") != null) {
	        	return "redirect:/member-list";
	        }
	    }

	    return "redirect:/";
	}


	// 회원을 삭제합니다.
	@DeleteMapping("/members/{userNo}")
	public void deleteMember(@PathVariable("userNo") int userNo, HttpSession session, HttpServletResponse response)
			throws IOException {
		memberService.deleteMember(userNo);
		session.setAttribute("memberDeleted", true);
		response.sendRedirect("/member-list");
	}
	
	//회원을 검색합니다.
	@GetMapping("/member-search")
    public String searchMembers(@RequestParam("keyword") String keyword, Model model) {
        List<Member> members = memberService.searchMembers(keyword);
        model.addAttribute("members", members);
        return "member-list";
    }
	
}
