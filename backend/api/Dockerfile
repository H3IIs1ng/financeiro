FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app


RUN apt-get update && apt-get install -y ca-certificates && rm -rf /var/lib/apt/lists/*

COPY backend/api/ .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

CMD java -jar target/api-0.0.1-SNAPSHOT.jar