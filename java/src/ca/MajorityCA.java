package ca;

import processing.core.PApplet;
import tools.SubPlot;

public class MajorityCA extends AutomatoCelular{

    public MajorityCA(PApplet p, SubPlot plt, int numLinhas, int numColunas, int numEstados, int raioVizinhos) {
        super(p, plt, numLinhas, numColunas, numEstados, raioVizinhos, 0);
    }

    @Override
    protected void createCells(){
        for (int i = 0; i < numLinhas; i++){
            for (int j = 0; j < numColunas; j++)
                celulas[i][j] = new MajorityCell(this, i, j);
        }
        setVizinhosMoore();
    }

    public boolean majorityRule(){
        for (int i = 0; i < numLinhas; i++){
            for (int j = 0; j < numColunas; j++){
                ((MajorityCell) celulas[i][j]).computeHistogram();
            }
        }

        boolean anyChanged = false;
        for (int i = 0; i < numLinhas; i++){
            for (int j = 0; j < numColunas; j++){
                boolean changed =
                        ((MajorityCell) celulas[i][j]).applyMajorityRule();
                if (changed)
                    anyChanged = true;
            }
        }
        return anyChanged;
    }

}
