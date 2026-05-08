#!/bin/bash

TAG=$1

if [ -z "$TAG" ]; then
    echo "ERRO: informe a TAG"
    echo "Exemplo:"
    echo "./build-hml.sh 202605072059"
    exit 1
fi

echo "====================================="
echo "BUILD BACKEND JAVA"
echo "TAG: $TAG"
echo "====================================="

# BUILD MAVEN
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "ERRO NO MAVEN BUILD"
    exit 1
fi

# BUILD DOCKER
docker build -t api-java-hml:$TAG .

if [ $? -ne 0 ]; then
    echo "ERRO NO DOCKER BUILD"
    exit 1
fi

# TAG latest
docker tag api-java-hml:$TAG api-java-hml:latest

echo "====================================="
echo "BUILD FINALIZADO"
echo "IMAGEM:"
echo "api-java-hml:$TAG"
echo "====================================="