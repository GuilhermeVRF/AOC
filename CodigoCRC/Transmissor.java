/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_transferenciadedados;

import java.util.Random;

/**
 *
 * @author guilh & rodol
 */
public class Transmissor {
    private String mensagem;

    public Transmissor(String mensagem) {
        this.mensagem = mensagem;
    }
    
    //convertendo um símbolo para "vetor" de boolean (bits)
    private boolean[] streamCaracter(char simbolo){
        
        //cada símbolo da tabela ASCII é representado com 8 bits
        boolean bits[] = new boolean[12];
        
        //convertendo um char para int (encontramos o valor do mesmo na tabela ASCII)
        int valorSimbolo = (int) simbolo;
        int indice = 7;
        //convertendo cada "bits" do valor da tabela ASCII
        while(valorSimbolo >= 2){
            int resto = valorSimbolo % 2;
            valorSimbolo /= 2;
            bits[indice] = (resto == 1);
            indice--;
        }
        bits[indice] = (valorSimbolo == 1);

        return bits;
    } 
    
    //não modifique (seu objetivo é corrigir esse erro gerado no receptor)
    private void geradorRuido(boolean bits[]){
        Random geradorAleatorio = new Random();
        
        //pode gerar um erro ou não..
        if(geradorAleatorio.nextInt(5) > 1){
            int indice = geradorAleatorio.nextInt(8);
            bits[indice] = !bits[indice];
        }
    }
    
    private boolean[] dadoBitsCRC(boolean bits[]){
       boolean[] crcTransmissor = CRC.gerarCRC(bits);
       for(int pos = 8, posCRC = 0; posCRC < crcTransmissor.length - 1; pos++, posCRC++){
            bits[pos] = crcTransmissor[posCRC];
        }    
       return bits;
    }
    
    public void enviaDado(Receptor receptor){
        for(int i = 0; i < this.mensagem.length();i++){
            boolean bits[] = streamCaracter(this.mensagem.charAt(i));
            
            //Preenchendo o dado com o código CRC.
            boolean bitsCRC[] = this.dadoBitsCRC(bits);
            boolean[] backupBits = new boolean[12];            
            
            do{
                System.arraycopy(bitsCRC, 0, backupBits, 0, 12);
                this.geradorRuido(backupBits);
            }while(!receptor.receberDadoBits(backupBits));
            
            //Enquanto o receptor não receber o dado íntegro, ele será re-enviado pelo transmissor.  

        }
    }
}
