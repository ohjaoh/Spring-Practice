package com.final_exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.final_exam.entity.Product;
import com.final_exam.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public Product findByProductCode(int productCode) {
        return productRepository.findById(productCode).orElse(null);
    }
    
    // 검색 메서드
    public List<Product> searchProducts(String keyword) {
        Integer keywordInt = null;
        String keywordForSearch = null;

        try {
            keywordInt = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            // keyword가 숫자가 아닌 경우 와일드카드 포함하여 검색어 설정
            keywordForSearch = "%" + keyword + "%";
        }

        // 숫자인 경우 null로 설정하고, 그렇지 않은 경우 와일드카드 포함된 keywordForSearch 사용
        return productRepository.searchByKeyword(keywordForSearch, keywordInt);
    }


}
