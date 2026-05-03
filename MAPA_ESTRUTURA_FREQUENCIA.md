# MAPA DE ESTRUTURA DO PROJETO ERP ESCOLAR - FREQUÊNCIA

## 1. VISÃO GERAL DO PROJETO

**Tecnologias Stack:**
- **Framework:** Spring Boot 3.5.11
- **Linguagem:** Java 17
- **Banco de Dados:** MySQL 8.0
- **Protocolo:** REST API
- **Documentação:** Swagger/OpenAPI
- **ORM:** Hibernate (JPA)
- **Total de Código:** ~2.180 linhas de Java

**Endereço do Banco de Dados:**
- Host: `134.209.164.87:3306`
- Database: `droplexdb`
- Driver: MySQL Connector/J 8.0.33

---

## 2. ESTRUTURA DE DIRETÓRIOS

```
D:\sistemas2025\Erp_Escolar_API/
├── src/main/
│   ├── java/com/escola/api/
│   │   ├── config/              # Configurações (CORS, etc)
│   │   ├── controller/          # REST Controllers
│   │   ├── service/             # Lógica de negócio
│   │   ├── repository/          # Acesso a dados (JPA)
│   │   ├── entity/              # Entidades/Modelos
│   │   ├── dto/                 # Data Transfer Objects
│   │   └── EscApiApplication.java
│   └── resources/
│       ├── application.properties
│       └── indices_performance.sql
├── pom.xml
├── mvnw
└── target/
```

---

## 3. ARQUIVOS RELACIONADOS A FREQUÊNCIA

### 3.1 ENTITY (Modelo de Dados)

**Arquivo:** `D:\sistemas2025\Erp_Escolar_API\src\main\java\com\escola\api\entity\Frequencia.java`

**Estrutura:**
```java
@Entity
@Table(name = "esc_frequencia", uniqueConstraints = @UniqueConstraint(columnNames = {"id_aluno", "id_turma", "data"}))
public class Frequencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;
    
    @ManyToOne
    @JoinColumn(name = "id_turma", nullable = false)
    private Turma turma;
    
    @Column(nullable = false)
    private LocalDate data;
    
    @Column(length = 1, nullable = false)
    private String status;  // P (Presente), F (Falta), etc
    
    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;
}
```

**Linhas:** 48
**Tabela Banco:** `esc_frequencia`
**Constraint Única:** (id_aluno, id_turma, data) - Impede duplicatas

---

### 3.2 REPOSITORY (Acesso a Dados)

**Arquivo:** `D:\sistemas2025\Erp_Escolar_API\src\main\java\com\escola\api\repository\FrequenciaRepository.java`

**Métodos Disponíveis:**

| Método | Descrição | Complexidade |
|--------|-----------|--------------|
| `findAll()` | Retorna todas as frequências (herdado) | O(n) |
| `findById(Integer id)` | Busca frequência por ID (herdado) | O(1) |
| `findByAlunoAndTurmaAndData()` | Busca 1 registro específico | **CRÍTICO - Índice!** |
| `findByTurmaAndDataBetween()` | Busca frequências por turma e período | **CRÍTICO - Índice!** |
| `findByTurmaIdAndDataBetween()` | Alternativa com ID de turma | **CRÍTICO - Índice!** |

**Linhas:** 15

---

### 3.3 SERVICE (Lógica de Negócio)

**Arquivo:** `D:\sistemas2025\Erp_Escolar_API\src\main\java\com\escola\api\service\FrequenciaService.java`

**Métodos Principais:**

| Método | Descrição | Performance |
|--------|-----------|-------------|
| `listarTodos()` | Carrega TODAS as frequências do BD | ⚠️ CUIDADO - N grandes |
| `buscarPorId(Integer id)` | Busca 1 frequência | ✅ Otimizado |
| `salvar(Frequencia)` | Salva ou atualiza 1 frequência | ✅ Otimizado |
| `deletar(Integer id)` | Deleta 1 frequência | ✅ Otimizado |
| `buscarPorTurmaEPeriodo()` | **PRINCIPAL** - Busca por turma + data | **SUPER CRÍTICO** |
| `salvarLote()` | Salva múltiplas frequências em loop | ⚠️ N+1 queries |

**Linhas:** 136

**Pontos de Atenção:**

1. ✅ **Validações de existência** (aluno, turma)
2. ✅ **Verificação de fim de semana** (bloqueia sábado/domingo)
3. ✅ **Prevenção de duplicatas** (usa unique constraint)
4. ⚠️ **`salvarLote()`** executa loop - cria N queries
5. ⚠️ **`findByAlunoAndTurmaAndData()`** - cada chamada é 1 query SELECT

