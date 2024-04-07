FROM maven:3.9.5-sapmachine-21

WORKDIR /app
COPY mvnw pom.xml ./

COPY src ./src

RUN mvn dependency:resolve

RUN mvn install

CMD mvn spring-boot:run