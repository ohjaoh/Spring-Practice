package com.final_exam.controller;

import java.io.IOException;
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

import com.final_exam.entity.Admin;
import com.final_exam.service.AdminService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 모든 요청에 대해 캐시를 비활성화하는 헤더를 설정합니다.
    @ModelAttribute
    public void setNoCacheHeaders(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    // 관리자 입력 폼을 보여줍니다.
    @GetMapping("/admin-form")
    public String showAdminForm(Model model, HttpSession session) {
        model.addAttribute("admin", new Admin());
        session.setAttribute("visitedAdminForm", true);
        return "admin-form";
    }

    // ID 중복 체크 엔드포인트 추가
    @GetMapping("/check-admin-id")
    @ResponseBody
    public String checkAdminId(@RequestParam("id") String id) {
        boolean isTaken = adminService.isAdminIdTaken(id);
        return isTaken ? "TAKEN" : "AVAILABLE";
    }

    // 새로운 관리자를 저장합니다.
    @PostMapping("/admins")
    public String saveAdmin(@Valid @ModelAttribute("admin") Admin admin, BindingResult result, Model model, HttpSession session) {
        // 유효성 검사에 오류가 있는 경우 폼을 다시 반환합니다.
        if (result.hasErrors()) {
            result.getAllErrors()
                .forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
            return "admin-form";
        }
        // ID 중복 체크
        if (adminService.isAdminIdTaken(admin.getAdminId())) {
            result.rejectValue("id", "error.admin", "이미 존재하는 ID입니다.");
            return "admin-form";
        }
        // 유효성 검사가 통과되면 관리자를 저장합니다.
        adminService.saveAdmin(admin);
        session.setAttribute("adminSaved", true);
        session.removeAttribute("visitedAdminForm");
        return "redirect:/admin-list";
    }

    // 관리자 목록을 보여줍니다.
    @GetMapping("/admin-list")
    public String viewAdminList(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/";
        }
        session.removeAttribute("adminSaved");
        List<Admin> admins = adminService.getAllAdmins();
        model.addAttribute("admins", admins);
        return "admin-list";
    }

    // 관리자 수정 폼을 보여줍니다.
    @GetMapping("/admin-edit/{adminNo}")
    public String showEditAdminForm(@PathVariable("adminNo") int adminNo, Model model, HttpSession session) {
        Admin admin = adminService.getAdminByAdminNo(adminNo);
        if (session.getAttribute("admin") == null) {
            return "redirect:/";
        }
        model.addAttribute("admin", admin);
        session.setAttribute("visitedEditForm", true);
        return "admin-edit";
    }

    // 관리자 정보를 업데이트합니다.
    @PutMapping("/admins/{adminNo}")
    public String updateAdmin(@PathVariable("adminNo") int adminNo, @Valid @ModelAttribute("admin") Admin adminDetails, BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors()
                .forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
            model.addAttribute("admin", adminDetails);
            return "admin-edit";
        }

        Admin admin = adminService.getAdminByAdminNo(adminNo);

        if (admin != null) {
            admin.setAdminId(adminDetails.getAdminId());
            admin.setAdminName(adminDetails.getAdminName());
            admin.setAdminPassword(adminDetails.getAdminPassword());
            admin.setAdminPosition(adminDetails.getAdminPosition());
            admin.setAdminPhoneNumber(adminDetails.getAdminPhoneNumber());

            adminService.saveAdmin(admin);
            session.setAttribute("adminUpdated", true);
            session.removeAttribute("visitedEditForm");

            return "redirect:/admin-list";
        }

        return "redirect:/";
    }

    // 관리자를 삭제합니다.
    @DeleteMapping("/admins/{adminNo}")
    public void deleteAdmin(@PathVariable("adminNo") int adminNo, HttpSession session, HttpServletResponse response) throws IOException {
        adminService.deleteAdmin(adminNo);
        session.setAttribute("adminDeleted", true);
        response.sendRedirect("/admin-list");
    }

    // 관리자를 검색합니다.
    @GetMapping("/admin-search")
    public String searchAdmins(@RequestParam("keyword") String keyword, Model model) {
        List<Admin> admins = adminService.searchAdmins(keyword);
        model.addAttribute("admins", admins);
        return "admin-list";
    }
}
