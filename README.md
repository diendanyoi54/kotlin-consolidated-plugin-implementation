# kotlin-consolidated-plugin-implementation

## Description
Example Spring Boot microservice that implements the kotlin-consolidated-plugin

## Requirements
For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Running the application locally
There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.company.kotlinconsolidatedpluginimplementation.KotlinConsolidatedPluginImplementationApplication.kt` class from your IDE.

Alternatively you can use the [Spring Boot Gradle plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-gradle-plugin.html) like so:

```shell
./gradlew bootRun
```

To successfully run this application you will need the following environment variables:
- **SPRING_APPLICATION_NAME** *_(This needs to match the name of the yaml file in the repository your cloud server points to)_*
- **SPRING_CLOUD_BOOTSTRAP_LOCATION** *_(This should map to the file where your configuration settings are for cloud clients)_*
- **SPRING_CLOUD_CONFIG_LABEL** *_(Optional - Branch you want to use for the yaml file located in repository your cloud server points to)_*
- **SERVER_PORT** (Optional)

## Other tasks
See [kotlin-consolidated-plugin](https://github.com/diendanyoi54/kotlin-consolidated-plugin)