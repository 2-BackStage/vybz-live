# 1. Gradle을 사용하여 애플리케이션 빌드 (이 부분은 그대로 둡니다)
FROM gradle:8.4-jdk17 AS builder
COPY . /app
WORKDIR /app
RUN gradle clean build -x test

# 2. 실제 실행될 경량 이미지 생성 (이 부분을 수정합니다)
FROM openjdk:17-jdk-slim
WORKDIR /app

# --- 이 부분을 추가합니다 ---
# 루트 권한으로 apt-get 업데이트 및 ffmpeg 설치
# --no-install-recommends 옵션으로 불필요한 패키지는 제외하여 이미지를 가볍게 유지합니다.
USER root
RUN apt-get update && apt-get install -y --no-install-recommends ffmpeg
# ------------------------

# 빌드된 .jar 파일만 복사 (기존 내용)
COPY --from=builder /app/build/libs/*-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]