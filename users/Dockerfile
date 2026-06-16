# COMPILACIÓN 
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos primero solo el archivo pom.xml para descargar las dependencias.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código del microservicio
COPY src ./src

# Compilamos el proyecto y generamos el archivo ejecutable (.jar)
# Omitimos las pruebas automáticas (-DskipTests) para acelerar el proceso de construcción
RUN mvn clean package -DskipTests

# PRODUCCIÓN 
FROM eclipse-temurin:17-jre-alpine

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el archivo .jar generado en la compilacion y lo renombramos a app.jar
COPY --from=build /app/target/*.jar app.jar

# Ingresamos el puerto que vamos a exponer
EXPOSE 8083

# ENTRYPOINT define el comando principal y el ciclo de vida del contenedor.
# Al ejecutarse este contenedor, arrancará inmediatamente nuestra aplicación Java.
# Si el proceso de Java muere, el contenedor se detendrá automáticamente.
ENTRYPOINT ["java", "-jar", "app.jar"]
