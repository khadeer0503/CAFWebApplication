# CAFWebApplication



[comment]: <> (docker )

[comment]: <> (1. first run the project once it's good to go then create a "Dockerfile" in the root directory of the project.)

[comment]: <> (Dockerfile)

[comment]: <> (FROM openjdk)

[comment]: <> (LABEL maintainer = "Mohammed Khadeeruddin")

[comment]: <> (##WORKDIR /usr/src/)

[comment]: <> (##COPY . /usr/src/)

[comment]: <> (##CMD ["java", "Employee_management-0.0.1-SNAPSHOT.jar"])

[comment]: <> (#)

[comment]: <> (ADD target/CAFWebApplication-0.0.1-SNAPSHOT.jar docker-image-caf.jar)

[comment]: <> (ENTRYPOINT ["java", "-jar", "docker-image-caf.jar"])

[comment]: <> (2.go to Maven >>  select the project >> Lifecycle >> maven package, the n the project will start to build.)

[comment]: <> (3.docker build process:)

[comment]: <> ([docker build -t image name:tag . current location&#40;.&#41;   ])

[comment]: <> (    docker build -t docker-image-caf:latest .)

[comment]: <> (By this a docker image will be created. we can check it through CMD and the command is "docker images" )

[comment]: <> (4. To check the current running process in CMD is "docker ps")

[comment]: <> (5. docker run)

[comment]: <> (docker run -p 8081 docker-image-caf)

[comment]: <> (6. But we have not started the mysql database ,so we will get the error here.)

[comment]: <> (   We don't want to write all the container details so we can use "DOCKER COMPOSE.yml file" in the current directory.)

    docker-compose.yml

[comment]: <> (version: '3')

[comment]: <> (services:)

[comment]: <> (    dbservice:)

[comment]: <> (        image: mysql)

[comment]: <> (        environment:)

[comment]: <> (            -MYSQL_ROOT_PASSWORD=root)

[comment]: <> (        volumes:)

[comment]: <> (            -./data:/var/lib/mysql)

[comment]: <> (        ports:)

[comment]: <> (            -3306:3306)
                        networks:
                            - cafnetwork


[comment]: <> (    appservice:)

[comment]: <> (        build: ./CAFWebApplication-main  )

[comment]: <> (        depends_on:)

[comment]: <> (            -dbservice)

[comment]: <> (        environment:)

[comment]: <> (            -spring.datasource.url=jdbc:mysql://dbservice:3306/user_management?createDatabaseIfNotExist=true)

[comment]: <> (            -spring.datasource.username=root)

[comment]: <> (            -spring.datasource.password=)

[comment]: <> (            -spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect)

[comment]: <> (            -spring.jpa.hibernate.ddl-auto=update)

[comment]: <> (        ports:)

[comment]: <> (            -8081:8081)
                        networks:
                          - cafnetwork

[comment]: <> (networks:)

[comment]: <> (-cafnetwrok)


[comment]: <> (docker-compose up)