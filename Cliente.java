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
public class Cliente {
    
    private double tempoDeAtendimento;
    private int tempoNoSistema;

    public void setTempoDeAtendimento(double tempoDeAtendimento) {
        this.tempoDeAtendimento = tempoDeAtendimento;
        tempoNoSistema = 0;
    }
    private String prioridade;

    public Cliente(double tempoDeAtendimento, String prioridade){
        this.tempoDeAtendimento = tempoDeAtendimento;
        this.prioridade = prioridade;
    }
    
    public double getTempoDeAtendimento() {
        return tempoDeAtendimento;
    }

    public String getPrioridade() {
        return prioridade;
    }
    
    public void maisTempo(){
        tempoNoSistema++;
    }
    
    public int getTempoNoSistema(){
        return tempoNoSistema;
    }
}
