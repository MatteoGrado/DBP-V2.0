FROM eclipse-temurin:25-jdk AS build

WORKDIR /build
COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY src src
RUN ./mvnw -q -DskipTests package

FROM eclipse-temurin:25-jre

COPY --from=build /build/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
