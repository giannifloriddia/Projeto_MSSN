package jogo;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

public class GameApp implements IProcessingApp {

    private float[] viewport = {0f, 0f, 1f, 1f};
    private SubPlot plt;
    private GameTerrain gt;
    private GamePopulation population;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(GameConstants.WINDOW, viewport, p.width, p.height);
        gt = new GameTerrain(p, plt);
        gt.setCoresEstado(getColors(p));
        gt.initRandomCustom(GameConstants.PATCH_TYPE_PROB);
        for (int i = 0; i < 10; i++)
            gt.majorityRule();
        population = new GamePopulation(p, plt, gt);
    }

    @Override
    public void draw(PApplet p, float dt) {
        gt.regenerate();
        population.update(dt, gt);

        population.objectColisions(p ,plt, gt);
        gt.display(p);
        population.display(p, plt);
        population.printInfo(p);
        population.checkFinalWinner();
    }

    @Override
    public void mousePressed(PApplet p) {

    }

    @Override
    public void keyPressed(PApplet p) {

    }

    private int[] getColors(PApplet p) {
        int[] colors = new int[GameConstants.NSTATES];
        for (int i = 0; i < GameConstants.NSTATES; i++){
            colors[i] = p.color(GameConstants.TERRAIN_COLORS[i][0], GameConstants.TERRAIN_COLORS[i][1], GameConstants.TERRAIN_COLORS[i][2]);
        }
        return colors;
    }

}
