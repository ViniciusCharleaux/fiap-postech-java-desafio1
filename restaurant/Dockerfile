FROM eclipse-temurin:21-jdk
WORKDIR /app

# copia qualquer .jar que exista na pasta target e renomeia pra app.jar
COPY target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]