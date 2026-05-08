#
# Autor: Antonio Marcos de Souza Santos
# Cargo: Developer Full Stack
# Data: 07/05/2026
#
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
