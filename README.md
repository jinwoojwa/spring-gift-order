# spring-gift-order

<br>

## 목차

0. [0단계 - 기본 코드 준비](#step-0---기본-코드-준비)
1. [1단계 - 카카오 로그인](#step-1---카카오-로그인)

---

<br>

## Step 0 - 기본 코드 준비

---

#### 기능 요구 사항

- `상품 고도화` 코드를 옮기기

---

<br>

## Step 1 - 카카오 로그인

---

#### 기능 요구 사항

- 카카오 로그인을 통해 인가 코드를 받고, 인가 코드를 통해 토큰을 받는다
  + 카카오 계정 로그인을 통해 인가 코드를 받음
  + 인가 코드로 액세스 토큰을 요청
  + 토큰을 수신

<br>

#### 세부 구현 사항

- `/auth/kakao/login`에서 카카오 인가 요청 URL로 302 리다이렉트
  + `client_id`, `redirect_uri`, `response_type=code`를 포함한 URL을 생성
  + 사용자가 카카오 로그인, 동의 과정을 완료시 인가 코드 발급

<br>

- `/auth/kakao/callback`에서 인가 코드를 수신
  + 쿼리 파라미터로 전달된 code 값을 추출

<br>

- 인가 코드로 카카오 토큰 API에 POST 요청
  + `grant_type=authorization_code`, `client_id`, `redirect_uri`, `code` 포함

<br>

- 카카오로부터 액세스 토큰, 리프레시 토큰 등의 정보를 포함한 JSON 응답 수신
  + 응답을 `KakaoTokenResponseDto` 객체로 매핑
  + `ResponseEntity`를 통해 응답 상태 코드와 함께 반환

<br>

- Spring Boot의 `RestClient`를 사용해 외부 API 통신 구현

<br>

- 인가 요청과 토큰 요청에 필요한 설정 값을 `application.properties`에서 관리


