
# 통합 관리 시스템

이 프로젝트는 Spring Boot와 Thymeleaf를 사용하여 구축된 통합 관리 시스템으로, 다양한 엔티티(상품, 고객, 주문 등)를 관리할 수 있습니다.

## 주요 기능

- **상품 관리**: 상품 추가, 수정, 조회, 삭제
- **고객 관리**: 고객 정보 추가, 수정, 조회, 삭제
- **주문 관리**: 주문 생성, 수정, 조회, 삭제

## 기술 스택

- **백엔드**: Spring Boot, Spring Data JPA
- **프론트엔드**: Thymeleaf, HTML, CSS
- **데이터베이스**: MySQL
- **빌드 도구**: Maven

## 설치 방법

1. 리포지토리를 클론합니다:

    ```sh
    git clone https://github.com/ohjaoh/Spring-Practice.git
    cd Spring-Practice
    ```

2. 데이터베이스를 설정합니다:

    - MySQL이 설치되고 실행 중인지 확인합니다.
    - `testdb`라는 데이터베이스를 생성합니다.
    - 데이터베이스 자격 증명을 `application.properties` 파일에 업데이트합니다.

3. 프로젝트를 빌드합니다:

    ```sh
    mvn clean install
    ```

4. 애플리케이션을 실행합니다:

    ```sh
    mvn spring-boot:run
    ```

## 사용 방법

1. 웹 브라우저를 열고 `http://localhost:8012`로 이동합니다.
2. 네비게이션 버튼을 사용하여 상품, 고객, 주문을 추가, 조회, 수정 또는 삭제할 수 있습니다.

## 디자인

이 애플리케이션은 현대적이고 매력적인 사용자 인터페이스를 만들기 위해 글래스모피즘과 뉴모피즘 디자인 원칙을 사용합니다.

### 주요 페이지

**상품 추가 페이지**  
![상품 추가 페이지](images/product_add.png)

**고객 추가 페이지**  
![고객 추가 페이지](images/customer_add.png)

**주문 추가 페이지**  
![주문 추가 페이지](images/order_add.png)

## 주의 사항

- HTML 파일을 수정할 때는 항상 최신의 코드를 반영하고 브라우저 캐시를 무시하도록 설정해야 합니다.
- `application.properties` 파일을 적절히 설정하고 필요 시 `persistence.xml` 파일로 데이터베이스 설정을 이동할 수 있습니다.
