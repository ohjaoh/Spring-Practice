package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.test.entity.Product;
import com.test.service.ProductService;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String viewHomePage() {
        return "index"; // index.html 뷰를 반환
    }

    @GetMapping("/product-form")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form"; // product-form.html 뷰를 반환
    }

    @PostMapping("/products")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model) {
        // @Valid: 유효성 검사를 수행
        // BindingResult: 유효성 검사 결과를 담는 객체
        if (result.hasErrors()) {
            // 유효성 검사 오류가 있는 경우 다시 입력 폼으로 이동
            return "product-form";
        }
        productService.saveProduct(product);
        return "redirect:/product-list"; // product-list.html로 리다이렉트
    }

    @GetMapping("/product-list")
    public String viewProductList(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list"; // product-list.html 뷰를 반환
    }

    @GetMapping("/product-edit/{id}")
    public String showEditProductForm(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-edit"; // product-edit.html 뷰를 반환
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable("id") int id, @Valid @ModelAttribute("product") Product productDetails, BindingResult result, Model model) {
        // @Valid: 유효성 검사를 수행
        // BindingResult: 유효성 검사 결과를 담는 객체
        if (result.hasErrors()) {
            // 유효성 검사 오류가 있는 경우 다시 수정 폼으로 이동
            model.addAttribute("product", productDetails);
            return "product-edit";
        }
        Product product = productService.getProductById(id);
        if (product != null) {
            product.setProductName(productDetails.getProductName());
            product.setProductCost(productDetails.getProductCost());
            product.setProductCategory(productDetails.getProductCategory());
            productService.saveProduct(product);
        }
        return "redirect:/product-list";
    }
}
