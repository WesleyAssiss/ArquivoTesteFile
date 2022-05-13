package ArquivoTesteFile;

import java.io.File;

import java.util.Collections;
import java.util.Vector;

public class TesteFile {

    public static void main(String[] args) throws InterruptedException {

        //Criando as Variaveis
        int cont = 0;
        int maior = 0;
        String caminho = null;

        //Encontrando o local onde o ArquivoTeste está.
        File diretorioInicial = new File("C:\\Users\\Downloads\\ARQUIVOTESTE");

        Vector<File> arquivosVerificar = new Vector<>();
        Vector<File> arquivosProcessar = new Vector<>();

        //add todos os diretorios da pasta raiz
        Collections.addAll(arquivosVerificar,
                diretorioInicial.listFiles());

        //abrindo cada uma das sub pastas
        while (!arquivosVerificar.isEmpty()) {
            File arqAtual = arquivosVerificar.remove(0);
            //sera que isso é arquivo .txt?
            if (arqAtual.isFile()
                    && arqAtual.getAbsolutePath().endsWith(".txt")) {
                arquivosProcessar.add(arqAtual);
                System.out.println(arqAtual);

            } else {
                //pode  ser pasta ou arquivo NAO txt
                if (arqAtual.isDirectory()) {
                    Collections.addAll(arquivosVerificar,
                            arqAtual.listFiles());

                }

            }
        }
        //Dividindo de acordo com o numero total de arquivos e numero total de Threads 
        Trabalhador trabs[] = {new Trabalhador(arquivosProcessar.subList(0, 557)),
            new Trabalhador(arquivosProcessar.subList(558, 1149)),
            new Trabalhador(arquivosProcessar.subList(1150, 1703)),
            new Trabalhador(arquivosProcessar.subList(1704, 2279)),
            new Trabalhador(arquivosProcessar.subList(2280, 2849)),
            new Trabalhador(arquivosProcessar.subList(2850, 3528))};

        //Excutando as Threads
        for (Trabalhador trab : trabs) {
            trab.start();
        }
        //esperando o término da thread para qual foi enviada a mensagem para ser liberada.
        for (Trabalhador trab : trabs) {
            trab.join();
        }

        //percorrendo para encontrar o maior número primo e o caminho dele
        for (Trabalhador trab : trabs) {
            if (trab.getNumero() > maior) {
                //Maior número primo
                maior = trab.getNumero();
                //Caminho do maior número primo
                caminho = trab.getCaminho();
            }
        }

        System.out.println();
        System.out.println("O maior número primo é: " + maior);
        System.out.println();
        System.out.println("O caminho onde está o maior número primo é: " + caminho);

    }

}
