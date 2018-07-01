/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_ad_final;

import java.util.ArrayList;

/**
 *
 * @author Matheus
 */
public class Trabalho_ad_final {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { // ******** ESTÁ CHEIO DE GAMBIARRA ***********
        
        // folga = Xn/m - X(n-1)/m --> deve ser um numero pequeno
        double lambda = 1;
        double semente = 5; // deve ser impar
        double m = 1048576;
        
        ArrayList<Double> teste = new ArrayList<>();
        
        double cont1 = 0;
        for (int i = 0; i<50*60; i++){
            semente = congruenteLinear(semente);
            double num = exponencial(semente/m, lambda);
            System.out.printf("O cliente %d chegou no tempo = %.2f%n", i, (cont1));
            teste.add(cont1);
            cont1+=num;
            
        }
        System.out.println(cont1);
        
        double cont3 = 0;
        ArrayList<Double> kk = new ArrayList();
        for (int i = 0; i<teste.size(); i++){
            int n = verifica(teste, i*60, (i+1)*60);
            kk.add((double)n);
            System.out.println("-> " + n);
        }
        
        for(int i = 0; i<kk.size(); i++){
            cont3+=kk.get(i);
        }
        
        System.out.println("media = " + (cont3/50));
        
//        System.out.println("media = " + cont3/50);
//        ArrayList<Double> temposDeChegada = poisson();
//        
//        //chegada dos clientes
//        double cont = 0;
//        for (int i = 0; i<temposDeChegada.size(); i++) {
//            cont+=temposDeChegada.get(i);
//            System.out.println("O cliente " + i + " chegou no tempo " + cont);
//        }
    }
    
    public static int verifica(ArrayList<Double> array, int valor1, int valor2){
        // funcao conta quantos estao entre valor1 e valor2 dentro de array
        int cont = 0;
        for(int i = 0; i<array.size(); i++){
            if(array.get(i)>=valor1 && array.get(i)<valor2){
                cont++;
            }
        }
        return cont;
    }
    
    public static double congruenteLinear(double anterior){
        double a = 17; // a = 4x+1 -> multiplo de 4 somado de + 1 //17
        double c = 43; //  deve ser primo de m //43
        double m = 1048576; // deve ser da forma 2^k 
//        double semente = 5; // deve ser impar //5
        return ((a*anterior)+c)%m;
    }
    
    public static ArrayList<Double> poisson(){
        
        double a = 17; // a = 4x+1 -> multiplo de 4 somado de + 1 //17
        double c = 43; //  deve ser primo de m //43
        double m = 1048576; // deve ser da forma 2^k 
        double semente = 5; // deve ser impar //5
        
        double lambda = 0.5;
        double exponencial = Math.pow(Math.E, -lambda);
        double x;
        
        ArrayList<Double> tempos = new ArrayList();        
        
        for(int i = 0; i<100; i++){
            double n = 0;
            double p = 1;
            
            while(true){
                double aleatorio = congruenteLinear(semente);// trocar em baixo
//                p=p*(Math.random());
                p = p*(aleatorio/m);
//                System.out.println(p);
                semente = aleatorio;
               
                if(p<exponencial){
                    x = n;
                    tempos.add(x);
                    System.out.println(x*100);
                    break;
                }
                else{
//                    System.out.println("entrou aqui ->" + p + " < " + exponencial);
                    n++;
                }
                
            }
        }
        
        return tempos;
    }
    
    public static double exponencial(double numAleatorio, double lambda){
//        double lambda = 1;
        double num = (1/(-lambda)) * (Math.log(numAleatorio));
//        System.out.printf("%.2f%n", (num*100));
        return num;
    }
}
