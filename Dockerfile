# Build stage with Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage with JRE
FROM eclipse-temurin:21-jre
WORKDIR /app
# FIX: Use EXACT JAR name from your pom.xml
COPY --from=build /app/target/open-ai-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=10000
EXPOSE 10000
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --server.port=$PORT"]
