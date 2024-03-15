/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arquitetura_organizacao;

import tp_transferenciadedados.CRC;

/**
 *
 * @author guilh
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean[] polinomio = CRC.polinomioGerado;
        
        for(int i = 0; i < polinomio.length;i++){
            if(polinomio[i]){
                System.out.print(1);
            }else{
                System.out.print(0);
            }          
        }
    }
    
}
