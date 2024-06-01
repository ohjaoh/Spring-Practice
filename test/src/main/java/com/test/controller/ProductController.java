package com.test.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.test.entity.Product;
import com.test.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @ModelAttribute
    public void setNoCacheHeaders(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    @GetMapping("/product-management")
    public String viewProductManagementPage() {
        return "product-management";
    }

    @GetMapping("/product-form")
    public String showProductForm(Model model, HttpSession session) {
        model.addAttribute("product", new Product());
        session.setAttribute("visitedProductForm", true);
        return "product-form";
    }

    @PostMapping("/products")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "product-form";
        }
        productService.saveProduct(product);
        session.setAttribute("productSaved", true);
        session.removeAttribute("visitedProductForm");
        return "redirect:/product-list";
    }

    @GetMapping("/product-list")
    public String viewProductList(Model model, HttpSession session) {
        session.removeAttribute("productSaved");
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/product-edit/{id}")
    public String showEditProductForm(@PathVariable("id") int id, Model model, HttpSession session) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        session.setAttribute("visitedEditForm", true);
        return "product-edit";
    }

    @PutMapping("/products/{id}")
    @ResponseBody
    public ResponseEntity<String> updateProduct(@PathVariable("id") int id, @Valid @RequestBody Product productDetails, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors occurred");
        }
        Product product = productService.getProductById(id);
        if (product != null) {
            product.setProductName(productDetails.getProductName());
            product.setProductCost(productDetails.getProductCost());
            product.setProductCategory(productDetails.getProductCategory());
            productService.saveProduct(product);
            session.setAttribute("productUpdated", true);
            session.removeAttribute("visitedEditForm");
            return ResponseEntity.ok("Product updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @DeleteMapping("/products/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id, HttpSession session) {
        productService.deleteProduct(id);
        session.setAttribute("productDeleted", true);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
