# Use uma imagem base do Java
FROM openjdk:17-jdk-alpine

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /clinixClinica

# Copie o arquivo JAR da aplicação para o contêiner
COPY target/clinixClinica.jar clinixClinica.jar

# Exponha a porta que a aplicação Spring Boot vai usar
EXPOSE 8081

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "clinixClinica.jar"]