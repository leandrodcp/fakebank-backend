####
# Before building the container image run:
#
# ./mvnw clean package
#
# Then, build the image with:
# sudo in Linux
# docker build -t fakebank-backend .
#
# Then run the container using:
# sudo in Linux
# docker run -i --rm -p 8080:8080 fakebank-backend
#
###
FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]