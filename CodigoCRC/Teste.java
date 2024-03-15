/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tp_transferenciadedados;

import java.util.Scanner;

/**
 *
 * @author guilh
 */
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        boolean[] bits = new boolean[12];
        
        //Preenchendo o vetor com o dado
        for(int pos = 0;pos < 8;pos++){
            System.out.println((pos + 1)+ "° bit: ");
            bits[pos] = (teclado.nextInt() == 1);
        }   
        
        boolean[] crc = CRC.gerarCRC(bits);
        
        //Adicionandoo código CRC ao dado
        for(int pos = 8, posCRC = 0; posCRC < 4;pos++, posCRC++){
            bits[pos] = crc[posCRC];
        }
        
        //Exibindo o dado + crc
        for(int pos = 0; pos < bits.length;pos++){
            if(pos == 8){
                System.out.print('-');
            }
            
            if(bits[pos]){
                System.out.print(1);
            }else{
                System.out.print(0);
            }
        }
    }
    
}
