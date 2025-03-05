package com.product.erp.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;  // 내부 관리용 PK

    @Column(unique = true, nullable = false)
    private String productCode;  // 실제 제품 코드 (예: "A001-0001", "A001-0002")

    @Column(nullable = false, length = 20)
    private String productName;  // 제품명

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private Category category;  // 제품이 속한 분류

    @Column(nullable = false)
    private LocalDate productionDate; // 제품생산일자

    @Column(nullable = false)
    private Long unitPrice; // 제품단가

    @Column(nullable = false)
    private Boolean isActive; // 운영여부 (true / false)

    private String productionAddress; // 생산지주소

    @Column(columnDefinition = "TEXT")
    private String productDescription; // 제품설명

    private String productImageUrl; // 제품이미지 경로

    @CreationTimestamp
    private LocalDateTime createdAt; // 시스템 등록일자

    @UpdateTimestamp
    private LocalDateTime updatedAt; // 시스템 수정일자

}
