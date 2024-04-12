FROM registry.access.redhat.com/ubi8/openjdk-17:1.14

COPY --chown=185 target/*.jar fetcher-service.jar

USER 185

ENTRYPOINT ["java","-jar","fetcher-service.jar"]
