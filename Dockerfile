FROM openjdk:11
EXPOSE 8585
ADD build/libs/employee-management-system-0.0.1-SNAPSHOT.jar Employee-server.jar
ENTRYPOINT ["java","-jar","/Employee-server.jar"]