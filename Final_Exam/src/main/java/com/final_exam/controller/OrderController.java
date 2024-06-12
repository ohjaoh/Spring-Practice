// OrderController.java
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
    private OrderService OrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MemberService memberService;

    // 맞춤 주문 관리 페이지를 반환합니다.
    @GetMapping("/orders-management")
    public String viewOrderManagementPage() {
        return "order-management";
    }

    // 새로운 맞춤 주문 추가 페이지로 이동
    @GetMapping("/orders/new")
    public String showAddOrderForm(Model model, HttpSession session) {
        LoginController.User user = (LoginController.User) session.getAttribute("user");
        if (user != null) {
            Member member = memberService.findById(user.getId());
            Order Order = new Order();
            Order.setMember(member); // 회원 정보를 맞춤 주문에 설정

            List<Product> products = productService.getAllProducts();
            model.addAttribute("Order", Order);
            model.addAttribute("products", products);
            session.setAttribute("visitedOrderForm", true);
            return "order-form";
        }
        return "redirect:/login";
    }

    // 새로운 주문 추가 처리
    @PostMapping("/orders")
    public String addOrder(@Valid @ModelAttribute("Order") Order Order, BindingResult result,
                                 Model model, HttpSession session) {
        if (result.hasErrors()) {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
            return "order-form";
        }

        // 회원 정보 설정
        LoginController.User user = (LoginController.User) session.getAttribute("user");
        if (user != null) {
            Member member = memberService.findById(user.getId());
            if (member != null) {
                Order.setMember(member);
                System.out.println("Member set with ID: " + member.getId() + ", RealName: " + member.getRealName()); // 디버깅 로그 출력
            } else {
                System.out.println("Member not found for ID: " + user.getId());
            }
        } else {
            System.out.println("User not found in session");
        }

        // 디버깅: 주문 정보 출력
        System.out.println("Order details:");
        System.out.println("Product Code: " + Order.getProduct_code().getProductCode());
        System.out.println("Delivery Date: " + Order.getDeliveryDate());
        System.out.println("Order Name: " + Order.getOrderName());
        System.out.println("Order Phone Number: " + Order.getOrderPhoneNumber());
        System.out.println("Order Address: " + Order.getOrderAddress());

        OrderService.saveOrder(Order);
        System.out.println("order saved with product code: " + Order.getProduct_code().getProductCode());
        session.setAttribute("OrderSaved", true);
        session.removeAttribute("visitedOrderForm");
        return "redirect:/orders";
    }

    // 맞춤 주문 목록 페이지로 이동
    @GetMapping("/orders")
    public String viewOrderList(Model model) {
        List<Order> Orders = OrderService.getAllOrders();
        model.addAttribute("Orders", Orders);
        return "order-list";
    }

    // 맞춤 주문 수정 페이지로 이동
    @GetMapping("/orders/edit/{id}")
    public String showEditOrderForm(@PathVariable("id") int id, Model model, HttpSession session) {
        Order Order = OrderService.getOrderById(id);
        List<Product> products = productService.getAllProducts();
        model.addAttribute("Order", Order);
        model.addAttribute("products", products);
        session.setAttribute("visitedEditOrderForm", true);
        return "order-edit";
    }

    // 맞춤 주문 수정 처리
    @PostMapping("/orders/edit/{id}")
    public String editOrder(@PathVariable("id") int id,
                                  @Valid @ModelAttribute("Order") Order Order, BindingResult result, Model model,
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
            Order.setMember(member);
        }

        OrderService.updateOrder(id, Order);
        session.setAttribute("OrderUpdated", true);
        session.removeAttribute("visitedEditOrderForm");
        return "redirect:/orders";
    }

    // 맞춤 주문 삭제 처리
    @DeleteMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id, HttpSession session) {
        OrderService.deleteOrder(id);
        session.setAttribute("OrderDeleted", true);
        return "redirect:/orders";
    }

}
