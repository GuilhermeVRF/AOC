/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_transferenciadedados;

/**
 *
 * @author guilh
 */
public class CRC {
    private static final int X = 2;
    
     public static boolean[] gerarCRC(boolean bits[]){
        boolean[] polinomio = new boolean[5];
        boolean[] resto = new boolean[5];
        int indice = 4;
        System.arraycopy(bits, 0, resto, 0, resto.length);
       
        //Verificando se o dado começa por 0 ou 1 para definir o polinomio inicial
        if(resto[0]){
            polinomio = gerarPolinomio();
        }              
        
        while(indice < bits.length){
            
            //Relaizando as operações XOR
            for(int posXOR = 0; posXOR <= 4; posXOR++){
                
                if(resto[posXOR] == true && polinomio[posXOR] == true){
                    resto[posXOR] = false;
                }else{
                    resto[posXOR] = !(resto[posXOR] == false && polinomio[posXOR] == false);
                }                          
            }
            
            //Cortando o 0 do resto
            for(int posCRC = 0, posProximoIndice = 1; posProximoIndice < resto.length;posCRC++, posProximoIndice++){
                resto[posCRC] = resto[posProximoIndice];
            }    
                
            //Adicionando o próximo bit ao resto
            if(indice < 11)
                resto[4] = bits[indice + 1];
            
            indice++;
            
            //Verificando se o resto de fato começa com 0, para saber se será 00000 ou 10101
            if(resto[0] == false){
                for(int pos = 0; pos < polinomio.length; pos++){
                    polinomio[pos] = false;
                }
            }else{
                polinomio = gerarPolinomio();              
            } 
        }
        
        
        return resto;
    }
    
    //Gerando um polinômio com base em G(X) = X^4 + X^2 + X^1
    private static boolean[] gerarPolinomio(){       
        int polinomio = 0;
        
        for(int expoente = 4; expoente >= 0; expoente = expoente - 2){
            int numeroMultiplicado = X;
            
            if(expoente == 0){
                numeroMultiplicado = 1;
            }else{
                for(int mult = expoente; mult > 1; mult--){
                    numeroMultiplicado = numeroMultiplicado * X;
                }
            }
            
            
            polinomio += numeroMultiplicado;
        }
        
        return converterBinario(polinomio);
    }
    
    private static boolean[] converterBinario(int polinomioDecimal){
        boolean[] numeroBinario = new boolean[5];
        int pos = numeroBinario.length - 1;
        
        while(polinomioDecimal != 1){
            numeroBinario[pos] = polinomioDecimal % 2 == 1;
            polinomioDecimal = polinomioDecimal / 2;
            pos--;
        }
        
        numeroBinario[0] = true;
        
        return numeroBinario;
    }
}
