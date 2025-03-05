package com.product.erp.service;

import com.product.erp.domain.Product;
import com.product.erp.domain.Category;
import com.product.erp.dto.ProductDTO;
import com.product.erp.repository.CategoryRepository;
import com.product.erp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getSearchProducts(String keyword, Pageable pageable) {
        return productRepository.findByProductNameContaining(keyword, pageable);
    }

    @Override
    public String generateProductCode(Category productCategory) {
        String lastProductCode = productRepository.findLastProductCodeByCategory(productCategory.getCategoryCode());
        int newSerialNumber = 1;

        if (lastProductCode != null) {
            String lastSerial = lastProductCode.substring(productCategory.getCategoryCode().length() + 1);
            newSerialNumber = Integer.parseInt(lastSerial) + 1;
        }

        return productCategory.getCategoryCode() + "-" + String.format("%04d", newSerialNumber);
    }

    @Override
    @Transactional
    public void createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductCode(generateProductCode(productDTO.getCategory())); // 상품 코드 생성
        product.setProductName(productDTO.getProductName());
        product.setCategory(productDTO.getCategory());
        product.setProductionDate(productDTO.getProductionDate());
        product.setUnitPrice(productDTO.getUnitPrice());
        product.setIsActive(productDTO.getIsActive());
        product.setProductionAddress(productDTO.getProductionAddress());
        product.setProductDescription(productDTO.getProductDescription());

        // 이미지 파일 업로드 후 이미지 URL 설정
        try {
            String imageUrl = fileUpload(productDTO.getProductImage());
            product.setProductImageUrl(imageUrl);
        } catch (IOException e) {
            log.error("이미지 업로드 중 오류 발생: ", e);
            throw new IllegalArgumentException("이미지 업로드에 실패했습니다.");
        }

        // 엔티티 저장
        productRepository.save(product);
    }

    @Override
    public String fileUpload(MultipartFile productImage) throws IOException {
        if (productImage != null && !productImage.isEmpty()) {
            return saveFile(productImage);
        }
        return null;
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String uploadFolder = "C:/upload"; // 로컬 저장 경로
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + "_" + originalFilename;
        File saveFile = new File(uploadFolder, filename);

        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }

        file.transferTo(saveFile);

        return "/images/" + filename;
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("제품이 존재하지 않습니다."));
    }

    @Override
    @Transactional
    public void modifyProduct(Long productId, ProductDTO productDTO) {
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제품입니다."));

        savedProduct.setProductName(productDTO.getProductName());
        savedProduct.setCategory(productDTO.getCategory());
        savedProduct.setProductionDate(productDTO.getProductionDate());
        savedProduct.setUnitPrice(productDTO.getUnitPrice());
        savedProduct.setIsActive(productDTO.getIsActive());
        savedProduct.setProductionAddress(productDTO.getProductionAddress());
        savedProduct.setProductDescription(productDTO.getProductDescription());

        // 이미지 업데이트가 있을 경우만 처리
        if (productDTO.getProductImage() != null && !productDTO.getProductImage().isEmpty()) {
            try {
                String imageUrl = fileUpload(productDTO.getProductImage());
                savedProduct.setProductImageUrl(imageUrl);
            } catch (IOException e) {
                log.error("이미지 업로드 중 오류 발생: ", e);
                throw new IllegalArgumentException("이미지 업로드에 실패했습니다.");
            }
        }

        productRepository.save(savedProduct);
    }
}
