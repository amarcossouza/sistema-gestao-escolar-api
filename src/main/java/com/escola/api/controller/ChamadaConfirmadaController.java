package com.escola.api.controller;

import com.escola.api.entity.ChamadaConfirmada;
import com.escola.api.service.ChamadaConfirmadaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chamada-confirmada")
public class ChamadaConfirmadaController {
    private final ChamadaConfirmadaService chamadaService;

    public ChamadaConfirmadaController(ChamadaConfirmadaService chamadaService) {
        this.chamadaService = chamadaService;
    }

    @GetMapping
    public ResponseEntity<List<ChamadaConfirmada>> buscarPorPeriodo(
            @RequestParam Integer turmaId,
            @RequestParam String dataInicio,
            @RequestParam String dataFim
    ) {
        List<ChamadaConfirmada> chamadas = chamadaService.buscarPorTurmaEPeriodo(
            turmaId,
            LocalDate.parse(dataInicio),
            LocalDate.parse(dataFim)
        );
        if (chamadas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(chamadas);
    }

    @PostMapping
    public ResponseEntity<?> confirmarChamada(@RequestBody Map<String, Object> body) {
        try {
            Integer turmaId = ((Number) body.get("turmaId")).intValue();
            String dataChamadaStr = (String) body.get("dataChamada");
            String dataHoraConfirmacaoStr = (String) body.get("dataHoraConfirmacao");
            String emailProfessor = (String) body.get("emailProfessor");

            System.out.println("Recebido: turmaId=" + turmaId + ", dataChamada=" + dataChamadaStr + ", dataHora=" + dataHoraConfirmacaoStr);

            LocalDate dataChamada = LocalDate.parse(dataChamadaStr);
            
            // Trata ambos os formatos de DateTime (com Z e sem Z)
            LocalDateTime dataHoraConfirmacao;
            if (dataHoraConfirmacaoStr.contains("Z")) {
                dataHoraConfirmacao = LocalDateTime.parse(dataHoraConfirmacaoStr.replace("Z", ""));
            } else {
                dataHoraConfirmacao = LocalDateTime.parse(dataHoraConfirmacaoStr);
            }

            ChamadaConfirmada chamada = chamadaService.confirmarChamada(
                turmaId,
                dataChamada,
                dataHoraConfirmacao,
                emailProfessor
            );

            System.out.println("Chamada salva com sucesso!");
            return ResponseEntity.ok(chamada);
        } catch (Exception e) {
            System.err.println("Erro ao confirmar chamada: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    // DELETE: Apenas para uso administrativo/correção de erros
    // O frontend NÃO faz chamadas DELETE - uma vez confirmado, é permanente
    @DeleteMapping
    public ResponseEntity<?> removerConfirmacao(
            @RequestParam Integer turmaId,
            @RequestParam String dataChamada
    ) {
        try {
            LocalDate data = LocalDate.parse(dataChamada);
            System.out.println("⚠️ AVISO: Removendo confirmação de chamada (Turma " + turmaId + ", Data " + data + ")");
            chamadaService.removerConfirmacao(turmaId, data);
            System.out.println("✓ Confirmação removida com sucesso!");
            return ResponseEntity.ok().body("Confirmação removida (apenas admin)");
        } catch (Exception e) {
            System.err.println("❌ Erro ao remover confirmação: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
}
