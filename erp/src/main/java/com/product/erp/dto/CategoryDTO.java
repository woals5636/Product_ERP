package com.product.erp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long categoryId;  // 내부 관리용 PK (1, 2, 3...)

    private String categoryCode;  // 실제 제품 분류 코드 (예: "A001", "B002")

    @NotBlank(message = "{category.name.required}")
    private String categoryName; // 제품 분류 명

    private Boolean isDeleted = false; // 삭제여부 (기본값 'false') (false : 삭제되지 않음 활성화 / true : 삭제됨)
}
