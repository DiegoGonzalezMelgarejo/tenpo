# Etapa 1: Construcción (compilación con Maven)
FROM maven:3.9.9-sapmachine-21 AS builder

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el código fuente al contenedor
COPY . .

# Ejecutar Maven para compilar el proyecto y crear el archivo JAR
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (usar Amazon Corretto para ejecutar el JAR)
FROM amazoncorretto:21-alpine-jdk

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el JAR generado desde la etapa de construcción
COPY --from=builder /app/target/diego-0.0.1-SNAPSHOT.jar .

# Exponer el puerto 8080
EXPOSE 8080

# Establecer las variables de entorno necesarias para la ejecución
ENV URL_FLY="jdbc:postgresql://localhost:5432/diego"
ENV REDIS="localhost"
ENV URL_R="r2dbc:postgresql://localhost:5432/diego"

# Ejecutar la aplicación con las variables de entorno
ENTRYPOINT ["java", "-jar", "diego-0.0.1-SNAPSHOT.jar"]