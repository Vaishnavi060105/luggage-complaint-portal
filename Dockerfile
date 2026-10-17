# Build the WAR first: mvn clean package -DskipTests
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/luggage-portal.war app.war
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.war", "--server.port=8080"]
