# Script de teste para API de Tipos de Ocorrências

Write-Host "=== Testando API de Tipos de Ocorrências ===" -ForegroundColor Cyan
Write-Host ""

# Teste 1: GET todos os tipos
Write-Host "1️⃣  Testando GET /api/tipos-ocorrencias" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/tipos-ocorrencias" `
        -Method GET `
        -Headers @{"Content-Type" = "application/json"} `
        -ErrorAction Stop
    
    Write-Host "✅ Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Resposta:" -ForegroundColor Green
    $response.Content | ConvertFrom-Json | ConvertTo-Json | Write-Host
} catch {
    Write-Host "❌ Erro: $_" -ForegroundColor Red
}

Write-Host ""
Write-Host ""

# Teste 2: GET tipos ativos
Write-Host "2️⃣  Testando GET /api/tipos-ocorrencias/ativos" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/tipos-ocorrencias/ativos" `
        -Method GET `
        -Headers @{"Content-Type" = "application/json"} `
        -ErrorAction Stop
    
    Write-Host "✅ Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Resposta:" -ForegroundColor Green
    $response.Content | ConvertFrom-Json | ConvertTo-Json | Write-Host
} catch {
    Write-Host "❌ Erro: $_" -ForegroundColor Red
}

Write-Host ""
Write-Host ""

# Teste 3: GET por ID
Write-Host "3️⃣  Testando GET /api/tipos-ocorrencias/1" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/tipos-ocorrencias/1" `
        -Method GET `
        -Headers @{"Content-Type" = "application/json"} `
        -ErrorAction Stop
    
    Write-Host "✅ Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Resposta:" -ForegroundColor Green
    $response.Content | ConvertFrom-Json | ConvertTo-Json | Write-Host
} catch {
    Write-Host "❌ Erro: $_" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== Fim dos testes ===" -ForegroundColor Cyan
