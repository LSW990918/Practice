# <strong>Practice</strong>


## 🎁개요


- **개발 기간** : 24.01.30 ~ 24.02.13 (2주)
- **개발 환경** : Kotlin, Spring Boot, Supabase, PostgreSql
- **이름** : Practice
- **설명** : 본격적인 프로젝트들 진행전에 혼자 연습해보기


## **📚기술스택**

### **Backend**

- Spring Boot: 3.2.2

### **DB**

- SupaBase(postgreSQL): [https://supabase.com/dashboard/projects]

## 🎈 주요기능

### 게시글 CRUD
- 게시글 CRUD
  - 인증/인가
    - 로그인이 되어 있어야 게시글 작성 가능
    - 본인의 게시글만 수정/삭제 가능
### 댓글 CRUD
- 댓글 CRUD
  - 인증/인가
    - 로그인이 되어 있어야 댓글 작성 가능
    - 본인의 댓글만 수정/삭제 가능
### 회원가입/로그인
- 회원가입/로그인
  - JWT Token 기반 인증/인가
    - Spring Security 활용
- 유저 CRUD


### 후기
-앞으로의 프로젝트에서 유용하게 써볼 기능들을 미리 공부해두는 주차였다.
이번 연습으로 동적쿼리(QueryDsl), JpaAuditing을 이용한 생성과 수정시간 자동기록, SignUp 단계에서 UserRole을 Enum으로 받는 방법 등 여러가지를 배웠다.
