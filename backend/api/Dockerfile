# Usa uma imagem do Java 21
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
# Copia o código e compila
COPY . .
RUN ./mvnw clean package -DskipTests
# Define o comando de inicialização
CMD java -jar target/api-0.0.1-SNAPSHOT.jar