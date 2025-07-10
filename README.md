# VYBZ LIVE SERVICE

## 개요

**VYBZ LIVE SERVICE**는 버스킹/라이브 방송 플랫폼의 실시간 스트리밍, 방송 관리, 시청자/버스커 상호작용, 구독자 전용 방송, 무한 스크롤 방송 목록, 실시간 통계(좋아요/시청자 수) 등 다양한 기능을 제공하는 Spring Boot 기반 마이크로서비스입니다.

---

## 기술 스택

- **Java 17**
- **Spring Boot 3.4.x**
- **Spring Web, WebSocket, Validation**
- **Spring Data MongoDB (동기/리액티브)**
- **Spring Cloud (Eureka, OpenFeign)**
- **Kafka (spring-kafka)**
- **Swagger (SpringDoc OpenAPI 3.x)**
- **Lombok**
- **Docker, Docker Compose**
- **FFmpeg (RTMP 송출)**
- **MongoDB, Redis**
- **GitHub Actions (CI/CD)**

---

## 프로젝트 구조 상세

```
vybz-live/
├── build.gradle                # Gradle 빌드/의존성 관리
├── Dockerfile                  # Docker 빌드/실행 스크립트
├── README.md                   # 프로젝트 설명서
├── .github/
│   └── workflows/
│       └── deploy.yml          # GitHub Actions CI/CD 배포 자동화
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── back/vybz/live_service/
│   │   │       ├── LiveServiceApplication.java   # Spring Boot 진입점
│   │   │       ├── common/        # 공통 모듈
│   │   │       │   ├── config/    # WebSocket, Swagger, Feign, Mongo 등 설정
│   │   │       │   ├── client/    # 외부 서비스 연동 (FeignClient)
│   │   │       │   ├── entity/    # 공통 응답/상태 엔티티
│   │   │       │   ├── exception/ # 예외 및 에러코드 표준화
│   │   │       │   └── util/      # WebSocket 핸들러, 커서 페이지 등 유틸
│   │   │       ├── kafka/         # Kafka 연동
│   │   │       │   ├── config/    # Kafka 설정
│   │   │       │   ├── consumer/  # Kafka 컨슈머
│   │   │       │   ├── event/     # Kafka 이벤트 객체
│   │   │       │   └── producer/  # Kafka 프로듀서
│   │   │       └── live/          # 라이브 방송 도메인
│   │   │           ├── application/
│   │   │           │   └── service/   # 서비스 계층 (비즈니스 로직, FFmpeg)
│   │   │           ├── domain/        # 도메인 모델 (LiveStream 등)
│   │   │           ├── dto/           # 데이터 전송 객체 (DTO)
│   │   │           │   ├── request/
│   │   │           │   └── response/
│   │   │           ├── infrastructure/ # DB 접근 (MongoRepository 등)
│   │   │           ├── presentation/   # REST 컨트롤러
│   │   │           └── vo/             # 값 객체 (VO)
│   │   │               ├── request/
│   │   │               └── response/
│   │   └── resources/
│   │       └── application.yml     # 환경 변수 및 서비스 설정
│   └── test/
│       └── java/                   # (테스트 코드 없음)
└── ...
```

| 경로/파일 | 설명 |
|-----------|------|
| build.gradle | Gradle 빌드 및 의존성 관리 파일 |
| Dockerfile | Docker 기반 배포/실행 스크립트 (FFmpeg 포함) |
| .github/workflows/deploy.yml | GitHub Actions 기반 CI/CD 자동화 (Docker 빌드/배포/알림) |
| src/main/java/back/vybz/live_service/LiveServiceApplication.java | Spring Boot 애플리케이션 진입점 |
| src/main/java/back/vybz/live_service/common/config/ | WebSocket, Swagger, Feign, Mongo 등 서비스 설정 |
| src/main/java/back/vybz/live_service/common/client/ | 외부 Support 서비스 연동용 FeignClient 및 Fallback |
| src/main/java/back/vybz/live_service/common/entity/ | 표준 응답/상태 엔티티 (BaseResponseEntity 등) |
| src/main/java/back/vybz/live_service/common/exception/ | 표준 예외 및 에러코드 (BaseException 등) |
| src/main/java/back/vybz/live_service/common/util/ | WebSocket 핸들러, 커서 페이지네이션 등 유틸리티 |
| src/main/java/back/vybz/live_service/kafka/ | Kafka 이벤트, 프로듀서, 컨슈머, 설정 모듈 |
| src/main/java/back/vybz/live_service/live/application/service/ | 라이브 방송 서비스 계층, FFmpeg 연동 등 |
| src/main/java/back/vybz/live_service/live/domain/ | 방송 도메인 모델 (LiveStream 등) |
| src/main/java/back/vybz/live_service/live/dto/ | API 데이터 전송 객체 (DTO, request/response) |
| src/main/java/back/vybz/live_service/live/infrastructure/ | MongoDB 등 데이터 저장소 접근 (Repository) |
| src/main/java/back/vybz/live_service/live/presentation/ | REST API 컨트롤러 (LiveStreamController 등) |
| src/main/java/back/vybz/live_service/live/vo/ | 값 객체(Value Object, request/response) |
| src/main/resources/application.yml | 환경 변수 및 서비스 설정 파일 |
| src/test/java/ | (테스트 코드 없음) |

