# 빌려방
>`부동산`부터 `대출`까지 개인 맞춤형 올인원 서비스

## 🔗 Link
> https://github.com/PDA-BillyBang

## 📝 간단한 설명
1. 다양한 앱에 포진된 `매물`들과 `대출상품`을 모아
2. 원하는 조건으로 매물을 `검색`할 수 있고
3. 내 재정상황에 맞는 대출상품을 `추천`해주는 웹 어플리케이션

## 🙋‍♂️ 담당한 기능
- 대출 서비스와 관련된 데이터 수집 및 api 개발

## 🗂️ 대출 서비스 Schema
<img src="https://github.com/user-attachments/assets/52bcac50-cfa8-4131-9956-e11c377d0c15" width="600" height="auto">

## 💾 대출 서비스 관련 데이터 수집

### 대출 상품 데이터
> [대출 상품 데이터 수집 과정](https://docs.google.com/spreadsheets/d/1XE87tyQRW_2EUAeJGgaObsAQ7MQwuDJp/edit?gid=1151371156#gid=1151371156)
1. 대출 상품을 모아서 통일된 형식으로 제공해 주는 API 찾을 수 없었음
2. 7~8 곳의 (저축)은행들의 대출상품 소개 페이지를 참조하여 데이터를 직접 수집함
3. 대략 80여 개의 대출 상품을 수집함

### 대출 상품 제공 업체 데이터
1. Dart API 활용
2. Dart API로 제공해 주지 않은 데이터는 Catch라는 사이트에서 크롤링

## 📑 대출 서비스 API 명세서
> [빌려방 대출 서비스 API 명세서](https://kkh0331.notion.site/API-3e29120e28ae42c08fcc88661f98e687)

## 😡 Trouble Issues

### Dart API 활용
> [BillyBang 프로젝트 Dart API 활용...](https://velog.io/@rlgus9301/BillyBang-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-Dart-API-%ED%99%9C%EC%9A%A9)
1. 몇몇 은행들에서 Dart API로 원하는 지표를 얻을 수 없었음 -> Catch라는 사이트에서 크롤링
2. 수집된 지표들로 빌려방 서비스만의 점수화 기준을 마련하고자 함 -> `표준화`와 `연립 방정식`을 이용

### 대출 Schema 설계
> [BillyBang 프로젝트 DB Schema 고민...](https://velog.io/@rlgus9301/BillyBang-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-DB-Schema-%EA%B3%A0%EB%AF%BC)
1. [대출 상품 데이터 수집 과정](https://docs.google.com/spreadsheets/d/1XE87tyQRW_2EUAeJGgaObsAQ7MQwuDJp/edit?gid=1151371156#gid=1151371156)에서 알 수 있듯이 대출 Schema가 여러번 변경됨
2. 맨 처음에 설계했던 것과 다르게... 데이터를 수집하면서 Schema가 변하기도 했고 코딩을 하는 과정에서 변하기도 했음
