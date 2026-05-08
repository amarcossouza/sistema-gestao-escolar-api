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
            // Usa A4 paisagem
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc, com.itextpdf.kernel.geom.PageSize.A4.rotate());

            // Margens pequenas para máximo aproveitamento
            doc.setMargins(10, 5, 10, 5);

            // ===== CABEÇALHO COMPACTO =====
            Paragraph titulo = new Paragraph(NOME_ESCOLA)
                    .setFontSize(9)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(1);
            doc.add(titulo);

                // Cabeçalho com mês/ano e turma em negrito
                String mesAno = String.format("%02d/%d", mes, ano);
                String turmaStr = nomeTurma != null ? nomeTurma : "";
                Paragraph periodo = new Paragraph()
                    .add("Frequência - ")
                    .add(new com.itextpdf.layout.element.Text(mesAno).setBold())
                    .add(" | Turma: ")
                    .add(new com.itextpdf.layout.element.Text(turmaStr).setBold())
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(3);
                doc.add(periodo);

            // ===== TABELA - UMA PÁGINA SÓ =====
            int diasNoMes = obterDiasNoMes(ano, mes);
            int totalColunas = 3 + diasNoMes;
            float larguraTotal = 100f;
            float larguraNum = 3.5f; // Nº
            float larguraFaltas = 4.5f; // Faltas
            float larguraAluno = 38f; // ALUNO (prioridade visual)
            float larguraDias = (larguraTotal - (larguraNum + larguraFaltas + larguraAluno)) / diasNoMes;
            float[] larguras = new float[totalColunas];
            larguras[0] = larguraNum;
            larguras[1] = larguraAluno;
            larguras[2] = larguraFaltas;
            for (int i = 3; i < totalColunas; i++) {
                larguras[i] = larguraDias;
            }
            Table tabela = new Table(com.itextpdf.layout.properties.UnitValue.createPercentArray(larguras)).useAllAvailableWidth();

            // CABEÇALHO: Números dos dias
            adicionarCelulaPadronizada(tabela, "Nº", true);
            adicionarCelulaPadronizada(tabela, "ALUNO", true);
            adicionarCelulaPadronizada(tabela, "Faltas", true);
            for (int dia = 1; dia <= diasNoMes; dia++) {
                adicionarCelulaPadronizada(tabela, String.valueOf(dia), true);
            }

            // CABEÇALHO: Dias da semana
            adicionarCelulaPadronizada(tabela, "", false);
            adicionarCelulaPadronizada(tabela, "", false);
            adicionarCelulaPadronizada(tabela, "", false);
            for (int dia = 1; dia <= diasNoMes; dia++) {
                String diaSemana = obterDiaSemana(dia, ano, mes);
                adicionarCelulaPadronizada(tabela, diaSemana, false);
            }

            // DADOS DOS ALUNOS
            if (dadosCompletos.getAlunos() != null && !dadosCompletos.getAlunos().isEmpty()) {
                for (Aluno aluno : dadosCompletos.getAlunos()) {
                    adicionarCelulaPadronizada(tabela, String.valueOf(aluno.getId()), false);
                    adicionarCelulaPadronizada(tabela, aluno.getNome(), false);
                    int faltas = contarFaltas(dadosCompletos, aluno.getId());
                    // Se faltas > 0, negrito; se zero, normal
                    adicionarCelulaPadronizada(tabela, String.valueOf(faltas), faltas > 0);
                    LocalDate hoje = LocalDate.now();
                    boolean ehMesAtual = (ano == hoje.getYear() && mes == hoje.getMonthValue());
                    for (int dia = 1; dia <= diasNoMes; dia++) {
                        LocalDate data = LocalDate.of(ano, mes, dia);
                        // Se sábado ou domingo, imprime célula vazia
                        if (data.getDayOfWeek().getValue() == 6 || data.getDayOfWeek().getValue() == 7) {
                            adicionarCelulaPadronizada(tabela, "", false);
                        } else if (ehMesAtual && data.isAfter(hoje)) {
                            // Se mês atual e dia futuro, imprime célula vazia
                            adicionarCelulaPadronizada(tabela, "", false);
                        } else {
                            String status = obterStatusFrequencia(dadosCompletos, aluno.getId(), dia, ano, mes);
                            adicionarCelulaPadronizada(tabela, status, false);
                        }
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
    /**
     * Adiciona célula padronizada: mesma fonte, tamanho, alinhamento, altura uniforme.
     */
    private void adicionarCelulaPadronizada(Table tabela, String conteudo, boolean ehCabecalho) {
        Cell celula = new Cell();
        String texto = conteudo == null ? "" : conteudo;
        Paragraph para = new Paragraph(texto)
            .setFontSize(7)
            .setTextAlignment(TextAlignment.CENTER)
            .setVerticalAlignment(VerticalAlignment.MIDDLE)
            .setMargin(0)
            .setMultipliedLeading(1f);
        if (ehCabecalho || "F".equals(texto)) {
            para.setBold();
        }
        celula.add(para);
        celula.setPadding(1.2f);
        celula.setMargin(0);
        celula.setVerticalAlignment(VerticalAlignment.MIDDLE);
        celula.setBorder(new SolidBorder(ColorConstants.BLACK, 0.18f));
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
