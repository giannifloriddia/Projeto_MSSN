package jogo;

import processing.core.*;
import tools.SubPlot;

public class Scissors extends GameObject{

    private PApplet parent;
    private SubPlot plt;
    private String path = "E:\\UNIVERSIDADE\\2º ANO\\1º Semestre\\MSSN\\PRJ_ 51945_51959\\PRJ_ 51945_51959\\java\\imagens\\tesoura.png";

    protected Scissors(GameObject o, boolean mutate, PApplet parent, SubPlot plt) {
        super(o, mutate, parent, plt);
        this.parent = parent;
        this.plt = plt;
        energy = GameConstants.INIT_OBJECT_ENERGY;
        this.emoji = parent.loadImage(path);
    }

    protected Scissors(PVector pos, float mass, float radius, int color, SubPlot plt, PApplet parent) {
        super(pos, mass, radius, color, plt, parent);
        this.parent = parent;
        this.plt = plt;
        energy = GameConstants.INIT_OBJECT_ENERGY;
        this.emoji = parent.loadImage(path);
    }

    @Override
    public void eat(GameTerrain terrain) {
        GamePatch patch = (GamePatch) terrain.world2Cell(pos.x, pos.y);
        if (patch.getEstado() == GameConstants.PatchType.GRASS.ordinal()){
            energy += GameConstants.ENERGY_TOOK_BIOME;
            patch.setFertile();
        }
    }

    @Override
    public void energy_consuption(float dt, GameTerrain terrain) {
        GamePatch patch = (GamePatch) terrain.world2Cell(pos.x, pos.y);
        if (patch.getEstado() == GameConstants.PatchType.OBSTACLES.ordinal() || patch.getEstado() == GameConstants.PatchType.WATER.ordinal()){
            energy = -1f;
        } else if (patch.getEstado() == GameConstants.PatchType.ROCKS.ordinal()){
            energy -= 1f;
        }
    }

}
