package com.final_exam.controller;

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

import com.final_exam.entity.Order;
import com.final_exam.entity.Member;
import com.final_exam.entity.Product;
import com.final_exam.service.OrderService;
import com.final_exam.service.MemberService;
import com.final_exam.service.ProductService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MemberService memberService;

    //  주문 관리 페이지를 반환합니다.
    @GetMapping("/orders-management")
    public String viewOrderManagementPage() {
        return "order-management";
    }

    // 새로운  주문 추가 페이지로 이동
    @GetMapping("/orders/new")
    public String showAddOrderForm(Model model, HttpSession session) {
        LoginController.User user = (LoginController.User) session.getAttribute("user");
        if (user != null) {
            Member member = memberService.findById(user.getId());
            Order order = new Order();
            order.setMember(member); // 회원 정보를  주문에 설정

            List<Product> products = productService.getAllProducts();
            model.addAttribute("order", order);
            model.addAttribute("products", products);
            session.setAttribute("visitedOrderForm", true);
            return "order-form";
        }
        return "redirect:/login";
    }

    @PostMapping("/ordersAdd")
    public String addOrder(@ModelAttribute("order") Order order, BindingResult result,
                           Model model, HttpSession session) {
        // 초기 디버깅 로그
        System.out.println("addOrder 메소드가 호출되었습니다.");

        // 회원 정보 설정
        LoginController.User user = (LoginController.User) session.getAttribute("user");
        if (user == null) {
            System.out.println("User not found in session");
            model.addAttribute("error", "로그인이 필요합니다.");
            return "redirect:/login";
        } else {
            System.out.println("User found in session with ID: " + user.getId());
            Member member = memberService.findById(user.getId());
            if (member == null) {
                System.out.println("Member not found for ID: " + user.getId());
                model.addAttribute("error", "회원 정보를 찾을 수 없습니다.");
                return "redirect:/login";
            } else {
                order.setMember(member);
                System.out.println("Member set with ID: " + member.getId() + ", RealName: " + member.getRealName());
            }
        }

        // 유효성 검사 수행
        if (result.hasErrors()) {
            // 유효성 검사 오류 메시지 출력
            result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
            
            // 유효성 검사 실패 시, 제품 목록을 다시 모델에 추가하고 폼으로 돌아감
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
            return "order-form";
        }

        // 디버깅: 주문 정보 출력
        System.out.println("Order details:");
        System.out.println("Product Code: " + order.getProductCode());
        System.out.println("Delivery Date: " + order.getDeliveryDate());
        System.out.println("Order Name: " + order.getOrderName());
        System.out.println("Order Phone Number: " + order.getOrderPhoneNumber());
        System.out.println("Order Address: " + order.getOrderAddress());

        orderService.saveOrder(order);
        System.out.println("Order saved with product code: " + order.getProductCode());
        session.setAttribute("OrderSaved", true);
        session.removeAttribute("visitedOrderForm");
        return "redirect:/orders";
    }

    //  주문 목록 페이지로 이동
    @GetMapping("/orders")
    public String viewOrderList(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order-list";
    }

    //  주문 수정 페이지로 이동
    @GetMapping("/orders/edit/{serial_no}")
    public String showEditOrderForm(@PathVariable("serial_no") int id, Model model, HttpSession session) {
        Order order = orderService.getOrderById(id);
        List<Product> products = productService.getAllProducts();
        model.addAttribute("order", order);
        model.addAttribute("products", products);
        session.setAttribute("visitedEditOrderForm", true);
        return "order-edit";
    }

    //  주문 수정 처리
    @PostMapping("/orders/edit/{id}")
    public String editOrder(@PathVariable("id") int id,
                                  @Valid @ModelAttribute("order") Order order, BindingResult result, Model model,
                                  HttpSession session) {
        if (result.hasErrors()) {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
            return "order-form";
        }

        // 회원 정보 설정
        LoginController.User user = (LoginController.User) session.getAttribute("user");
        if (user != null) {
            Member member = memberService.findById(user.getId());
            order.setMember(member);
        }

        orderService.updateOrder(id, order);
        session.setAttribute("OrderUpdated", true);
        session.removeAttribute("visitedEditOrderForm");
        return "redirect:/orders";
    }

    //  주문 삭제 처리
    @DeleteMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id, HttpSession session) {
        orderService.deleteOrder(id);
        session.setAttribute("OrderDeleted", true);
        return "redirect:/orders";
    }

}
