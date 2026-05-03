#!/bin/bash

echo "=== Testando API de Tipos de Ocorrências ==="
echo ""

echo "1️⃣ Testando GET /api/tipos-ocorrencias"
curl -X GET http://localhost:8080/api/tipos-ocorrencias \
  -H "Content-Type: application/json" \
  -v

echo ""
echo ""

echo "2️⃣ Testando GET /api/tipos-ocorrencias/ativos"
curl -X GET http://localhost:8080/api/tipos-ocorrencias/ativos \
  -H "Content-Type: application/json" \
  -v

echo ""
echo ""

echo "3️⃣ Testando GET /api/tipos-ocorrencias/1"
curl -X GET http://localhost:8080/api/tipos-ocorrencias/1 \
  -H "Content-Type: application/json" \
  -v

echo ""
echo "=== Fim dos testes ==="
