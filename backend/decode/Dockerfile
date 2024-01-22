# 베이스 이미지로 OpenJDK 17을 사용
FROM openjdk:17-slim

# 애플리케이션을 실행할 디렉토리 설정
WORKDIR /app

# 빌드 인자로 받은 JAR 파일을 컨테이너에 복사
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# 컨테이너가 시작될 때 실행될 명령어 설정 (Spring Boot 애플리케이션 실행)
CMD ["java", "-jar", "app.jar"]
