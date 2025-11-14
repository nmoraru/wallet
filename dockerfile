FROM eclipse-temurin:17-jre-jammy

RUN groupadd -r spring && useradd -r -g spring spring
USER spring

WORKDIR /app

COPY target/*.jar app.jar

# Параметры JVM для оптимизации
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]