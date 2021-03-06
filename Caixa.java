/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_ad_final;

/**
 *
 * @author Matheus
 */
public class Caixa {
    
    private boolean livre;
    private double tempoQueDeveLiberar;
    private Cliente cliente;
    private int tempoOcupado;
    
    public Caixa(){
        livre = true;
        tempoQueDeveLiberar = 0;
        cliente = null;
        tempoOcupado = 0;
    }
    
    public void bloqueia(){
        livre = false;
    }

    public boolean isLivre() {
        return livre;
    }

    public void desbloqueia(){
        livre = true;
    }
    
    public double getTempoQueDeveLiberar() {
        return tempoQueDeveLiberar;
    }

    public void setTempoQueDeveLiberar(double tempoQueDeveLiberar) {
        this.tempoQueDeveLiberar = tempoQueDeveLiberar;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void addCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void imprime(){
        if(cliente!=null){
            cliente.maisTempo();
        }
        System.out.println("livre = " + livre + " tempo de liberacao " + tempoQueDeveLiberar);
    }
    
    public int getTempoOcupado() {
        return tempoOcupado;
    }

    public void setTempoOcupado(int tempoOcupado) {
        this.tempoOcupado = tempoOcupado;
    }
}
