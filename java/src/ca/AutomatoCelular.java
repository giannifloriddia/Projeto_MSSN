package ca;

import processing.core.PApplet;
import processing.core.PVector;
import tools.CustomRandomGenerator;
import tools.SubPlot;

import java.util.Random;

public class AutomatoCelular {
    protected int numLinhas,numColunas,numEstados,raioVizinhos;
    protected Celula[][] celulas;
    private int[] cores;
    private int probabilidade;
    protected float larguraCelula, alturaCelula; //pixels
    protected float xmin, ymin;
    private SubPlot plt;


    public AutomatoCelular(PApplet p, SubPlot plt, int numLinhas , int numColunas , int numEstados , int raioVizinhos, int probabilidade){
        this.numLinhas = numLinhas;
        this.numColunas = numColunas;
        this.numEstados = numEstados;
        this.raioVizinhos = raioVizinhos;
        this.probabilidade = probabilidade;
        celulas = new Celula[numLinhas][numColunas];
        cores = new int[numEstados];
        float[] bb = plt.getBoundingBox();
        xmin = bb[0];
        ymin = bb[1];
        larguraCelula = bb[2]/numColunas;
        alturaCelula = bb[3]/numLinhas;
        this.plt = plt;
        createCells();
        setCoresEstado(p);
    }

    public void updateCelulas(PApplet p){

        int[][] novosEstados = new int[numLinhas][numColunas];

        for (int i = 0; i < numLinhas; i++){
            for (int j = 0; j < numColunas; j++){
                Celula cell = celulas[i][j];
                int nVizinhos = nVizinhosVivos(cell);

                if (cell.getEstado() == 1) {
                    if (nVizinhos < 2 || nVizinhos > 3) {
                        novosEstados[i][j] = 0;
                    } else {
                        novosEstados[i][j] = 1;
                    }
                } else {
                    if (nVizinhos == 3) {
                        novosEstados[i][j] = 1;

                    } else {
                        novosEstados[i][j] = 0;
                    }
                }
            }
        }

        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                celulas[i][j].setEstado(novosEstados[i][j]);
            }
        }

    }

    private int nVizinhosVivos(Celula cell){

        Celula[] vizinhos = cell.getVizinhos();
        int contador = 0;

        for (int i = 0; i < vizinhos.length; i++){

            if (vizinhos[i] != cell && vizinhos[i].getEstado() == 1) //pq conta a própria célula como vizinha
                contador++;

        }

        return contador;

    }

    protected void createCells(){
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                celulas[i][j] = new Celula(this,i,j);
            }
        }
        setVizinhosMoore();
    }

    public void setCoresEstado(PApplet p){
        for (int i = 0; i < numEstados; i++) {
            cores[i] = p.color(p.random(255), p.random(255), p.random(255));
        }
        //cores[0] = p.color(50);
        //cores[1] = p.color(0, 100, 50);
    }

    public void setCoresEstado(int[] colors){
        this.cores = colors;
    }

    public int[] getCoresEstado(){
        return cores;
    }

    public void initRandom(){
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                celulas[i][j].setEstado((int)(numEstados*Math.random()));
                //celulas[i][j].setEstado(probabilidade());
            }
        }
        //placeGlider(celulas);
    }

    public void initRandomCustom(double[] pmf){
        CustomRandomGenerator crg = new CustomRandomGenerator(pmf);
        for (int i = 0; i < numLinhas; i++){
            for (int j = 0; j < numColunas; j++){
                celulas[i][j].setEstado(crg.getRandomClass());
            }
        }
    }

    private void placeGlider(Celula[][] celulas) {
        int i = 9;
        int j = 9;

        // Glider pattern
        celulas[i][j + 1].setEstado(1);
        celulas[i + 1][j + 2].setEstado(1);
        celulas[i + 2][j].setEstado(1);
        celulas[i + 2][j + 1].setEstado(1);
        celulas[i + 2][j + 2].setEstado(1);
    }

    private int probabilidade(){

        Random rdm = new Random();

        if (rdm.nextInt(100) < probabilidade)
            return 0;
        else
            return 1;

    }

    public Celula world2Cell(double x, double y){
        float[] xy = plt.getPixelCoord(x, y);
        return pixel2Celula(xy[0], xy[1]);
    }

    public Celula pixel2Celula(float x, float y){
        int linha = (int)((y-ymin)/alturaCelula);
        int coluna = (int)((x-xmin)/alturaCelula);
        if(linha >= numLinhas)
            linha = numLinhas-1;
        if(coluna >= numColunas)
            coluna = numColunas-1;
        if (linha < 0)
            linha = 0;
        if(coluna < 0)
            coluna = 0;
        return celulas[linha][coluna];
    }

    protected void setVizinhosMoore(){
        int numVizinhos = (2*raioVizinhos+1)*(2*raioVizinhos+1);
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                Celula[] vizinho = new Celula[numVizinhos];
                int n = 0;
                for (int ii = -raioVizinhos; ii <= raioVizinhos; ii++) {
                    int linha = (i + ii + numLinhas) % numLinhas;
                    for (int jj = -raioVizinhos; jj <= raioVizinhos; jj++) {
                        int coluna = (j + jj + numColunas) % numColunas;
                        vizinho[n++] = celulas[linha][coluna];

                    }
                }
                celulas[i][j].setVizinhos(vizinho);
            }
        }
    }

    public PVector getCenterCell(int row, int col){
        float x = (col + 0.5f) * larguraCelula;
        float y = (row + 0.5f) * alturaCelula;
        double[] w = plt.getWorldCoord(x, y);
        return new PVector((float) w[0], (float) w[1]);
    }

    public void display(PApplet p){
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                celulas[i][j].display(p);
            }
        }
    }
}
