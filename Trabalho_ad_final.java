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
    public static void main(String[] args) { // CHEIO DE GAMBIARRAS!!! //
        
        //numero de guiches
        int numGuiches = 2;
        //numero de filas
        int numFilasComum = 3;
        //numero de filas prioritarias
        int numFilasPrio = 3;
        //numero de 'caixas de atendimento'
        int numCaixaAtendComum = 3;
        //numero de 'caixas de atendimento prioritario'
        int numCaixaAtendPrio = 1;
        
        
        // folga = Xn/m - X(n-1)/m --> deve ser um numero pequeno
        double lambda = 0.028;//=100/60 aprox.
        double semente = 5; // deve ser impar
        double m = 1048576;
        
//        ArrayList<Double> teste = new ArrayList<>();//apagar dps
        ArrayList<Double> temposDeChegada = new ArrayList<>();
        
        
        // primeiro obtemos todos os tempos de chegada para a fila inicial em segundos
        double cont1 = 0;
        while(cont1<=3600){
            semente = congruenteLinear(semente);
            double aleatorioExp = exponencial(semente/m, lambda);
            System.out.printf("O cliente chegou no tempo = %.2f com num = %.2f%n", (cont1), aleatorioExp);
            temposDeChegada.add(cont1);
//            teste.add(cont1);
            cont1+=aleatorioExp;
            
            /*
               num vai ser o iintervalo entre as chegadas.
            */
        }
        
        //cria guiches
        ArrayList<Guiche> guiches = new ArrayList<>();
        for(int i = 0; i<numGuiches; i++){
            guiches.add(new Guiche());
        }
        
        //filas
        ArrayList<Fila> filasComum = new ArrayList<>();
        for(int i = 0; i<numFilasComum; i++){
            filasComum.add(new Fila("comum"));
        }
        
        ArrayList<Fila> filasPrio = new ArrayList<>();
        for(int i = 0; i<numFilasPrio; i++){
            filasPrio.add(new Fila("prioritario"));
        }
        
        
        //recursos --> caixas 
        ArrayList<Caixa> caixasComum = new ArrayList<>();
        for(int i = 0; i<numCaixaAtendComum; i++){
            caixasComum.add(new Caixa());
        }
        
        ArrayList<Caixa> caixasPrio = new ArrayList<>();
        for(int i = 0; i<numCaixaAtendPrio; i++){
            caixasPrio.add(new Caixa());
        }
        
        //execução
        semente = 5;
        double semente1 = 5;
        double semente2 = 5;
        lambda = 0.033;
        double tempo = 0;
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        int numFilaComum = 0; // controla de qual fila vai vir o cliente 
        
        while(tempo<3600){
            
            System.out.println("\n --- tempo " + tempo + " ---\n");
            
            if(temposDeChegada.size()>0){
                if(temposDeChegada.get(0)<=tempo){
                    lambda = 0.033;
                    semente = congruenteLinear(semente);
                    double aleatorioExp = exponencial(semente/m, lambda);

                    if((congruenteLinear(semente)/m) > 0.7){ // sorteio para ver se e prioritario
                        clientes.add(new Cliente(aleatorioExp, "prioritario"));
                        System.out.println("Cliente prioritario add ");
                    }
                    else{
                        clientes.add(new Cliente(aleatorioExp, "comum"));
                        System.out.println("Cliente comum add ");
                    }
                    temposDeChegada.remove(0);
                }
            }
            
            for (Guiche guiche : guiches) { // libera guiches que podem ser liberados e manda para as proximas filas
                if(!guiche.isLivre()){
                    if(guiche.getTempoQueDeveLiberar()<= tempo){
                        guiche.desbloqueia();
                        
                        if(guiche.getCliente().getPrioridade().equals("prioritario")){
                            int filaComMenorNum = 0;
                            for(int i = 0; i<filasPrio.size(); i++){
                                if(filasPrio.get(i).getSize()<filasPrio.get(filaComMenorNum).getSize()){
                                    filaComMenorNum = i;
                                }
                            }
                            
                            filasPrio.get(filaComMenorNum).add(guiche.getCliente());
                        }
                        else{
                            int filaComMenorNum = 0;
                            for(int i = 0; i<filasComum.size(); i++){
                                if(filasComum.get(i).getSize()<filasComum.get(filaComMenorNum).getSize()){
                                    filaComMenorNum = i;
                                }
                            }
                            
                            filasComum.get(filaComMenorNum).add(guiche.getCliente());
                        }
                    }
                }
            }
            for (Guiche guiche : guiches) {   // coloca novos clientes nos guiches
                if(guiche.isLivre() && clientes.size()>0){
                    guiche.bloqueia();
                    guiche.addCliente(clientes.get(0));
                    guiche.setTempoQueDeveLiberar(tempo+clientes.get(0).getTempoDeAtendimento());
                    clientes.remove(0);
                }
            }
            
            //esvazia os caixas...
            for (Caixa caixa : caixasComum) { // libera guiches que podem ser liberados e manda para as proximas filas
                if(!caixa.isLivre()){
                    if(caixa.getTempoQueDeveLiberar()<= tempo){
                        caixa.desbloqueia();
                        caixa.addCliente(null);
                        //esvazia o caixa
                    }
                }
            }
            
            for (Caixa caixa : caixasPrio) { // libera guiches que podem ser liberados e manda para as proximas filas
                if(!caixa.isLivre()){
                    if(caixa.getTempoQueDeveLiberar()<= tempo){
                        caixa.desbloqueia();
                        caixa.addCliente(null);
                        //esvazia o caixa
                    }
                }
            }
            
            // tirar das filas e colocar nos recursos (caixas)
            
            for(Caixa caixa:caixasComum){
                if(caixa.isLivre()){
                    for(Fila fila:filasComum){
                        Cliente c = fila.primeiro();
                        
                        if(c!=null){
                            lambda = 0.003;
                            semente1 = congruenteLinear(semente1);
                            double aleatorioExp = exponencial(semente1/m, lambda);
                            c.setTempoDeAtendimento(aleatorioExp);
                            caixa.bloqueia();
                            caixa.addCliente(c);
                            caixa.setTempoQueDeveLiberar(tempo + c.getTempoDeAtendimento());
                            break;
                        }
                    }
                }
            }
            
            //filas com prioridade
            for(Caixa caixa:caixasPrio){
                if(caixa.isLivre()){
                    for(Fila fila:filasPrio){
                        Cliente c = fila.primeiro();
                        
                        if(c!=null){
                            lambda = 0.003;
                            semente2 = congruenteLinear(semente2);
                            double aleatorioExp = exponencial(semente2/m, lambda);
                            c.setTempoDeAtendimento(aleatorioExp);
                            caixa.bloqueia();
                            caixa.addCliente(c);
                            caixa.setTempoQueDeveLiberar(tempo + c.getTempoDeAtendimento());
                            break;
                        }
                    }
                }
            }
            
            tempo+=1;
            
            // printando o estado do sistema -->>
//            System.out.println("\nEstado fila entrada\n");
//            for(int i = 0; i<temposDeChegada.size();i++){
//                System.out.println(temposDeChegada.get(i));
//            }
//            System.out.println("\n");
            
            System.out.println("\nEstado dos guiches\n");
            for(int i = 0; i<numGuiches; i++){
                System.out.println("Guiche " + i);
                guiches.get(i).imprime();
            }
            System.out.println();
            
            System.out.println("\nEstado das filas prioritarias\n");
            for(int i = 0; i<numFilasPrio; i++){
                System.out.println("Fila " + i);
                filasPrio.get(i).imprime();
                System.out.println("size = " + filasPrio.get(i).getSize());
            }
            
            System.out.println();
            System.out.println("\nEstado das filas comuns\n");
            for(int i = 0; i<numFilasComum; i++){
                System.out.println("Fila " + i);
                filasComum.get(i).imprime();
                System.out.println("size = " + filasComum.get(i).getSize());
            }
            
            System.out.println();
            System.out.println("\nEstado dos caixas\n");
            System.out.println("Comum");
            for(int i = 0; i<numCaixaAtendComum; i++){
                System.out.println("Caixa " + i);
                caixasComum.get(i).imprime();
            }
            
            System.out.println("\nprio");
            for(int i = 0; i<numCaixaAtendPrio; i++){
                System.out.println("Caixa " + i);
                caixasPrio.get(i).imprime();
            }
            
            System.out.println("\n\n\n\n");
            
        }
        
        
        
        
        // daqui pra baixo são testes -->
        
        // verifica media --
//        double cont3 = 0;
//        ArrayList<Double> kk = new ArrayList();
//        for (int i = 0; i<teste.size()/60; i++){
//            int n = verifica(teste, i*60, (i+1)*60);
//            kk.add((double)n);
//            System.out.println("-> " + n);
//        }
//        
//        for(int i = 0; i<kk.size(); i++){
//            cont3+=kk.get(i);
//        }
//        
//        System.out.println("media = " + (cont3/30));
//       
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
        return (1/(-lambda)) * (Math.log(numAleatorio));
        
    }
}
