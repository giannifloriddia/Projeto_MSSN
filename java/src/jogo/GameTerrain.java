package jogo;

import ca.MajorityCA;
import physics.Body;
import processing.core.PApplet;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class GameTerrain extends MajorityCA {
    public GameTerrain(PApplet p, SubPlot plt) {
        super(p, plt, GameConstants.NROWS, GameConstants.NCOLS,GameConstants.NSTATES,GameConstants.radius);
    }

    @Override
    protected void createCells() {

        int minRT = (int) (GameConstants.REGENERATION_TIME[0]*1000);
        int maxRT = (int) (GameConstants.REGENERATION_TIME[1]*1000);

        for (int i = 0; i < GameConstants.NROWS; i++){
            for (int j = 0; j < GameConstants.NCOLS; j++){
                int timeToGrow = (int) (minRT + (maxRT-minRT)*Math.random());
                celulas[i][j] = new GamePatch(this, i, j, timeToGrow);
            }
        }
        setVizinhosMoore();
    }

    protected void regenerate(){
        for (int i = 0; i < GameConstants.NROWS; i++){
            for (int j = 0; j < GameConstants.NCOLS; j++)
                ((GamePatch)celulas[i][j]).regenerate();
        }
    }

    public List<Body> getObstacles(GameObject objeto) {
        List<Body> bodies = new ArrayList<Body>();
        for (int i = 0; i < GameConstants.NROWS; i++){
            for (int j = 0; j < GameConstants.NCOLS; j++) {
                if (objeto instanceof Scissors  || objeto instanceof Rocks){
                    if (celulas[i][j].getEstado() == GameConstants.PatchType.OBSTACLES.ordinal() || celulas[i][j].getEstado() == GameConstants.PatchType.WATER.ordinal()){
                        Body b = new Body(this.getCenterCell(i, j));
                        bodies.add(b);
                    }
                } else {
                    if (celulas[i][j].getEstado() == GameConstants.PatchType.OBSTACLES.ordinal()){
                        Body b = new Body(this.getCenterCell(i, j));
                        bodies.add(b);
                    }
                }
            }
        }
        return bodies;
    }
}
