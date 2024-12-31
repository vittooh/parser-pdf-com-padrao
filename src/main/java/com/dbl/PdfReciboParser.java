package com.dbl;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PdfReciboParser {

    public static void main(String[] args) throws IOException {
        PDDocument document = Loader.
                loadPDF(new File("C:\\Users\\vitor\\Documents\\digitalbricklayer\\extrator-pdf-java\\src\\main\\resources\\pdf_padrao.docx.pdf"));

        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        List<String> linhas = List.of(pdfTextStripper.getText(document).split("\n"));
        linhas = linhas.stream()
                .map(linha -> linha.replace("\r", ""))
                .filter(linha -> !linha.isEmpty()).toList();

        linhas.forEach(System.out::println);
        AtomicBoolean acheiProdutos = new AtomicBoolean(false);

        linhas.forEach(linha -> {
            if (linha.contains("Nome")) {
                String nome = linha.substring(linha.indexOf("Nome") + 4, linha.indexOf("Email")).trim();
                String email = linha.substring(linha.indexOf("Email") + 5).trim();
                System.out.println("Nome ::" + nome);
                System.out.println("Email ::" + email);
                return;
            }

            if (linha.contains("Data de Pagamento")) {
                String dataPagamento = linha.replace("Data de Pagamento", "").trim();
                System.out.println("Data Pagamento : " + dataPagamento);
                return;
            }

            if (linha.contains("Produtos")) {
                acheiProdutos.set(true);
                return;
            }

            if (acheiProdutos.get()) {
                String nomeProduto = linha.substring(0, linha.indexOf("Valor")).trim();
                String valor = linha.substring(linha.indexOf("Valor") + 5).trim();
                System.out.println("nomeProduto ::" + nomeProduto);
                System.out.println("valor ::" + valor);

            }
        });

    }
}
