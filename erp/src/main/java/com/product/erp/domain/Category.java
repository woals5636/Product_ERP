    package com.product.erp.domain;

    import jakarta.persistence.*;
    import lombok.*;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;

    import java.time.LocalDateTime;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "product_category")
    public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long categoryId;  // 내부 관리용 PK (1, 2, 3...)

        @Column(unique = true, nullable = false, length = 10)
        private String categoryCode;  // 실제 제품 분류 코드 (예: "A001", "B002")

        @Column(nullable = false, length = 255)
        private String categoryName; // 제품분류명

        @Column(nullable = false)
        private Boolean isDeleted; // 삭제여부 (false : 삭제되지 않음 활성화 / true : 삭제됨 )

        @CreationTimestamp
        private LocalDateTime createdAt; // 시스템 등록일자

        @UpdateTimestamp
        private LocalDateTime updatedAt; // 시스템 수정일자

    }
