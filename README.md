# VYBZ Live Service

VYBZ 플랫폼의 라이브 스트리밍 서비스를 담당하는 마이크로서비스입니다.

## 🚀 프로젝트 개요

이 서비스는 버스커(스트리머)의 라이브 방송 시작/종료, 시청자 입장, 실시간 시청자 수 및 좋아요 수 관리, 카테고리별 라이브 방송 목록 조회 등의 기능을 제공합니다.

## 🛠 기술 스택

### Backend

![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-59666C?style=for-the-badge)
![Spring WebSocket](https://img.shields.io/badge/Spring_WebSocket-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

### Infra

![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### 협업

![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)


### 패키지 구조
```
src/main/java/back/vybz/live_service/
├── common/                    # 공통 모듈
│   ├── client/               # 외부 서비스 클라이언트
│   ├── config/               # 설정 클래스들
│   ├── entity/               # 공통 엔티티
│   ├── exception/            # 예외 처리
│   └── util/                 # 유틸리티 클래스
├── kafka/                    # Kafka 이벤트 처리
│   ├── config/               # Kafka 설정
│   ├── consumer/             # 이벤트 컨슈머
│   ├── event/                # 이벤트 모델
│   └── producer/             # 이벤트 프로듀서
└── live/                     # 라이브 스트리밍 도메인
    ├── application/          # 애플리케이션 서비스
    ├── domain/               # 도메인 모델
    ├── dto/                  # 데이터 전송 객체
    ├── infrastructure/       # 인프라스트럭처
    ├── presentation/         # 컨트롤러
    └── vo/                   # 뷰 객체
```

## 📋 주요 기능

### 1. 라이브 스트리밍 관리
- **라이브 시작**: 버스커가 새로운 라이브 방송을 시작
- **라이브 종료**: 버스커가 라이브 방송을 종료
- **스트림 키 생성**: 고유한 스트리밍 키 자동 생성

### 2. 시청자 관리
- **라이브 입장**: 시청자가 라이브 방송에 입장
- **구독자 전용 라이브**: 구독자만 접근 가능한 프리미엄 라이브
- **실시간 시청자 수**: WebSocket을 통한 실시간 시청자 수 업데이트

### 3. 라이브 방송 목록
- **전체 라이브 목록**: 무한 스크롤로 모든 라이브 방송 조회
- **카테고리별 목록**: 특정 카테고리의 라이브 방송만 조회
- **실시간 좋아요 수**: Kafka를 통한 실시간 좋아요 수 업데이트

### 4. 실시간 통신
- **WebSocket**: 시청자와 스트리머 간 실시간 양방향 통신
- **이벤트 스트리밍**: Kafka를 통한 시청자 수, 좋아요 수 이벤트 처리

## 🔌 API 엔드포인트

### 라이브 스트리밍 관리
```
POST /api/v1/live/start          # 라이브 시작
POST /api/v1/live/end            # 라이브 종료
GET  /api/v1/live/enter/{streamKey}  # 라이브 입장
```

### 라이브 목록 조회
```
GET /api/v1/live/all             # 전체 라이브 목록 (무한스크롤)
GET /api/v1/live/category        # 카테고리별 라이브 목록 (무한스크롤)
```

### WebSocket 엔드포인트
```
/ws-live/stream                  # 스트리머용 WebSocket
/ws-live/viewer                  # 시청자용 WebSocket
```

## 🔄 이벤트 스트리밍

### Kafka 이벤트
- **ViewCountKafkaEvent**: 시청자 입장 시 발생
- **LiveViewCountResultEvent**: 시청자 수 업데이트 결과
- **LiveLikeCountResultEvent**: 좋아요 수 업데이트 결과

## 🛠️ 설정

### 필수 환경 변수
```bash
# MongoDB 연결
spring.data.mongodb.uri=mongodb://localhost:27017/vybz_live

# Kafka 설정
spring.kafka.bootstrap-servers=localhost:9092

# Eureka 서버
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Support 서비스 URL (Feign Client)
support.service.url=http://support-service:8080
```

## 🐳 Docker 실행

```bash
# 이미지 빌드
docker build -t vybz-live-service .

# 컨테이너 실행
docker run -p 8080:8080 vybz-live-service
```

## 📦 의존성

### 주요 의존성
- **Spring Boot Starter Web**: REST API 지원
- **Spring Boot Starter Data MongoDB**: MongoDB 연동
- **Spring Boot Starter WebSocket**: 실시간 통신
- **Spring Cloud Netflix Eureka Client**: 서비스 디스커버리
- **Spring Cloud OpenFeign**: 서비스 간 통신
- **Spring Kafka**: 이벤트 스트리밍
- **SpringDoc OpenAPI**: API 문서화

## 🔧 개발 환경 설정

### 요구사항
- Java 17
- Gradle 8.4+
- MongoDB
- Apache Kafka
- FFmpeg

### 로컬 실행
```bash
# 프로젝트 클론
git clone <repository-url>
cd vybz-live

# 애플리케이션 실행
./gradlew bootRun
```

## 📚 API 문서

애플리케이션 실행 후 다음 URL에서 Swagger UI를 통해 API 문서를 확인할 수 있습니다:
```
http://localhost:8080/swagger-ui.html
```

## 🤝 연동 서비스

- **Support Service**: 구독자 검증 및 기타 지원 기능
- **Eureka Server**: 서비스 디스커버리
- **Kafka**: 이벤트 스트리밍
- **MongoDB**: 데이터 저장소

## 📝 라이센스

이 프로젝트는 VYBZ 플랫폼의 일부입니다.
