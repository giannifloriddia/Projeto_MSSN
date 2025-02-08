package ca;

import tools.Histogram;

public class MajorityCell extends Celula{

    private Histogram hist;

    public MajorityCell(AutomatoCelular ac, int linha, int coluna) {
        super(ac, linha, coluna);
    }

    public void computeHistogram(){
        Celula[] neighbors = getVizinhos();
        int[] data = new int[neighbors.length];
        for (int i = 0; i < neighbors.length; i++)
            data[i] = neighbors[i].getEstado();
        hist = new Histogram(data, ac.numEstados);
    }

    public boolean applyMajorityRule(){
        int mode = hist.getMode(0);
        boolean changed = false;
        if (getEstado() != mode){
            setEstado(mode);
            changed = true;
        }
        return changed;
    }

}
