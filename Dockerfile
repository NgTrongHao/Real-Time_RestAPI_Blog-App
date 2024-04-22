FROM eclipse-temurin:22

LABEL mentainer="ngtronghao02@gmail.com"

WORKDIR /app

COPY target/Real-Time_RestAPI_Blog-App-0.0.1-SNAPSHOT.jar /app/springboot-blog-app-docker.jar

ENTRYPOINT ["java", "-jar", "springboot-blog-app-docker.jar"]