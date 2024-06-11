package com.final_exam.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.final_exam.entity.CustomOrder;
import com.final_exam.entity.Product;
import com.final_exam.service.CustomOrderService;
import com.final_exam.service.ProductService;

import jakarta.validation.Valid;

@Controller
public class CustomOrderController {

	@Autowired
	private CustomOrderService customOrderService;

	@Autowired
	private ProductService productService;

	// 맞춤 주문 관리 페이지를 반환합니다.
	@GetMapping("/custom-orders-management")
	public String viewCustomOrderManagementPage() {
		return "custom-order-management";
	}

	// 새로운 맞춤 주문 추가 페이지로 이동
	@GetMapping("/custom-orders/new")
	public String showAddCustomOrderForm(Model model, HttpSession session) {
		CustomOrder customOrder = new CustomOrder();
		List<Product> products = productService.getAllProducts();
		model.addAttribute("customOrder", customOrder);
		model.addAttribute("products", products);
		session.setAttribute("visitedCustomOrderForm", true);
		return "custom-order-form";
	}

	// 새로운 맞춤 주문 추가 처리
	@PostMapping("/custom-orders")
	public String addCustomOrder(@Valid @ModelAttribute("customOrder") CustomOrder customOrder, BindingResult result,
			Model model, HttpSession session) {
		if (result.hasErrors()) {
			List<Product> products = productService.getAllProducts();
			model.addAttribute("products", products);
			return "custom-order-form";
		}
		customOrderService.saveCustomOrder(customOrder);
		session.setAttribute("customOrderSaved", true);
		session.removeAttribute("visitedCustomOrderForm");
		return "redirect:/custom-orders";
	}

	// 맞춤 주문 목록 페이지로 이동
	@GetMapping("/custom-orders")
	public String viewCustomOrderList(Model model) {
		List<CustomOrder> customOrders = customOrderService.getAllCustomOrders();
		model.addAttribute("customOrders", customOrders);
		return "custom-order-list";
	}

	// 맞춤 주문 수정 페이지로 이동
	@GetMapping("/custom-orders/edit/{id}")
	public String showEditCustomOrderForm(@PathVariable("id") int id, Model model, HttpSession session) {
		CustomOrder customOrder = customOrderService.getCustomOrderById(id);
		List<Product> products = productService.getAllProducts();
		model.addAttribute("customOrder", customOrder);
		model.addAttribute("products", products);
		session.setAttribute("visitedEditCustomOrderForm", true);
		return "custom-order-edit";
	}

	// 맞춤 주문 수정 처리
	@PostMapping("/custom-orders/edit/{id}")
	public String editCustomOrder(@PathVariable("id") int id,
			@Valid @ModelAttribute("customOrder") CustomOrder customOrder, BindingResult result, Model model,
			HttpSession session) {
		if (result.hasErrors()) {
			List<Product> products = productService.getAllProducts();
			model.addAttribute("products", products);
			return "custom-order-form";
		}
		customOrderService.updateCustomOrder(id, customOrder);
		session.setAttribute("customOrderUpdated", true);
		session.removeAttribute("visitedEditCustomOrderForm");
		return "redirect:/custom-orders";
	}

	// 맞춤 주문 삭제 처리
	@PostMapping("/custom-orders/delete/{id}")
	public String deleteCustomOrder(@PathVariable("id") int id, HttpSession session) {
		customOrderService.deleteCustomOrder(id);
		session.setAttribute("customOrderDeleted", true);
		return "redirect:/custom-orders";
	}
}