> 각 디렉토리/파일의 실제 역할과 연관성을 명확히 파악할 수 있도록 상세하게 기술하였습니다.

---

## 빌드 및 실행 방법

### 1. 로컬 실행

```bash
# 빌드
./gradlew clean build -x test

# 실행
java -jar build/libs/vybz-live-*.jar
```

### 2. Docker 실행

```bash
docker build -t vybz-live .
docker run -p 9999:9999 vybz-live
```

- FFmpeg가 컨테이너에 자동 설치됨
- 환경 변수는 `application.yml`로 관리

### 3. CI/CD (GitHub Actions)
- dev 브랜치 push 시 Docker Hub 빌드/푸시 → EC2 배포 자동화
- 배포 후 Discord 알림

---

## 환경 변수 (application.yml)

```yaml
server:
  port: 9999
spring:
  application:
    name: live-service
  kafka:
    bootstrap-servers: <KAFKA_SERVERS>
    ...
  data:
    mongodb:
      uri: mongodb://<USER>:<PW>@<HOSTS>/<DB>?authSource=admin&replicaSet=myReplicaSet
    redis:
      host: <REDIS_HOST>
      port: <REDIS_PORT>
      password: <REDIS_PW>
eureka:
  ...
live:
  rtmp:
    url-prefix: rtmp://<RTMP_HOST>:1935/live/
```

---

## 주요 기능 및 API

### 1. 라이브 방송 관리
- 방송 시작/종료, 입장, 무한 스크롤 목록, 카테고리별 조회 등

#### 예시 엔드포인트 (Swagger UI 제공)

- **방송 시작**: `POST /api/v1/live/start`
- **방송 종료**: `POST /api/v1/live/end`
- **방송 입장**: `GET /api/v1/live/enter/{streamKey}`
- **전체 목록**: `GET /api/v1/live/all`
- **카테고리별 목록**: `GET /api/v1/live/category?categoryId=...`

#### Swagger 문서
- `/live-service/swagger-ui/index.html` (게이트웨이 경유)

#### 예시 요청/응답

```json
// 성공 응답
{
  "isSuccess": true,
  "message": "SUCCESS",
  "code": 200,
  "result": {
    ...
  }
}

// 에러 응답
{
  "isSuccess": false,
  "message": "구독자만 시청할 수 있는 라이브입니다.",
  "code": 418,
  "result": null
}
```

---

### 2. 실시간 WebSocket API

#### 엔드포인트
- **방송 송출**: `ws://<HOST>:9999/ws-live/stream?streamKey=...`
- **시청자 입장**: `ws://<HOST>:9999/ws-live/viewer?streamKey=...&viewerUuid=...`

#### 주요 메시지
- 방송 종료: `스트림이 종료되었습니다.`
- 좋아요/시청자 수 실시간 push: `{ "type": "LIKE_COUNT", "likeCount": 123 }`, `{ "type": "VIEWER_COUNT", "viewerCount": 45 }`
- 구독자 전용 방송 접근 제한: `{ "type": "ERROR", "message": "구독자만 시청할 수 있는 라이브입니다." }`

#### 방송 송출(FFmpeg)
- WebSocket으로 webm 스트림 → 서버에서 FFmpeg로 RTMP 변환/송출
- RTMP URL: `rtmp://<RTMP_HOST>:1935/live/{streamKey}`

---

### 3. Kafka 연동
- 방송 시청/좋아요 등 이벤트를 Kafka로 송수신
- `ViewCountKafkaEventProducer`, `LiveViewCountResultEventConsumer` 등 구현

---

### 4. 데이터베이스
- **MongoDB**: 방송 정보, 상태, 통계 저장
- **Redis**: 세션/캐시 등 (설정만 존재)

---

### 5. 마이크로서비스/외부 연동
- **Eureka**: 서비스 레지스트리/디스커버리
- **OpenFeign**: Support 서비스와 구독자 상태 연동
- 장애 시 Fallback 및 CustomErrorDecoder 적용

---

## 데이터 구조 예시

### LiveStream (MongoDB Document)
```json
{
  "id": "...",
  "buskerUuid": "...",
  "streamKey": "...",
  "title": "...",
  "thumbnailUrl": "...",
  "liveStreamStatus": "ON_AIR",
  "likeCount": 0,
  "viewerCount": 0,
  "categoryId": 1,
  "membership": false,
  "startTime": "2024-06-01T12:00:00Z",
  "endTime": null,
  "createdAt": "2024-06-01T12:00:00Z",
  "updatedAt": "2024-06-01T12:00:00Z"
}
```

---

## 협업/운영 안내

- **CI/CD**: dev 브랜치 push 시 자동 배포 (deploy.yml 참고)
- **Swagger**: API 문서 자동화
- **코드 컨벤션**: Lombok, Builder, 예외/응답 표준화
- **장애 대응**: Feign Fallback, CustomErrorDecoder, WebSocket 에러 메시지 등
- **환경 변수 관리**: application.yml, GitHub Secrets, Docker 환경변수 등
- **테스트**: (현재 테스트 코드 없음)

---

## 문의/기여

- 이슈/PR 등록, Discord 알림, Swagger 문서 활용
- 추가 문의: 관리자/팀장에게 연락 