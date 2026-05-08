#!/bin/bash

# =====================================
# Autor: Antonio Marcos de Souza Santos
# Cargo: Developer Full Stack
# Data: 07/05/2026
# =====================================

TAG=$1

if [ -z "$TAG" ]; then
    echo "ERRO: informe a TAG"
    echo "Exemplo:"
    echo "./deploy-hml.sh 202605072059"
    exit 1
fi

IMAGE_NAME="api-java-hml"
SERVER="root@134.209.164.87"

echo "====================================="
echo "DEPLOY BACKEND JAVA"
echo "TAG: $TAG"
echo "====================================="

# EXPORTA IMAGEM
echo "EXPORTANDO IMAGEM..."

docker save -o ${IMAGE_NAME}.tar ${IMAGE_NAME}:$TAG

if [ $? -ne 0 ]; then
    echo "ERRO AO EXPORTAR IMAGEM"
    exit 1
fi

# ENVIA PARA SERVIDOR
echo "ENVIANDO IMAGEM PARA SERVIDOR..."

scp ${IMAGE_NAME}.tar ${SERVER}:/opt/docker/

if [ $? -ne 0 ]; then
    echo "ERRO SCP"
    exit 1
fi

# EXECUTA DEPLOY REMOTO
echo "EXECUTANDO DEPLOY REMOTO..."

ssh ${SERVER} << EOF

docker load -i /opt/docker/${IMAGE_NAME}.tar

docker stop ${IMAGE_NAME} || true

docker rm ${IMAGE_NAME} || true

docker run -d \
--name ${IMAGE_NAME} \
-p 8080:8080 \
--restart always \
${IMAGE_NAME}:$TAG

EOF

echo "====================================="
echo "DEPLOY FINALIZADO"
echo "====================================="