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
public class Fila {
    
    private ArrayList<Cliente> clientes;
    private String prioridade;
    private int numero;
   

    public Fila(String prioridade, int numero){
        clientes = new ArrayList<>();
        this.prioridade = prioridade;
        this.numero = numero;
        
    }
    
    public int getNumero() {
        return numero;
    }
    
    public void add(Cliente c){
        clientes.add(c);
    }
    
    public Cliente primeiro(){
        Cliente c = null;
        if(clientes.size()>0){
            c = clientes.get(0);
            clientes.remove(0);
        }
        return c;
    }
    
    public String getPrioridade(){
        return prioridade;
    }
    
    public int getSize(){
        return clientes.size();
    }
    
    public double imprime(){
        for(Cliente c: clientes){
            if(c!=null){
                c.maisTempo();
            }
            System.out.println("cliente " + prioridade);
        }
        return (double)clientes.size();
    }
    
    

    

    
}
