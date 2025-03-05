# 🏭 제품 ERP 웹페이지 구현

### 📌 프로젝트 개요
본 프로젝트는 제품과 제품 분류를 관리할 수 있는 ERP 시스템입니다. 

### 🔧 기술 스택
- **Backend**: Java 17, Spring Boot, JPA
- **Frontend**: Thymeleaf, Bootstrap, Flatpickr
- **Database**: MySQL
- **Server**: Tomcat
- **IDE**: IntelliJ

---

## 📂 기능 설명

### 1️⃣ 제품 분류 마스터 관리
- **등록**: 제품 분류를 등록할 수 있으며, 입력값 검증 기능이 포함됨
- **조회**: 페이징 및 검색 기능 제공 (한 페이지당 5개 항목 표시)
- **수정**: 제품 분류 코드는 변경할 수 없으며, 필수 입력값 검증 적용

### 2️⃣ 제품 마스터 관리
- **등록**:
  - 제품 분류 중 "삭제 여부"가 `false`인 항목만 선택 가능
  - Flatpickr 라이브러리를 활용한 날짜 입력 기능
  - 파일 업로드 기능: `C:/upload` 경로에 저장 후 접근 가능
  - 제품 코드는 제품 분류 코드 + "-0001" 형식으로 자동 생성
  - 트랜잭션 관리를 위해 `@Transactional` 적용
- **조회**: 페이징 및 검색 기능 제공 (한 페이지당 5개 항목 표시)
- **수정**: 제품 분류와 동일한 로직 적용

---

## 📜 추가 자료
- 프로젝트 PPT 링크: [여기](https://www.canva.com/design/DAGgSx0OzcY/tolutWD3YXw_kd9A-1x35Q/view?utm_content=DAGgSx0OzcY&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h933f680268)
