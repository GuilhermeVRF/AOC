package processaimagebalckwhite;

import processaimagebalckwhite.parallelEditor.Editor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ProcessImageBalckWhite {




    public static void main(String[] args) {

        // Pegando os arquivos da memória secundária
        File directory = new File("C:\\Users\\guilh\\Documentos\\IdeaProjects\\AOC\\src\\processaimagebalckwhite\\modificadas");
        File[] imagesFile = directory.listFiles();

        //Carregando as imagens no Workpool
        for(File img : imagesFile){
            Editor.addFIle(img);
        }

        // Pegando o número de cores do processador disponíveis
        int nCores = Runtime.getRuntime().availableProcessors();
        Editor[] editors = new Editor[nCores];
        // Iniciando a paralelização da execução das tarefas
        for(int core = 0; core < nCores; core++){
            Editor editor = new Editor();
            editors[core] = editor;
            editor.start();
        }

        // Sincronizando a thread principal com as threads filhas
        for(int posEditor = 0; posEditor < editors.length;posEditor++){
            try{
                editors[posEditor].join();
            }catch (InterruptedException ex){
                System.out.println("The main thread was finished before the expected!");
            }
        }
    }
}