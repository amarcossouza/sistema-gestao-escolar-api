package com.escola.api.service;

import com.escola.api.dto.FrequenciaCompleteResponseDTO;
import com.escola.api.entity.Turma;
import com.escola.api.repository.TurmaRepository;
import com.escola.api.repository.AlunoRepository;
import com.escola.api.repository.FrequenciaRepository;
import com.escola.api.repository.OcorrenciaRepository;
import com.escola.api.repository.ChamadaConfirmadaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TurmaService {
    private final TurmaRepository turmaRepository;
    private final AlunoRepository alunoRepository;
    private final FrequenciaRepository frequenciaRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    private final ChamadaConfirmadaRepository chamadaConfirmadaRepository;

    public TurmaService(
            TurmaRepository turmaRepository,
            AlunoRepository alunoRepository,
            FrequenciaRepository frequenciaRepository,
            OcorrenciaRepository ocorrenciaRepository,
            ChamadaConfirmadaRepository chamadaConfirmadaRepository
    ) {
        this.turmaRepository = turmaRepository;
        this.alunoRepository = alunoRepository;
        this.frequenciaRepository = frequenciaRepository;
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.chamadaConfirmadaRepository = chamadaConfirmadaRepository;
    }

    public List<Turma> listarTodos() {
        return turmaRepository.findAll();
    }

    public Turma buscarPorId(Integer id) {
        return turmaRepository.findById(id).orElse(null);
    }

    public Turma salvar(Turma turma) {
        return turmaRepository.save(turma);
    }

    public void deletar(Integer id) {
        turmaRepository.deleteById(id);
    }

    public List<Turma> buscarPorPeriodo(String periodo) {
        return turmaRepository.findByPeriodo(periodo);
    }

    public FrequenciaCompleteResponseDTO obterDadosCompletos(Integer turmaId, Integer mes, Integer ano) {
        long inicioTempo = System.currentTimeMillis();
        System.out.println("\n=== BUSCANDO DADOS COMPLETOS ===");
        System.out.println("Turma: " + turmaId + " | Mês: " + mes + " | Ano: " + ano);
        
        // 1. Calcular datas
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, dataInicio.lengthOfMonth());
        System.out.println("Período: " + dataInicio + " até " + dataFim);

        // 2. Buscar alunos da turma
        long tempoAlunos = System.currentTimeMillis();
        var alunos = alunoRepository.findByTurmaId(turmaId);
        System.out.println("✓ Alunos encontrados: " + alunos.size() + " (" + (System.currentTimeMillis() - tempoAlunos) + "ms)");

        // 3. Buscar frequências do período
        long tempoFrequencias = System.currentTimeMillis();
        var frequencias = frequenciaRepository.findByTurmaIdAndDataBetween(turmaId, dataInicio, dataFim);
        System.out.println("✓ Frequências encontradas: " + frequencias.size() + " (" + (System.currentTimeMillis() - tempoFrequencias) + "ms)");

        // 4. Buscar chamadas confirmadas do período
        long tempoChamadas = System.currentTimeMillis();
        var chamadasConfirmadas = chamadaConfirmadaRepository.findByTurmaIdAndDataChamadaBetween(turmaId, dataInicio, dataFim);
        System.out.println("✓ Chamadas confirmadas encontradas: " + chamadasConfirmadas.size() + " (" + (System.currentTimeMillis() - tempoChamadas) + "ms)");

        // 5. Contar ocorrências por aluno - OTIMIZADO: única query em vez de N+1
        long tempoOcorrencias = System.currentTimeMillis();
        Map<Integer, Integer> ocorrenciasCount = new HashMap<>();
        
        // Query otimizada que traz tudo em uma única consulta
        var ocorrenciasAgrupadas = ocorrenciaRepository.countOcorrenciasByTurmaId(turmaId);
        for (var mapa : ocorrenciasAgrupadas) {
            Integer alunoId = ((Number) mapa.get("alunoId")).intValue();
            Long count = (Long) mapa.get("count");
            ocorrenciasCount.put(alunoId, count != null ? count.intValue() : 0);
        }
        
        // Garantir que alunos sem ocorrências tenham 0
        for (var aluno : alunos) {
            ocorrenciasCount.putIfAbsent(aluno.getId(), 0);
        }
        
        System.out.println("✓ Ocorrências contadas: " + ocorrenciasAgrupadas.size() + " alunos com ocorrências (" + (System.currentTimeMillis() - tempoOcorrencias) + "ms)");

        // 6. Montar DTO com todos os dados
        FrequenciaCompleteResponseDTO dto = new FrequenciaCompleteResponseDTO(
            alunos, 
            frequencias, 
            ocorrenciasCount, 
            chamadasConfirmadas
        );
        
        long tempoTotal = System.currentTimeMillis() - inicioTempo;
        System.out.println("=== DTO PRONTO ===");
        System.out.println("⏱️  TEMPO TOTAL: " + tempoTotal + "ms\n");
        return dto;
    }
}
