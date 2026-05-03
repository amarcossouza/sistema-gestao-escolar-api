package com.escola.api.service;

import com.escola.api.dto.FrequenciaCompleteResponseDTO;
import com.escola.api.entity.Aluno;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.SolidBorder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Serviço para gerar relatórios PDF de frequência em UMA PÁGINA A4
 * 
 * Design: Minimalista, sem cores, linhas finas
 * Objetivo: Caber ~35 alunos x 31 dias em uma folha A4
 */
@Service
public class FrequenciaPdfService {

    private static final String NOME_ESCOLA = "EMEF JOSÉ DE FIGUEIREDO FERRAZ";

    /**
     * Gera um PDF com o relatório de frequência em UMA PÁGINA A4
     */
    public byte[] gerarRelatorioPdf(
            Integer turmaId,
            Integer mes,
            Integer ano,
            FrequenciaCompleteResponseDTO dadosCompletos,
            String nomeTurma) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc, new com.itextpdf.kernel.geom.PageSize(210, 297));

            // Margens MUITO PEQUENAS para máximo aproveitamento
            doc.setMargins(5, 5, 5, 5);

            // ===== CABEÇALHO COMPACTO =====
            Paragraph titulo = new Paragraph(NOME_ESCOLA)
                    .setFontSize(9)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(1);
            doc.add(titulo);

            Paragraph periodo = new Paragraph(String.format(
                    "Frequência - %s/%d | Turma: %s",
                    String.format("%02d", mes), ano, nomeTurma))
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(3);
            doc.add(periodo);

            // ===== TABELA - UMA PÁGINA SÓ =====
            int diasNoMes = obterDiasNoMes(ano, mes);
            int numColunas = 3 + diasNoMes; // Nº, Nome, Faltas + dias

            Table tabela = new Table(numColunas);
            tabela.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));

            // CABEÇALHO: Números dos dias
            adicionarCelulaCompacta(tabela, "Nº", true, 10);
            adicionarCelulaCompacta(tabela, "ALUNO", true, 60);
            adicionarCelulaCompacta(tabela, "F", true, 10);

            for (int dia = 1; dia <= diasNoMes; dia++) {
                adicionarCelulaCompacta(tabela, String.valueOf(dia), true, 4);
            }

            // CABEÇALHO: Dias da semana
            adicionarCelulaCompacta(tabela, "", false, 10);
            adicionarCelulaCompacta(tabela, "", false, 60);
            adicionarCelulaCompacta(tabela, "", false, 10);

            for (int dia = 1; dia <= diasNoMes; dia++) {
                String diaSemana = obterDiaSemana(dia, ano, mes);
                adicionarCelulaCompacta(tabela, diaSemana, false, 4);
            }

            // DADOS DOS ALUNOS
            if (dadosCompletos.getAlunos() != null && !dadosCompletos.getAlunos().isEmpty()) {
                for (Aluno aluno : dadosCompletos.getAlunos()) {
                    // ID do aluno
                    adicionarCelulaCompacta(tabela, String.valueOf(aluno.getId()), false, 10);

                    // Nome (truncado)
                    String nome = aluno.getNome().length() > 20 ? 
                        aluno.getNome().substring(0, 20) : aluno.getNome();
                    adicionarCelulaCompacta(tabela, nome, false, 60);

                    // Total de faltas
                    int faltas = contarFaltas(dadosCompletos, aluno.getId());
                    adicionarCelulaCompacta(tabela, String.valueOf(faltas), false, 10);

                    // Frequência dos dias
                    for (int dia = 1; dia <= diasNoMes; dia++) {
                        String status = obterStatusFrequencia(dadosCompletos, aluno.getId(), dia, ano, mes);
                        adicionarCelulaCompacta(tabela, status, false, 4);
                    }
                }
            }

            doc.add(tabela);

            // RODAPÉ
            Paragraph rodape = new Paragraph("C = Comparecimento | F = Falta (em negrito)")
                    .setFontSize(6)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(2);
            doc.add(rodape);

            doc.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    /**
     * Adiciona célula COMPACTA - 8pt cabeçalho, 7pt dados
     */
    private void adicionarCelulaCompacta(Table tabela, String conteudo, boolean ehCabecalho, float widthPercent) {
        Cell celula = new Cell();

        Paragraph para = new Paragraph(conteudo)
                .setFontSize(6)
                .setTextAlignment(TextAlignment.CENTER)
                .setMargin(0);

        if (ehCabecalho) {
            para.setBold();
        }

        if ("F".equals(conteudo)) {
            para.setBold();
        }

        celula.add(para);
        celula.setPadding(0.5f);
        celula.setMargin(0);
        celula.setVerticalAlignment(VerticalAlignment.MIDDLE);
        celula.setBorder(new SolidBorder(ColorConstants.BLACK, 0.2f)); // Linha muito fina
        celula.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(widthPercent));
        celula.setBackgroundColor(ColorConstants.WHITE);

        tabela.addCell(celula);
    }

    /**
     * Conta faltas de um aluno
     */
    private int contarFaltas(FrequenciaCompleteResponseDTO dados, Integer alunoId) {
        if (dados.getFrequencias() == null) return 0;

        return (int) dados.getFrequencias().stream()
                .filter(f -> f.getAluno() != null && 
                           f.getAluno().getId().equals(alunoId) &&
                           "F".equals(f.getStatus()))
                .count();
    }

    /**
     * Obtém status de frequência (C ou F)
     */
    private String obterStatusFrequencia(
            FrequenciaCompleteResponseDTO dados,
            Integer alunoId,
            Integer dia,
            Integer ano,
            Integer mes) {

        if (dados.getFrequencias() == null) return "C";

        LocalDate data = LocalDate.of(ano, mes, dia);

        return dados.getFrequencias().stream()
                .filter(f -> f.getAluno() != null &&
                           f.getAluno().getId().equals(alunoId) &&
                           f.getData() != null &&
                           f.getData().equals(data) &&
                           "F".equals(f.getStatus()))
                .findFirst()
                .map(f -> "F")
                .orElse("C");
    }

    /**
     * Obtém dias no mês
     */
    private int obterDiasNoMes(Integer ano, Integer mes) {
        return YearMonth.of(ano, mes).lengthOfMonth();
    }

    /**
     * Obtém inicial do dia da semana
     */
    private String obterDiaSemana(Integer dia, Integer ano, Integer mes) {
        LocalDate data = LocalDate.of(ano, mes, dia);
        String[] dias = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"};
        return dias[data.getDayOfWeek().getValue() % 7];
    }
}
