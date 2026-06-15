package util;

import java.io.*;
import java.time.LocalDateTime;

public class LoggerService {
    private static final String ARQUIVO = "logs/log.txt";

    public static void log(String tipo, String mensagem) {
        try {
            File pasta = new File("logs");
            if (!pasta.exists()) {
                pasta.mkdir();
            }

            FileWriter writer = new FileWriter(ARQUIVO, true);

            LocalDateTime dataAgora = LocalDateTime.now();

            String linha = "[" + dataAgora + "] " + tipo + " - " + mensagem + "\n";

            writer.write(linha);
            writer.close();

        } catch (IOException e) {
            System.out.println("Erro ao registrar log.");
        }
    }
}