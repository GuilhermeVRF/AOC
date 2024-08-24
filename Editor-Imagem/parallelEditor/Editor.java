package processaimagebalckwhite.parallelEditor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Editor extends Thread{
    // Workpool de tarefas - Imagens
    private static final List<File> filesList = new ArrayList<>();
    private static int updatedImages = 0;
    private static final Object acessController = new Object();

    public void run(){
        while (updatedImages < filesList.size()) {
            this.corrigirImagem();
        }
    }

    // Método para converter as imagens em matriz
    private int[][] lerPixels(String caminho) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(caminho));
            int largura = bufferedImage.getWidth(null);
            int altura = bufferedImage.getHeight(null);

            int[][] pixels = new int[largura][altura];
            for (int i = 0; i < largura; i++) {
                for (int j = 0; j < altura; j++) {
                    //normalizando de forma simplificada para imagem escala de cinza (é esperado ocorrer "clareamento")
                    float vermelho = new Color(bufferedImage.getRGB(i, j)).getRed();
                    float verde = new Color(bufferedImage.getRGB(i, j)).getGreen();
                    float azul = new Color(bufferedImage.getRGB(i, j)).getBlue();
                    int escalaCinza = (int) (vermelho + verde + azul) / 3;

                    pixels[i][j] = escalaCinza;
                }
            }

            return pixels;
        } catch (IOException ex) {
            System.err.println("Erro no caminho indicado pela imagem");
        }

        return null;
    }

    // Método síncronizado que garante que cada thread arrume uma imagem por vez
    private void corrigirImagem(){
        String imgPath;
        synchronized (acessController){
            imgPath = filesList.get(updatedImages++).getAbsolutePath();
        }

        int[][] imgMat = this.lerPixels(imgPath);
        int imagemCorrigida[][] = new int[imgMat.length][imgMat[0].length];

        int pixel;
        for(int posLine= 0; posLine< imgMat.length; posLine++){
            for(int posColumn=0; posColumn< imgMat[0].length;  posColumn++){
                pixel = imgMat[posLine][posColumn];
                if(pixel==255 || pixel==0){
                    pixel = getPixel(posLine, posColumn, imgMat);
                }

                imagemCorrigida[posLine][posColumn] = pixel;
            }

        }

        this.gravarPixels(imgPath, imagemCorrigida);
    }

    // Método para transformar as matrizes em imagens e salvar na memória secundária
    private void gravarPixels(String caminhoGravar, int pixels[][]) {
        caminhoGravar = caminhoGravar
                .replace(".png", "_modificado.png")
                .replace(".jpg", "_modificado.jpg");

        int largura = pixels.length;
        int altura = pixels[0].length;

        BufferedImage imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_BYTE_GRAY);

        // Transformando a mat. em um vetor de bytes
        byte bytesPixels[] = new byte[largura * altura];
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                bytesPixels[y * (largura) + x] = (byte) pixels[x][y];
            }
        }

        // Copindo todos os bytes para a nova imagem
        imagem.getRaster().setDataElements(0, 0, largura, altura, bytesPixels);

        // Criamos o arquivo e gravamos os bytes da imagem nele
        File ImageFile = new File(caminhoGravar);
        try {
            ImageIO.write(imagem, "png", ImageFile);
            System.out.println("Nova Imagem dispon. em: " + caminhoGravar);
        } catch (IOException e) {
            System.err.println("Erro no caminho indicado pela imagem");
        }
    }

    // Método para verificar se existe um ruído na imagem (0 ou 255) e o remover
    public static int getPixel(int posLine, int posColumn, int imgMat[][]){
        int width = imgMat.length;
        int heigth = imgMat[0].length;
        int aroundPixelMedium = 0;
        int cont = 0;

        for(int posLinePixel = posLine-1; posLinePixel<=posLine+1;posLinePixel++){
            for(int posColumnPixel = posColumn-1; posColumnPixel<=posColumn+1;posColumnPixel++){
                if (posLinePixel >= 0 && posLinePixel < width && posColumnPixel >= 0 && posColumnPixel < heigth) {
                    aroundPixelMedium += imgMat[posLinePixel][posColumnPixel];
                    cont++;
                }
            }
        }

        return aroundPixelMedium/cont;
    }

    public static void addFIle(File image){
        filesList.add(image);
    }
}
