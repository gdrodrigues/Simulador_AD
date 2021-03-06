/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_ad_final;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Matheus
 */
public class Trabalho_ad_final {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { // CHEIO DE GAMBIARRAS!!! //
        Scanner s = new Scanner(System.in);
        
        //numero de guiches
        int numGuiches;
        //numero de filas
        int numFilasComum;
        //numero de filas prioritarias
        int numFilasPrio;
        //numero de 'caixas de atendimento'
        int numCaixaAtendComum;
        //numero de 'caixas de atendimento prioritario'
        int numCaixaAtendPrio;
        
        int limiteFila;
        
        int capacidadeGuiches;
        
        int capacidadeCaixas;
        
        int taxaChegada;
        
        System.out.println("Usar configurações padrão? s/n");
        String opcao = s.nextLine();
        
        if(!opcao.equals("s")){
            
            System.out.println("Digite o numero de pessoas que chegam por hora");
            taxaChegada = s.nextInt();
            
            System.out.println("Digite o tamanho de todas as filas");
            limiteFila = s.nextInt();
            
            System.out.println("Digite quantas pessoas um guiche atende por hora");
            capacidadeGuiches = s.nextInt();
            
            System.out.println("Digite quantas pessoas um caixa atende por hora");
            capacidadeCaixas = s.nextInt();
            
            System.out.println("Digite o numero de guiches");
            numGuiches = s.nextInt();
            
            System.out.println("Digite o numero de filas comuns");
            numFilasComum = s.nextInt();
            
            System.out.println("Digite o numero de filas prioritarias");
            numFilasPrio = s.nextInt();
            
            System.out.println("Digite o numero de caixas comuns");
            numCaixaAtendComum = s.nextInt();
            
            System.out.println("Digite o numero de caixas prioritarios");
            numCaixaAtendPrio = s.nextInt();
        }
        else{
            limiteFila = 15;
            taxaChegada = 32727; // 32727
            capacidadeGuiches = 40000;
            capacidadeCaixas = 40000;
            
            numGuiches = 3; //3
            numFilasComum = 3; //3
            numFilasPrio = 2; //2
            numCaixaAtendComum = 3; //3
            numCaixaAtendPrio = 2; //2
        }
        
        double lambda = taxaChegada/(double)3600;//=100/3600 aprox. 0,028 
        //lamba primeiro usado para calcular as chegadas
        //depois ele é usado para calcular o tempo de atendimento nos guiches
        double mi = capacidadeCaixas/(double)3600;//12/3600 = aprox. 0,003
        //mi é usado para calcular os tempos de atendimento nos caixas
        
        
//        System.out.println(((double)((double)120/(double)3600)) + "-------------------");
        double semente = 5; // deve ser impar
        double m = 1048576;
        
        ArrayList<Double> temposDeChegada = new ArrayList<>();
        
        // primeiro obtemos todos os tempos de chegada para a fila inicial em segundos
        double cont1 = 0;
        int contador = 0;
        while(cont1<=3600){
            semente = congruenteLinear(semente);
            double aleatorioExp = exponencial(semente/m, lambda);
            System.out.printf("O cliente %d chegou no tempo = %.2f com intervalo = %.2f%n", contador,(cont1), aleatorioExp);
            temposDeChegada.add(cont1);
            cont1+=aleatorioExp;
            contador++;
            
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
            filasComum.add(new Fila("comum", i));
        }
        
