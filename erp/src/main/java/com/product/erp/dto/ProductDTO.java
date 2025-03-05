package com.product.erp.dto;

import com.product.erp.domain.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long productId;  // 내부 관리용 코드

    private String productCode; // 실제 제품 코드 (예: "A001-0001", "A001-0002")

    @NotBlank(message = "{product.name.required}")
    private String productName;  // 제품명

    @NotNull(message = "{product.category.required}")
    private Category category;   // 제품분류 코드

    @NotNull(message = "{product.productionDate.required}")
    private LocalDate productionDate; // 제품 생산일자

    @NotNull(message = "{product.unitPrice.required}")
    @Min(value = 100, message = "{product.unitPrice.min}")
    @Max(value = 100000000, message = "{product.unitPrice.max}")
    private Long unitPrice;  // 제품 단가

    private Boolean isActive;  // 운영 여부

    private String productionAddress; // 생산지 주소
    private String productDescription; // 제품 설명

    private MultipartFile productImage;  // MultipartFile로 HTTP 요청에서 업로드된 이미지 처리
}