---

### 3.4 CONTROLLER (REST Endpoints)

**Arquivo:** `D:\sistemas2025\Erp_Escolar_API\src\main\java\com\escola\api\controller\FrequenciaController.java`

**Endpoints Disponíveis:**

| Método | Endpoint | Descrição | Performance |
|--------|----------|-----------|------------|
| GET | `/frequencias` | Listar todas ou filtrar por turma+período | ✅ Com filtro é bom |
| GET | `/frequencias/{id}` | Buscar frequência específica | ✅ Otimizado |
| POST | `/frequencias` | Salvar nova frequência | ✅ Otimizado |
| PUT | `/frequencias/{id}` | Atualizar frequência | ✅ Otimizado |
| DELETE | `/frequencias/{id}` | Deletar frequência | ✅ Otimizado |
| POST | `/frequencias/salvar-lote` | **IMPORTANTE** - Salvar múltiplas | ⚠️ Loop problem |

**Linhas:** 101

**Query Parameters Suportados:**
- `turmaId` (Integer) - ID da turma
- `dataInicio` (String) - Data inicial (YYYY-MM-DD)
- `dataFim` (String) - Data final (YYYY-MM-DD)

---

### 3.5 DTOs (Objetos de Transferência)

#### 3.5.1 FrequenciaRequest.java

**Arquivo:** `D:\sistemas2025\Erp_Escolar_API\src\main\java\com\escola\api\dto\FrequenciaRequest.java`

**Campos:**
```java
public class FrequenciaRequest {
    private Integer alunoId;
    private Integer turmaId;
    private LocalDate data;
    private String status;
}
```

**Uso:** Requisições POST/PUT e salvar lote
**Linhas:** 51

#### 3.5.2 FrequenciaCompleteResponseDTO.java

**Arquivo:** `D:\sistemas2025\Erp_Escolar_API\src\main\java\com\escola\api\dto\FrequenciaCompleteResponseDTO.java`

**Campos:**
```java
public class FrequenciaCompleteResponseDTO {
    private List<Aluno> alunos;                          // Alunos da turma
    private List<Frequencia> frequencias;                // Frequências do período
    private Map<Integer, Integer> ocorrenciasCount;      // Contagem de ocorrências
    private List<ChamadaConfirmada> chamadasConfirmadas; // Chamadas confirmadas
}
```

**Uso:** Resposta do endpoint `/turmas/{id}/dados-completos`
**Linhas:** 63

---

## 4. ENTIDADES RELACIONADAS

### 4.1 Aluno

**Arquivo:** `D:\sistemas2025\Erp_Escolar_API\src\main\java\com\escola\api\entity\Aluno.java`

```java
@Entity
@Table(name = "esc_aluno")
public class Aluno {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_turma")
    @JsonIgnoreProperties({"escola"})
    private Turma turma;
}
```

**Fetch Type:** EAGER (carga a turma automaticamente)
**Linhas:** 32

---

### 4.2 Turma

**Arquivo:** `D:\sistemas2025\Erp_Escolar_API\src\main\java\com\escola\api\entity\Turma.java`

```java
@Entity
@Table(name = "esc_turma")
public class Turma {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_escola")
    private Escola escola;
    
    private String nome;
    private String periodo;      // Matutino, Vespertino, Noturno
    
    @Column(name = "ano_letivo")
    private Integer anoLetivo;
}
```

**Linhas:** 38

---

### 4.3 Ocorrencia

**Arquivo:** `D:\sistemas2025\Erp_Escolar_API\src\main\java\com\escola\api\entity\Ocorrencia.java`

```java
@Entity
@Table(name = "esc_ocorrencias")
public class Ocorrencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "aluno_id", nullable = false)
    private Integer alunoId;
    
    @Column(name = "turma_id", nullable = false)
    private Integer turmaId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_ocorrencia_id", nullable = false)
    private TipoOcorrencia tipoOcorrencia;
    
    @Column(name = "data_ocorrencia", nullable = false)
    private LocalDate dataOcorrencia;
    
    @Column(name = "hora_ocorrencia", nullable = false)
    private LocalTime horaOcorrencia;
    
    private String observacoes;
    private String emailProfessor;
    private String nomeProfessor;
    private LocalDateTime criadoEm;
}
```

**Linhas:** 79

**Repository Método Otimizado:**
```java
@Query("SELECT new map(o.alunoId as alunoId, COUNT(o) as count) " +
       "FROM Ocorrencia o " +
       "WHERE o.turmaId = :turmaId " +
       "GROUP BY o.alunoId")
List<Map<String, Object>> countOcorrenciasBy
