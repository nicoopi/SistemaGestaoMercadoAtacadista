package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class ArquivoUtil {
    public void salvarDados(Object dados, String nomeArquivo) {
        try {
            File pasta = new File("dados");
            if (!pasta.exists()) {
                pasta.mkdir();
            }

            FileOutputStream fileOut = new FileOutputStream("dados/" + nomeArquivo);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(dados);

            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("ERRO ao salvar o arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }

    public Object lerDados(String nomeArquivo) {
        Object dadosLidos = null;

        try {
            FileInputStream fileIn = new FileInputStream("dados/" + nomeArquivo);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            dadosLidos = objectIn.readObject();

            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("Aviso: Arquivo " + nomeArquivo + " não encontrado. Um novo será criado ao salvar.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERRO ao ler o arquivo " + nomeArquivo + ": " + e.getMessage());
        }
        return dadosLidos;
    }
}
