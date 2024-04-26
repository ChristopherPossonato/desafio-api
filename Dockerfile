# Use a imagem base do OpenJDK
FROM openjdk:17-jdk
LABEL maintainer="christopherpossonato@gmail.com"

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o JAR da sua aplicação para o contêiner
COPY target/desafio.jar /app/desafio.jar

EXPOSE 8081
# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "/app/desafio.jar"]
