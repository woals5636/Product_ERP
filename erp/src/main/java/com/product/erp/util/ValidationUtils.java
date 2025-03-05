package com.product.erp.util;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ValidationUtils {

    // 공통 검증 오류 처리 메서드
    public static void handleValidationErrors(BindingResult bindingResult,
                                              Object dto,
                                              RedirectAttributes redirectAttributes) {
        bindingResult.getFieldErrors().forEach(error ->
                redirectAttributes.addFlashAttribute(error.getField() + "Error", error.getDefaultMessage())
        );
        redirectAttributes.addFlashAttribute("dto", dto);
    }
}