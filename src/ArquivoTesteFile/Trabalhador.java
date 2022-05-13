/*
Wesley Assis
 */
package ArquivoTesteFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Trabalhador extends Thread {

    private final List<File> arquivosProcessar;
    private int numero;
    private String caminho = "";

    public int getNumero() {
        return numero;
    }

    public String getCaminho() {
        return caminho;
    }

    public Trabalhador(List<File> arquivosProcessar) {
        this.arquivosProcessar = arquivosProcessar;

 
    }

//Executando as atividades das threads
    @Override
    public void run() {
    int contador = 0;
        //inicia as threads
        for (File arquivos : arquivosProcessar) {

            try {
                String text = new String(Files.readAllBytes(Paths.get(arquivos.toString())));
                ArrayList<String> lines = new ArrayList<>();
                lines.add(text);
                // aplicar threads aqui.
                for (String line : lines) {
                    String[] items = line.replaceAll("\s+", ",").split(","); // alguns arquivos continham espaços em branco.
                    for (String item : items) {
                        if (item.matches("[0-9]+")) {
                            if (testaPrimo(Integer.parseInt(item))) {
                                if (Integer.parseInt(item) > numero) {
                                    numero = Integer.parseInt(item);
                                    caminho = arquivosProcessar.get(contador).toString();
                                    System.out.println(numero);
                                    System.out.println(caminho);
                                }

                            }

                        }

                       
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Trabalhador.class.getName()).log(Level.SEVERE, null, ex);
            }
            contador++;
        }
    }

    //Verificando se é primo ou não
    private static boolean testaPrimo(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }

        }
        return true;
    }

}
