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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.final_exam.entity.Product;
import com.final_exam.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	// 모든 요청에 대해 캐시를 비활성화하는 헤더를 설정합니다.
	@ModelAttribute
	public void setNoCacheHeaders(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	// 제품 관리 페이지를 반환합니다.
	@GetMapping("/product-management")
	public String viewProductManagementPage(HttpSession session) {
		// 세션에서 "user" 속성을 확인합니다. 여기를 관리자로 변경해야함
        if (session.getAttribute("admin") == null) {
            // "user"가 없으면 index.html로 리다이렉트합니다.
            return "redirect:/";
        }
		return "product-management";
	}

	// 제품 입력 폼을 보여줍니다.
	@GetMapping("/product-form")
	public String showProductForm(Model model, HttpSession session) {
		// 세션에서 "user" 속성을 확인합니다. 여기를 관리자로 변경해야함
        if (session.getAttribute("admin") == null) {
            // "user"가 없으면 index.html로 리다이렉트합니다.
            return "redirect:/";
        }
		model.addAttribute("product", new Product());
		session.setAttribute("visitedProductForm", true);
		return "product-form";
	}

	// 새로운 제품을 저장합니다.
	@PostMapping("/products")
	public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model,
			HttpSession session) {
		if (result.hasErrors()) {
			return "product-form";
		}
		productService.saveProduct(product);
		session.setAttribute("productSaved", true);
		session.removeAttribute("visitedProductForm");
		return "redirect:/product-list";
	}

	// 제품 목록을 보여줍니다.
	@GetMapping("/product-list")
	public String viewProductList(Model model, HttpSession session) {
		// 세션에서 "user" 속성을 확인합니다. 여기를 관리자로 변경해야함
        if (session.getAttribute("admin") == null) {
            // "user"가 없으면 index.html로 리다이렉트합니다.
            return "redirect:/";
        }
		session.removeAttribute("productSaved");
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "product-list";
	}

	// 제품 수정 폼을 보여줍니다.
	@GetMapping("/product-edit/{id}")
	public String showEditProductForm(@PathVariable("id") int id, Model model, HttpSession session) {
		// 세션에서 "user" 속성을 확인합니다. 여기를 관리자로 변경해야함
        if (session.getAttribute("admin") == null) {
            // "user"가 없으면 index.html로 리다이렉트합니다.
            return "redirect:/";
        }
		Product product = productService.getProductById(id); // 여기서 항상 최신 데이터를 가져옵니다.
		model.addAttribute("product", product);
		session.setAttribute("visitedEditForm", true);
		return "product-edit";
	}

	// 제품 정보를 업데이트합니다.
	@PutMapping("/products/{id}")
	@ResponseBody
	public ResponseEntity<String> updateProduct(@PathVariable("id") int id, @Valid @RequestBody Product productDetails,
	        BindingResult result, HttpSession session) {
	    
	    // 유효성 검사 오류가 있는 경우, 400 Bad Request 응답을 반환합니다.
	    if (result.hasErrors()) {
	        return ResponseEntity.badRequest().body("Validation errors occurred");
	    }
	    
	    // 주어진 ID를 사용하여 제품을 검색합니다.
	    Product product = productService.getProductById(id);
	    
	    // 제품이 존재하는 경우
	    if (product != null) {
	        // 제품의 이름, 가격, 카테고리, 색상을 업데이트합니다.
	        product.setProductName(productDetails.getProductName());
	        product.setProductCost(productDetails.getProductCost());
	        product.setProductCategory(productDetails.getProductCategory());
	        product.setProductColor(productDetails.getProductColor());;
	        product.setSalesRate(productDetails.getSalesRate());
	        
	        // 업데이트된 제품 정보를 저장합니다.
	        productService.saveProduct(product);
	        
	        // 세션에 'productUpdated' 속성을 설정하여 업데이트 완료를 표시합니다.
	        session.setAttribute("productUpdated", true);
	        
	        // 세션에서 'visitedEditForm' 속성을 제거합니다.
	        session.removeAttribute("visitedEditForm");
	        
	        // 200 OK 응답을 반환하고, 업데이트가 성공적으로 완료되었음을 알립니다.
	        return ResponseEntity.ok("Product updated successfully");
	    }
	    
	    // 제품을 찾을 수 없는 경우, 404 Not Found 응답을 반환합니다.
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}

	// 제품을 삭제합니다.
	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable("id") int id, HttpSession session, HttpServletResponse response)
			throws IOException {
		productService.deleteProduct(id);
		session.setAttribute("productDeleted", true);
		response.sendRedirect("/product-list");
	}
	
	@GetMapping("/user-products")
    public String viewUserProductList(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "user-product-list";
    }

    @GetMapping("/user-products/{code}")
    public String viewUserProductDetail(@PathVariable("code") int code, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        Product product = productService.findByProductCode(code);
        model.addAttribute("product", product);
        return "user-product-detail";
    }
    
	//상품을 검색합니다.
	@GetMapping("/product-search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = productService.searchProducts(keyword);
        model.addAttribute("products", products);
        return "product-list";
    }
	
	
}
