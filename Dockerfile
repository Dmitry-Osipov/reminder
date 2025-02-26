FROM openjdk:23-jdk-slim
WORKDIR /reminder
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B
COPY . .
RUN ./mvnw clean package -DskipTests
CMD ["java", "-jar", "target/reminder-0.0.1-SNAPSHOT.jar"]
