package ca;

import processing.core.PApplet;

public class Celula {
    protected int linha,coluna,estado;
    private Celula[] vizinhos;
    protected AutomatoCelular ac;

    public Celula(AutomatoCelular ac,int linha, int coluna) {
        this.ac = ac;
        this.linha = linha;
        this.coluna = coluna;
        this.estado = 0;
        this.vizinhos = null;
    }

    public void setVizinhos(Celula[] vizinhos) {
        this.vizinhos = vizinhos;
    }

    public Celula[] getVizinhos() {
        return vizinhos;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEstado() {
        return estado;
    }

    public void display(PApplet p){
        p.pushStyle();
        p.noStroke();
        p.fill(ac.getCoresEstado()[estado]);
        p.rect( ac.xmin + coluna * ac.larguraCelula,ac.ymin + linha * ac.alturaCelula,ac.larguraCelula,ac.alturaCelula);
        p.popStyle();
    }
}
