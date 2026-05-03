package com.escola.api.service;

import com.escola.api.dto.FrequenciaRequest;
import com.escola.api.entity.Aluno;
import com.escola.api.entity.Frequencia;
import com.escola.api.entity.Turma;
import com.escola.api.repository.AlunoRepository;
import com.escola.api.repository.FrequenciaRepository;
import com.escola.api.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FrequenciaService {
    private final FrequenciaRepository frequenciaRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    public FrequenciaService(FrequenciaRepository frequenciaRepository, 
                             AlunoRepository alunoRepository,
                             TurmaRepository turmaRepository) {
        this.frequenciaRepository = frequenciaRepository;
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
    }

    public List<Frequencia> listarTodos() {
        return frequenciaRepository.findAll();
    }

    public Frequencia buscarPorId(Integer id) {
        return frequenciaRepository.findById(id).orElse(null);
    }

    public Frequencia salvar(Frequencia frequencia) {
        try {
            System.out.println("=== SALVANDO FREQUÊNCIA ===");
            System.out.println("Frequência: " + frequencia);
            
            // Validar se aluno e turma existem
            if (frequencia.getAluno() == null || frequencia.getAluno().getId() == null) {
                throw new IllegalArgumentException("Aluno é obrigatório");
            }
            if (frequencia.getTurma() == null || frequencia.getTurma().getId() == null) {
                throw new IllegalArgumentException("Turma é obrigatória");
            }
            
            // Buscar aluno completo
            Aluno aluno = alunoRepository.findById(frequencia.getAluno().getId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + frequencia.getAluno().getId()));
            
            // Buscar turma completa
            Turma turma = turmaRepository.findById(frequencia.getTurma().getId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada: " + frequencia.getTurma().getId()));
            
            // Verificar se já existe frequência para essa data
            Frequencia frequenciaExistente = frequenciaRepository.findByAlunoAndTurmaAndData(aluno, turma, frequencia.getData());
            
            if (frequenciaExistente != null) {
                System.out.println("Atualizando frequência existente: " + frequenciaExistente.getId());
                frequenciaExistente.setStatus(frequencia.getStatus());
                return frequenciaRepository.save(frequenciaExistente);
            } else {
                System.out.println("Criando nova frequência");
                frequencia.setAluno(aluno);
                frequencia.setTurma(turma);
                return frequenciaRepository.save(frequencia);
            }
        } catch (Exception e) {
            System.err.println("❌ ERRO ao salvar frequência: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar frequência: " + e.getMessage(), e);
        }
    }

    public void deletar(Integer id) {
        frequenciaRepository.deleteById(id);
    }

    public List<Frequencia> buscarPorTurmaEPeriodo(Integer turmaId, LocalDate dataInicio, LocalDate dataFim) {
        Optional<Turma> turmaOpt = turmaRepository.findById(turmaId);
        if (turmaOpt.isEmpty()) {
            return List.of();
        }
        return frequenciaRepository.findByTurmaAndDataBetween(turmaOpt.get(), dataInicio, dataFim);
    }

    /**
     * Verifica se uma data é sábado ou domingo
     */
    private boolean ehFimDeSemana(LocalDate data) {
        DayOfWeek diaDaSemana = data.getDayOfWeek();
        return diaDaSemana == DayOfWeek.SATURDAY || diaDaSemana == DayOfWeek.SUNDAY;
    }

    public void salvarLote(List<FrequenciaRequest> frequencias) {
        for (FrequenciaRequest req : frequencias) {
            Optional<Aluno> alunoOpt = alunoRepository.findById(req.getAlunoId());
            Optional<Turma> turmaOpt = turmaRepository.findById(req.getTurmaId());
            
            if (alunoOpt.isEmpty() || turmaOpt.isEmpty()) {
                throw new IllegalArgumentException("Aluno ou Turma não encontrado");
            }
            
            // Verifica se é sábado ou domingo
            if (ehFimDeSemana(req.getData())) {
                throw new IllegalArgumentException("Não é permitido salvar frequência para sábado ou domingo");
            }
            
            // Verifica se já existe frequência para esse aluno nessa data
            Frequencia frequenciaExistente = frequenciaRepository.findByAlunoAndTurmaAndData(
                alunoOpt.get(), 
                turmaOpt.get(), 
                req.getData()
            );
            
            if (frequenciaExistente != null) {
                // Atualiza a frequência existente
                frequenciaExistente.setStatus(req.getStatus());
                frequenciaRepository.save(frequenciaExistente);
            } else {
                // Cria nova frequência
                Frequencia novaFrequencia = new Frequencia();
                novaFrequencia.setAluno(alunoOpt.get());
                novaFrequencia.setTurma(turmaOpt.get());
                novaFrequencia.setData(req.getData());
                novaFrequencia.setStatus(req.getStatus());
                frequenciaRepository.save(novaFrequencia);
            }
        }
    }
}
