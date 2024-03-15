/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tp_transferenciadedados;

import java.util.Scanner;

/**
 *
 * @author guilh & rodol
 */
public class TP_TransferenciaDeDados {
    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        System.out.print("Informe o dado a ser transmitido: ");
        //criando receptor e transmissor com a informação a ser enviada
        Transmissor seuSmartphone = new Transmissor(entrada.nextLine());
        Receptor umServidorQualquer = new Receptor();

        //"enviando" a informação do "smartphone" ao "servidor"
        seuSmartphone.enviaDado(umServidorQualquer);

        //mensagem recebida no "servidor"
        System.out.println(umServidorQualquer.getMensagem());          
    }   
}
