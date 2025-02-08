package jogo;

import ca.AutomatoCelular;
import ca.MajorityCA;
import ca.MajorityCell;

public class GamePatch extends MajorityCell {
    private long eatenTime;
    private int timeToGrow;

    public GamePatch(AutomatoCelular ac, int linha, int coluna, int timeToGrow) {
        super(ac, linha, coluna);
        this.timeToGrow = timeToGrow;
        eatenTime = System.currentTimeMillis();
    }

    public void setFertile(){
        estado = GameConstants.PatchType.FERTILE.ordinal();
        eatenTime = System.currentTimeMillis();
    }

    public void regenerate(){
        if (estado == GameConstants.PatchType.FERTILE.ordinal() && System.currentTimeMillis() > (eatenTime + timeToGrow))
            estado = GameConstants.PatchType.GRASS.ordinal();
    }

}
