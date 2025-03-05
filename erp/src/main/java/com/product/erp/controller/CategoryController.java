package com.product.erp.controller;

import com.product.erp.domain.Category;
import com.product.erp.dto.CategoryDTO;
import com.product.erp.service.CategoryService;
import com.product.erp.util.ValidationUtils;
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

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    // 제품 분류 목록 (검색, 페이징 처리)
    @GetMapping
    public String showCategories(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        String searchKeyword = (keyword != null) ? keyword.trim() : "";

        Page<Category> categories = searchKeyword.isEmpty()
                ? categoryService.getAllCategories(pageable)
                : categoryService.getSearchCategories(searchKeyword, pageable);

        model.addAttribute("categories", categories);
        model.addAttribute("keyword", searchKeyword);
        return "category/category_list";
    }

    // 제품 분류 추가 페이지
    @GetMapping("/add")
    public String showAddCategoryPage(Model model) {
        model.addAttribute("dto", new CategoryDTO());
        return "category/category_add";
    }

    // 제품 분류 저장
    @PostMapping("/save")
    public String saveCategory(@Valid @ModelAttribute("dto") CategoryDTO categoryDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            ValidationUtils.handleValidationErrors(bindingResult, categoryDTO, redirectAttributes);
            return "redirect:/categories/add";
        }

        try {
            categoryService.createCategory(categoryDTO);
        } catch (Exception e) {
            return "redirect:/categories/add";
        }

        return "redirect:/categories";
    }

    // 제품 분류 수정 페이지 이동
    @GetMapping("/edit/{categoryId}")
    public String showEditCategoryPage(@PathVariable Long categoryId, Model model) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
        model.addAttribute("dto", categoryDTO);
        return "category/category_edit";
    }

    // 제품 분류 수정
    @PostMapping("/update/{categoryId}")
    public String updateCategory(@PathVariable Long categoryId,
                                 @Valid @ModelAttribute("dto") CategoryDTO categoryDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            ValidationUtils.handleValidationErrors(bindingResult, categoryDTO, redirectAttributes);
            return "redirect:/categories/edit/" + categoryId;
        }

        categoryService.updateCategory(categoryId, categoryDTO);
        return "redirect:/categories";
    }
}
