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
    
    public Fila(String prioridade){
        clientes = new ArrayList<>();
        this.prioridade = prioridade;
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
    
    public void imprime(){
        for(Cliente c: clientes){
            System.out.println("cliente " + prioridade);
        }
    }
}
