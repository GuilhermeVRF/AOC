/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_transferenciadedados;

/**
 *
 * @author guilh & rodol
 */
public class Receptor {
     //mensagem recebida pelo transmissor
    private String mensagem;

    public Receptor() {
        //mensagem vazia no inicio da execução
        this.mensagem = "Saída: ";
    }
    
    public String getMensagem() {
        return mensagem;
    }
 
    private boolean decodificarDado(boolean bits[]){
        int codigoAscii = 0;
        int expoente = bits.length-1;
        
        //converntendo os "bits" para valor inteiro para então encontrar o valor tabela ASCII
        for(int i = 0; i < bits.length;i++){
            if(bits[i]){
                codigoAscii += Math.pow(2, expoente);
            }
            expoente--;
        }
        //concatenando cada simbolo na mensagem original
        this.mensagem += (char)codigoAscii;
        //esse retorno precisa ser pensado... será que o dado sempre chega sem ruído???
        return true;
    }
    
    public boolean decoficarDadoCRC(boolean bits[]){
        boolean[] crcReceptor = CRC.gerarCRC(bits);
        
        //Verificandose o CRC do dado recebido é igual a 0000, para conferir a integridade.
        for(int posCRC = 0;posCRC < crcReceptor.length - 1;posCRC++){
            if(crcReceptor[posCRC]){
                //System.out.println("Erro");
                return false;
            }
        }
        
        
        return true;
    }
    
    
    //recebe os dados do transmissor
    public boolean receberDadoBits(boolean bits[]){
        //Verificando se o dado está íntegro.
        if(this.decoficarDadoCRC(bits)){
            //Separando o dado do código CRC, para decodificar a letra para linguagem natural.
            boolean[] letra = new boolean[8];
            System.arraycopy(bits, 0, letra, 0, letra.length);   
            this.decodificarDado(letra);
            
            return true;
        }else{
            
            return false;
        }
        
        //Não terá sucesso sempre, pois o dado pode vir com ruído.
    }
}
