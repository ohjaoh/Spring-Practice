package com.final_exam.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.final_exam.entity.CustomOrder;
import com.final_exam.entity.Member;
import com.final_exam.entity.Product;
import com.final_exam.service.CustomOrderService;
import com.final_exam.service.MemberService;
import com.final_exam.service.ProductService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CustomOrderController {

	@Autowired
	private CustomOrderService customOrderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private MemberService memberService;

	// 새로운 맞춤 주문 추가 페이지로 이동
	@GetMapping("/custom-orders/new")
	public String showAddCustomOrderForm(Model model, HttpSession session) {
		LoginController.User user = (LoginController.User) session.getAttribute("user");
		if (user != null) {
			Member member = memberService.findById(user.getId());
			CustomOrder customOrder = new CustomOrder();
			customOrder.setMember(member); // 회원 정보를 맞춤 주문에 설정

			List<Product> products = productService.getAllProducts();
			model.addAttribute("customOrder", customOrder);
			model.addAttribute("products", products);
			session.setAttribute("visitedCustomOrderForm", true);
			return "custom-order-form";
		}
		return "redirect:/login";
	}

	// 새로운 맞춤 주문 추가 처리
	@PostMapping("/custom-orders")
	public String addCustomOrder(@Valid @ModelAttribute("customOrder") CustomOrder customOrder, BindingResult result,
			Model model, HttpSession session) {
		// 회원 정보 설정
		LoginController.User user = (LoginController.User) session.getAttribute("user");
		if (user != null) {
			Member member = memberService.findById(user.getId());
			customOrder.setMember(member);
		} else {
			result.rejectValue("member", "error.customOrder", "Member is required");
		}

		if (result.hasErrors()) {
			List<Product> products = productService.getAllProducts();
			model.addAttribute("products", products);
			return "custom-order-form";
		}

		// 유효성 검사가 통과된 경우, 결제 페이지로 리다이렉트
		Product product = productService.findByProductCode(customOrder.getOrderProductCode());
		if (product == null) {
			result.rejectValue("product", "error.customOrder", "Product not found");
			List<Product> products = productService.getAllProducts();
			model.addAttribute("products", products);
			return "custom-order-form";
		}
		session.setAttribute("customOrder", customOrder);
		session.setAttribute("productCost", product.getProductCost());
		return "redirect:/custom-orders/payment";
	}

	// 결제 페이지
	@GetMapping("/custom-orders/payment")
	public String showPaymentPage(HttpSession session, Model model) {
		CustomOrder customOrder = (CustomOrder) session.getAttribute("customOrder");
		BigDecimal productCost = (BigDecimal) session.getAttribute("productCost");
		if (customOrder == null || productCost == null) {
			return "redirect:/custom-orders/new";
		}
		model.addAttribute("customOrder", customOrder);
		model.addAttribute("productCost", productCost);
		System.out.println(productCost);
		return "payment-page";
	}

	// 결제 완료 후 주문 저장
	@PostMapping("/custom-orders/complete")
	public String completeOrder(HttpSession session) {
		CustomOrder customOrder = (CustomOrder) session.getAttribute("customOrder");
		if (customOrder == null) {
			return "redirect:/custom-orders/new";
		}
		System.out.println("저장됨?" + customOrder.getSerial_no());
		
	    // 제품의 sales_rate 증가
	    Product product = customOrder.getProduct();
	    if (product != null) {
	        product.setSalesRate(product.getSalesRate() + 1);
	        productService.saveProduct(product);  // productService를 통해 제품 정보 저장
	    }
		
		customOrderService.saveCustomOrder(customOrder);

		session.removeAttribute("customOrder");
		session.removeAttribute("productCost");
		return "redirect:/";
	}

	// 맞춤 주문 목록 페이지로 이동
	@GetMapping("/custom-orders")
	public String viewCustomOrderList(Model model, HttpSession session) {
		// 세션에서 "user" 속성을 확인합니다. 여기를 관리자로 변경해야함
		if (session.getAttribute("admin") == null) {
			// "user"가 없으면 index.html로 리다이렉트합니다.
			return "redirect:/";
		}
		List<CustomOrder> customOrders = customOrderService.getAllCustomOrders();
		model.addAttribute("customOrders", customOrders);

		return "custom-order-list";
	}

	// 맞춤 주문 수정 페이지로 이동
	@GetMapping("/custom-orders/edit/{id}")
	public String showEditCustomOrderForm(@PathVariable("id") int id, Model model, HttpSession session) {
		// 세션에서 "user" 속성을 확인합니다. 여기를 관리자로 변경해야함
		if (session.getAttribute("admin") == null) {
			// "user"가 없으면 index.html로 리다이렉트합니다.
			return "redirect:/";
		}
		CustomOrder customOrder = customOrderService.getCustomOrderById(id);
		List<Product> products = productService.getAllProducts();
		model.addAttribute("customOrder", customOrder);
		model.addAttribute("products", products);
		session.setAttribute("visitedEditCustomOrderForm", true);
		return "custom-order-edit";
	}

	// 맞춤 주문 수정 처리
	@PostMapping("/custom-orders/{id}")
	public String editCustomOrder(@PathVariable("id") int id,
			@Valid @ModelAttribute("customOrder") CustomOrder customOrder, BindingResult result, Model model,
			HttpSession session) {
		System.out.println("주문수정처리진입");
		// 상품 정보 설정
		Product product = productService.findByProductCode(customOrder.getOrderProductCode());
		if (product != null) {
			customOrder.setProduct(product);
			customOrder.setOrderProductName(product.getProductName());
			customOrder.setOrderProductCode(product.getProductCode());

		} else {
			result.rejectValue("product", "error.customOrder", "Product is required");
		}

		if (result.hasErrors()) {
			// 유효성 검사 오류 메시지 출력
			result.getAllErrors()
					.forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));

			List<Product> products = productService.getAllProducts();
			model.addAttribute("products", products);
			return "custom-order-form";
		}

		// 디버깅: 주문 정보 출력
		System.out.println("CustomOrder details:");
		System.out.println("Product Code: " + customOrder.getOrderProductCode());
		System.out.println("Product Name: " + customOrder.getOrderProductName());
		System.out.println("Customization Details: " + customOrder.getCustomizationDetails());
		System.out.println("Delivery Date: " + customOrder.getDeliveryDate());
		System.out.println("Special Instructions: " + customOrder.getSpecialInstructions());
		System.out.println("Order Name: " + customOrder.getOrderName());
		System.out.println("Order Phone Number: " + customOrder.getOrderPhoneNumber());
		System.out.println("Order Address: " + customOrder.getOrderAddress());

		customOrderService.updateCustomOrder(id, customOrder);
		System.out.println("Custom order saved with product code: " + customOrder.getOrderProductCode());
		session.setAttribute("customOrderSaved", true);
		session.removeAttribute("visitedCustomOrderForm");
		return "redirect:/custom-orders";
	}

	// 맞춤 주문 삭제 처리
	@DeleteMapping("/custom-orders/delete/{id}")
	public String deleteCustomOrder(@PathVariable("id") int id, HttpSession session) {
		customOrderService.deleteCustomOrder(id);
		session.setAttribute("customOrderDeleted", true);
		return "redirect:/custom-orders";
	}

	// 주문을 검색합니다.
	@GetMapping("/custom-order-search")
	public String searchMembers(@RequestParam("keyword") String keyword, Model model) {
		List<CustomOrder> customOrder = customOrderService.searchCustomOrders(keyword);
		model.addAttribute("customOrders", customOrder);
		return "custom-order-list";
	}

}
