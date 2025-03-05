package com.product.erp.controller;

import com.product.erp.domain.Product;
import com.product.erp.domain.Category;
import com.product.erp.dto.ProductDTO;
import com.product.erp.service.CategoryService;
import com.product.erp.service.ProductService;
import com.product.erp.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    // 제품 목록 (검색, 페이징 처리)
    @GetMapping
    public String showProductList(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        String searchKeyword = (keyword != null) ? keyword.trim() : "";

        Page<Product> products = searchKeyword.isEmpty()
                ? productService.getAllProducts(pageable)
                : productService.getSearchProducts(searchKeyword, pageable);

        model.addAttribute("products", products);
        model.addAttribute("keyword", searchKeyword);
        return "product/product_list";
    }

    // 제품 추가 페이지로 이동하기
    @GetMapping("/add")
    public String showAddProduct(Model model) {
        if (!model.containsAttribute("dto")) {
            model.addAttribute("dto", new ProductDTO());
        }

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        return "product/product_add";
    }

    // 제품 추가 페이지에서 등록하기
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("dto") ProductDTO productDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            ValidationUtils.handleValidationErrors(bindingResult, productDTO, redirectAttributes);
            return "redirect:/products/add";
        }
        try {
            productService.createProduct(productDTO);
        } catch (Exception e) {
            return "redirect:/products/add";
        }
        return "redirect:/products";
    }

    // 제품 수정 페이지로 이동
    @GetMapping("/edit/{productId}")
    public String showEditProduct(@PathVariable("productId") Long id, Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        return "product/product_edit";  // 수정 페이지 이동
    }

    // 제품 수정 등록하기
    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable Long productId,
                                @Valid @ModelAttribute("product") ProductDTO productDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                ValidationUtils.handleValidationErrors(bindingResult, productDTO, redirectAttributes);
                throw new IllegalArgumentException("입력값 검증 실패");
            }
            productService.modifyProduct(productId, productDTO);
        } catch (IllegalArgumentException e) {
            return "redirect:/products/edit/" + productId;
        }
        return "redirect:/products";
    }
}
