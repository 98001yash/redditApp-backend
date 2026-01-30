# ----------- Build Stage -----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy parent pom
COPY pom.xml .

# Copy all modules (because parent pom needs them)
COPY reddit-common ./reddit-common
COPY notification-service ./notification-service
COPY post-service ./post-service
COPY feed-service ./feed-service
COPY auth-service ./auth-service
COPY user-service ./user-service
COPY discovery-server ./discovery-server
COPY api-gateway ./api-gateway
COPY vote-service ./vote-service
COPY karma-service ./karma-service
COPY comment-service ./comment-service

# Build only notification-service + deps
RUN mvn clean package -pl notification-service -am -DskipTests


# ----------- Run Stage -----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/notification-service/target/*.jar app.jar

EXPOSE 8097

ENTRYPOINT ["java", "-jar", "app.jar"]