        ArrayList<Fila> filasPrio = new ArrayList<>();
        for(int i = 0; i<numFilasPrio; i++){
            filasPrio.add(new Fila("prioritario", i));
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
        lambda = capacidadeGuiches/(double)3600;
        semente = 5;
        double semente1 = 5;
        double semente2 = 5;
        
        double tempo = 0;
        ArrayList<Cliente> clientes = new ArrayList<>(); // fila do guiche
        
        int numFilaComum = -1; // controla de qual fila vai vir o cliente 
        int numFilaPrio = -1;
        
        int cont_terminados = 0;
        
        //as duas variaveis abaixo servem
        int qtdPacotesDescartadosGuiches = 0;
        int qtdClientesDescartadosCaixas = 0;
        
        // o arraylist abaixo é usado para guardar o tempo de cada usuário no sistema
        //e depois fazer uma media disso
        ArrayList<Integer> temposClientesNoSistema = new ArrayList<>();
        
        
        //variavel para contar o numero de elementos no sistema
        double numElementosNoSistema = 0;
        while(tempo<3600){
            
            System.out.println("\n --- tempo " + tempo + " ---\n");
            
            if(temposDeChegada.size()>0){
                while(temposDeChegada.get(0)<=tempo){
                    semente = congruenteLinear(semente);
                    double aleatorioExp = exponencial(semente/m, lambda);
                    
                    if(clientes.size()<limiteFila){
                        if((congruenteLinear(semente)/m) > 0.7){ // sorteio para ver se e prioritario
                            clientes.add(new Cliente(aleatorioExp, "prioritario"));
                            System.out.println("Cliente prioritario add ");
                        }
                        else{
                            clientes.add(new Cliente(aleatorioExp, "comum"));
                            System.out.println("Cliente comum add ");
                        }
                    }else{ // pacote descartado 
                        qtdPacotesDescartadosGuiches++;
                    }
                    temposDeChegada.remove(0);
                    if(temposDeChegada.isEmpty()){
                        break;
                    }
                }
            }
            
            for (Guiche guiche : guiches) { // libera guiches que podem ser liberados e manda para as proximas filas
                if(!guiche.isLivre()){
                    guiche.setTempoOcupado(guiche.getTempoOcupado()+1);
                    if(guiche.getTempoQueDeveLiberar()<= tempo){
                        guiche.desbloqueia();
                        
                        if(guiche.getCliente().getPrioridade().equals("prioritario")){
                            int filaComMenorNum = 0;
                            for(int i = 0; i<filasPrio.size(); i++){
                                if(filasPrio.get(i).getSize()<filasPrio.get(filaComMenorNum).getSize()){
                                    filaComMenorNum = i;
                                }
                            }
                            
                            if(filasPrio.get(filaComMenorNum).getSize()<limiteFila){
                                filasPrio.get(filaComMenorNum).add(guiche.getCliente());
                            }
                            else{
                                qtdClientesDescartadosCaixas++;
                            }
                            
                            guiche.setCliente(null);
                          
                        }
                        else{
                            int filaComMenorNum = 0;
                            for(int i = 0; i<filasComum.size(); i++){
                                if(filasComum.get(i).getSize()<filasComum.get(filaComMenorNum).getSize()){
                                    filaComMenorNum = i;
                                }
                            }
                            if(filasComum.get(filaComMenorNum).getSize()<limiteFila){
                                filasComum.get(filaComMenorNum).add(guiche.getCliente());
                            }
                            else{
                                qtdClientesDescartadosCaixas++;
                            }
//                            filasComum.get(filaComMenorNum).add(guiche.getCliente());
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
            for (Caixa caixa : caixasComum) { //
                if(!caixa.isLivre()){
                    caixa.setTempoOcupado(caixa.getTempoOcupado()+1);
                    if(caixa.getTempoQueDeveLiberar()<= tempo){
                        caixa.desbloqueia();
                        temposClientesNoSistema.add(caixa.getCliente().getTempoNoSistema());
                        caixa.addCliente(null);
                        cont_terminados++;
                        //esvazia o caixa
                    }
                }
            }
            
            for (Caixa caixa : caixasPrio) { // 
                if(!caixa.isLivre()){
                    caixa.setTempoOcupado(caixa.getTempoOcupado()+1);
                    if(caixa.getTempoQueDeveLiberar()<= tempo){
                        caixa.desbloqueia();
                        temposClientesNoSistema.add(caixa.getCliente().getTempoNoSistema());
                        caixa.addCliente(null);
                        cont_terminados++;
                        //esvazia o caixa
                    }
                }
            }
            
            // tirar das filas e colocar nos recursos (caixas)
           
            for(Caixa caixa:caixasComum){
                if(caixa.isLivre()){
                    for(Fila fila:filasComum){        
                        if(numFilaComum != fila.getNumero()){
                            Cliente c = fila.primeiro();
                        
                            if(c!=null){
                                if(filasComum.size()>1 && fila.getSize()!=0){
                                    numFilaComum = fila.getNumero();
                                }
                                semente1 = congruenteLinear(semente1);
                                double aleatorioExp = exponencial(semente1/m, mi);
                                c.setTempoDeAtendimento(aleatorioExp);
                                caixa.bloqueia();
                                caixa.addCliente(c);
                                caixa.setTempoQueDeveLiberar(tempo + c.getTempoDeAtendimento());
                                break;
                            }
                        }
                    }
                }
            }
            
            //filas com prioridade
            for(Caixa caixa:caixasPrio){
                if(caixa.isLivre()){
                    for(Fila fila:filasPrio){
                        if(numFilaPrio != fila.getNumero()){
                            Cliente c = fila.primeiro();
                            if(c!=null){
                                if(filasPrio.size()>1){
                                    numFilaPrio = fila.getNumero();
                                }
                                semente2 = congruenteLinear(semente2);
                                double aleatorioExp = exponencial(semente2/m, mi);
                                c.setTempoDeAtendimento(aleatorioExp);
                                caixa.bloqueia();
                                caixa.addCliente(c);
                                caixa.setTempoQueDeveLiberar(tempo + c.getTempoDeAtendimento());
                                break;
                            }
                        }
                    }
                }
            }
            
            tempo+=1;
            
            // asd funcoes abaixo fazem a impressão e controle do estado do sistema
            
            System.out.println("\nEstado da fila do guiche");
            for(int i = 0; i<clientes.size(); i++){
                numElementosNoSistema+=1;
                clientes.get(i).maisTempo();
            }

           
            
            System.out.println("\nEstado dos guiches\n");
            for(int i = 0; i<numGuiches; i++){
                System.out.println("Guiche " + i);
                guiches.get(i).imprime();
                if(guiches.get(i).getCliente()!=null){
                    numElementosNoSistema+=1;
                }
            }
            System.out.println();
            
            System.out.println("\nEstado das filas prioritarias\n");
            for(int i = 0; i<numFilasPrio; i++){
                System.out.println("Fila " + i);
                double n = filasPrio.get(i).imprime();
                numElementosNoSistema+=n;
                System.out.println("size = " + filasPrio.get(i).getSize());
            }
            
            System.out.println();
            System.out.println("\nEstado das filas comuns\n");
            for(int i = 0; i<numFilasComum; i++){
                System.out.println("Fila " + i);
                filasComum.get(i).imprime();
                double n = filasComum.get(i).imprime();
                numElementosNoSistema+=n;
                System.out.println("size = " + filasComum.get(i).getSize());
            }
            
            System.out.println();
            System.out.println("\nEstado dos caixas\n");
            System.out.println("Comum");
            for(int i = 0; i<numCaixaAtendComum; i++){
                System.out.println("Caixa " + i);
                caixasComum.get(i).imprime();
                if(caixasComum.get(i).getCliente()!=null){
                    numElementosNoSistema+=1;
                }
            }
            
            System.out.println("\nprio");
            for(int i = 0; i<numCaixaAtendPrio; i++){
                System.out.println("Caixa " + i);
                caixasPrio.get(i).imprime();
                if(caixasPrio.get(i).getCliente()!=null){
                    numElementosNoSistema+=1;
                }
            }
            
            System.out.println("\n\n\n\n");
            
        }// fim while
        
        // estado final do sistema
        
        System.out.println("Numero de pacotes que concluiram a utilizacao do sistema com sucesso = " + cont_terminados);
        System.out.println("perdidos na fila do guiche = " + qtdPacotesDescartadosGuiches 
            + " taxa de descarte = " + ((double)qtdPacotesDescartadosGuiches/taxaChegada));
        
        System.out.println("perdidos nas filas dos caixas = " + qtdClientesDescartadosCaixas
            + " taxa de descarte = " + ((double)qtdClientesDescartadosCaixas/taxaChegada));
        
        System.out.println("\nTempo que os recursos ficaram ocupados");
        
        for(int i = 0; i<guiches.size(); i++){
            System.out.println("Guiche " + i + " tempo ocupado = " + guiches.get(i).getTempoOcupado()
                + " taxa de utilizacao = " + ((double)guiches.get(i).getTempoOcupado()/3600.0));
        }
        System.out.println("\n");
        
        for(int i = 0; i<caixasComum.size(); i++){
            System.out.println("Caixa comum " + i + " tempo ocupado = " + caixasComum.get(i).getTempoOcupado()
                + " taxa de utilizacao = " + ((double)caixasComum.get(i).getTempoOcupado()/3600.0));
        }
        System.out.println("\n");
        
        for(int i = 0; i<caixasPrio.size(); i++){
            System.out.println("Caixa prio " + i + " tempo ocupado = " + caixasPrio.get(i).getTempoOcupado()
                + " taxa de utilizacao = " + ((double)caixasPrio.get(i).getTempoOcupado()/3600.0));
        }
        System.out.println("\n");
        
        System.out.println("Clientes na fila dos guiches = " + clientes.size() );
        
        
        // tempos medios no sistema
        int contTempSys = 0;
        for(Integer i: temposClientesNoSistema){
            contTempSys+=i;
        }
        System.out.println("Tempo medio de uma requisição no sistema = " + (contTempSys/temposClientesNoSistema.size()) + " - " + temposClientesNoSistema.size());
        
        
        //numero de elementos no sistema
        System.out.println("Numero médio de clientes no sistema = " + (numElementosNoSistema/3600.0));
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
    
    public static ArrayList<Double> poisson(){//não esta sendo usada, por enquanto.
        
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
                double aleatorio = congruenteLinear(semente);
                p = p*(aleatorio/m);
                semente = aleatorio;
               
                if(p<exponencial){
                    x = n;
                    tempos.add(x);
                    System.out.println(x*100);
                    break;
                }
                else{
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
