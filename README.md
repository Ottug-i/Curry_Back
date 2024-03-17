# 카레: 카메라로 찍고 레시피 추천받자! 🍛
현대인의 건강한 식습관 형성을 위한 식재료 인식 및 맞춤 레시피 추천 어플리케이션<br/><br/> 

## 🛠 백엔드 기술 스택
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">

- **Backend** : JAVA 11, Spring Boot (Gradle), Spring Data JPA, Spring Security, JWT, Spring Batch<br/>
- **DevOps** : GCP Compute Engine (Ubuntu), MySQL, Redis, Docker, GitHub Actions CI/CD<br/>
- **API Documentation** : Swagger
<br>

## ⚙ 스키마 설계
- 기본적인 테이블의 경우 MySQL에 스키마를 작성
- 캐시를 사용하기 위한 경우에는 Redis를 사용<br><br>
![스키마 설계](https://github.com/Ottug-i/Curry_Back/assets/87821678/b894bd62-c110-4b05-b9b4-8353d9e4d56e)
<br><br>

## 📍 API 설계
- REST API 설계
- Swagger API 문서 제공<br><br>
![API 설계](https://github.com/Ottug-i/Curry_Back/assets/87821678/605bb587-b127-442b-b3ff-002b9ba08cac)
<br><br>

## 📍 주요 기능
#### 소셜 로그인 & 초기 평점 입력
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/30167b1e-8558-4043-b6f9-194c48c4229c">
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/e116a761-53d6-43ec-aae1-bb059baf9cac">

#### 추천 레시피 페이지
- 메인 화면에 추천 레시피 10개 제공
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/338a358d-f0a5-48a2-a166-e71eaa7d7e7d">
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/a71a9c42-332e-497c-bea1-f7cf7c6dc33b"><br/>

#### 재료 촬영 페이지
- 객체 인식 모델로 식재료 인식
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/f4c82932-6abf-4c35-aa74-7152b82dd8cf">
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/8444dd02-c1b3-4079-9f69-f295d478814b">
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/5bb4856f-7468-4b68-a99d-a4d96378979c">

#### 레시피 조회 페이지 
- 재료 정보/ 조리 순서 제공<br/>
- 챗봇, 타이머<br/>
- 평점 및 북마크 추가, 삭제
<div><img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/c059a4c7-cf1b-46ec-bd60-a07bf1e1bd0f">
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/95db8489-7493-4900-854e-b18c329d2f30">
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/5983a081-a910-4284-aa77-eeb23e7eb380">
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/5d3c253a-656e-4473-a26d-099f24b03c53"></div>

#### 레시피 검색 페이지
- 인기 검색어<br/>
- 옵션 검색 (요리 시간, 난이도, 구성)<br/>
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/45de16af-f510-4cdf-83fd-6a16563f040c">
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/326333fc-b0d1-47e8-9b4a-608dfa901a89"><br/>

#### 인증샷 찍기 페이지
- 완성한 요리를 3D 캐릭터와 함께 인증샷 촬영
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/6eebe7c4-7eaa-4e2e-9599-d606081e3fb3">
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/f4937e13-61d9-49e3-ba2e-39c349bd35c6">
<img width="350" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/0917e63a-cb1c-42ea-90a9-6c5a9f4a4436">

#### 북마크 페이지 
- 북마크한 레시피와 비슷한 레시피 5개 추천
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/5023941a-699b-4ecc-badd-da075b8d9fa2">
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/384fac63-e2cb-41d5-a154-a0023aaf1911">

#### 마이페이지 
- 최근 본 레시피
- 로그아웃/ 탈퇴하기
<img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/04a06a10-3f07-400a-8142-f484f61f2bb9">

#### 부가 기능
<table>
   <tr>
      <td>
         스플래시 화면
      </td>
      <td>
         알람
      </td>
   </tr>
   <tr>
      <td>
         <img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/8729a1d7-23d3-43bc-9c52-86c2de135f20">
      </td>
      <td>
         <img width="235" alt="image" src="https://github.com/Ottug-i/Curry_Front/assets/76986589/a1a8945c-903b-4946-a833-9b8950789d36">
      </td>
   </tr>
</table>
<br><br>

## 👩🏻‍💻 백엔드 개발자
| 김가경 |
| :-: |
|[@GaGa-Kim](https://github.com/GaGa-Kim) |
|<img src="https://github.com/GaGa-Kim.png" style="width:150px; height:150px;">|
