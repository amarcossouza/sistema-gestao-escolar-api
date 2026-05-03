package com.escola.api.controller;

import com.escola.api.dto.FrequenciaCompleteResponseDTO;
import com.escola.api.dto.FrequenciaRelatorioPdfRequest;
import com.escola.api.entity.Turma;
import com.escola.api.service.FrequenciaPdfService;
import com.escola.api.service.TurmaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerar relatórios PDF de frequência
 */
@RestController
@RequestMapping("/relatorios/frequencia")
public class FrequenciaRelatorioPdfController {

    private final FrequenciaPdfService frequenciaPdfService;
    private final TurmaService turmaService;

    public FrequenciaRelatorioPdfController(
            FrequenciaPdfService frequenciaPdfService,
            TurmaService turmaService) {
        this.frequenciaPdfService = frequenciaPdfService;
        this.turmaService = turmaService;
    }

    /**
     * Gera um PDF com o relatório de frequência
     * 
     * @param request Contém turmaId, mes e ano
     * @return Arquivo PDF como resposta
     */
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> gerarRelatorioPdf(@RequestBody FrequenciaRelatorioPdfRequest request) {
        try {
            System.out.println("\n>>> POST /relatorios/frequencia/pdf");
            System.out.println("Turma: " + request.getTurmaId() + " | Mês: " + request.getMes() + " | Ano: " + request.getAno());

            // Validar entrada
            if (request.getTurmaId() == null || request.getMes() == null || request.getAno() == null) {
                return ResponseEntity.badRequest().build();
            }

            // Carregar dados da turma
            Turma turma = turmaService.buscarPorId(request.getTurmaId());
            if (turma == null) {
                return ResponseEntity.notFound().build();
            }

            // Obter dados completos (alunos, frequências, etc)
            FrequenciaCompleteResponseDTO dadosCompletos = turmaService.obterDadosCompletos(
                    request.getTurmaId(),
                    request.getMes(),
                    request.getAno()
            );

            // Gerar PDF
            byte[] pdfBytes = frequenciaPdfService.gerarRelatorioPdf(
                    request.getTurmaId(),
                    request.getMes(),
                    request.getAno(),
                    dadosCompletos,
                    turma.getNome()
            );

            // Preparar resposta
            String nomeArquivo = String.format("Frequencia_%s_%02d_%d.pdf",
                    turma.getNome(),
                    request.getMes(),
                    request.getAno());

            System.out.println("<<< PDF gerado com sucesso: " + nomeArquivo + "\n");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                    .body(pdfBytes);

        } catch (Exception e) {
            System.err.println("❌ ERRO ao gerar PDF: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
