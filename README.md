# 📚 Redo API - 책 리뷰 관리 시스템

Spring Boot를 활용한 도서 리뷰 관리 REST API 프로젝트입니다.

## ✨ 주요 기능

- 🔐 **사용자 인증**: JWT 기반 회원가입 및 로그인
- 📝 **리뷰 관리**: 도서 리뷰 작성, 조회, 수정, 삭제
- 🔍 **고급 검색**: 책 제목, 평점, 사용자별 리뷰 검색 및 필터링
- 📄 **페이지네이션**: 대용량 데이터 처리를 위한 페이지 기반 조회
- ⭐ **평점 기반 조회**: 고평점 리뷰 우선 조회

## 🛠 기술 스택

### Backend
- **Java 21**
- **Spring Boot 3.5.8**
- **Spring Security** - JWT 인증
- **Spring Data JPA** - 데이터 접근 계층
- **QueryDSL** - 동적 쿼리 생성
- **H2 Database** - 개발용 인메모리 데이터베이스

### Documentation & Testing
- **Swagger/OpenAPI 3.0** - API 문서화
- **JUnit 5** - 단위 테스트

### Build & Dependencies
- **Gradle** - 빌드 도구
- **Lombok** - 보일러플레이트 코드 감소
- **Validation** - 입력값 검증

## 📋 API 문서

### 🔐 인증 API
| 메서드 | 엔드포인트 | 설명 |
|--------|------------|------|
| POST | `/api/auth/signup` | 회원가입 |
| POST | `/api/auth/login` | 로그인 |

### 📚 리뷰 API
| 메서드 | 엔드포인트 | 설명 |
|--------|------------|------|
| POST | `/api/reviews` | 리뷰 작성 |
| GET | `/api/reviews/{id}` | 리뷰 단건 조회 |
| GET | `/api/reviews` | 전체 리뷰 조회 |
| PUT | `/api/reviews/{id}` | 리뷰 수정 |
| DELETE | `/api/reviews/{id}` | 리뷰 삭제 |

### 🔍 검색 API
| 메서드 | 엔드포인트 | 설명 |
|--------|------------|------|
| GET | `/api/reviews/search` | 종합 검색 (책 제목, 평점, 사용자) |
| GET | `/api/reviews/rating/{rating}` | 평점별 리뷰 조회 |
| GET | `/api/reviews/user/{userId}` | 사용자별 리뷰 조회 |
| GET | `/api/reviews/book` | 책 제목별 리뷰 조회 |
| GET | `/api/reviews/high-rated` | 고평점 리뷰 조회 |

## 🏗 프로젝트 구조

```
src/main/java/com/lsk/redoapi/
├── auth/                    # 인증 모듈
│   ├── domain/
│   │   ├── entity/         # User 엔티티
│   │   └── repository/     # 사용자 저장소
│   ├── presentation/       # 컨트롤러 및 DTO
│   └── service/           # 비즈니스 로직
├── review/                 # 리뷰 모듈
│   ├── domain/
│   │   ├── entity/        # Review 엔티티
│   │   └── repository/    # 리뷰 저장소 (QueryDSL 포함)
│   ├── presentation/      # 컨트롤러 및 DTO
│   └── service/          # 비즈니스 로직
└── global/               # 공통 설정
    ├── common/          # 공통 엔티티 및 응답
    ├── config/          # 설정 클래스
    ├── exception/       # 예외 처리
    └── security/        # JWT 보안 설정
```

## 🚀 실행 방법

### 요구사항
- Java 21 이상
- Gradle

### 실행 단계

1. **프로젝트 클론**
```bash
git clone <repository-url>
cd redo-api
```

2. **애플리케이션 실행**
```bash
./gradlew bootRun
```

3. **접속 정보**
- **애플리케이션**: http://localhost:8090
- **H2 콘솔**: http://localhost:8090/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (빈값)
- **Swagger UI**: http://localhost:8090/swagger-ui/index.html

### 테스트 실행
```bash
./gradlew test
```

## 💾 더미 데이터

애플리케이션 실행 시 자동으로 더미 데이터가 생성됩니다:
- **사용자**: user1, user2, user3 (비밀번호: password123)
- **리뷰**: 각 사용자당 여러 개의 도서 리뷰 데이터

## 🔧 개발 설정

### JWT 설정
- **만료 시간**: 24시간
- **시크릿 키**: application.yaml에서 설정

### 로깅 설정
- SQL 쿼리 로그 활성화
- Hibernate 파라미터 바인딩 로그 포함

---

**개발 목적**: Java 실무 연습용 프로젝트