package jogo;

import physics.Behavior;
import physics.Boid;
import physics.DNA;
import physics.Eye;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

public abstract class GameObject extends Boid implements IObjects{
    protected float energy;
    protected PImage emoji;

    protected GameObject(GameObject o, boolean mutate, PApplet p, SubPlot plt){
        super(o.pos, o.mass, o.radius, o.color, plt, p);
        for (Behavior b : o.behaviors)
            this.addBehavior(b);
        if (o.eye != null)
            eye = new Eye(this, o.eye);
        dna = new DNA(o.dna, mutate);
    }

    protected GameObject(PVector pos, float mass, float radius, int color, SubPlot plt, PApplet p) {
        super(pos, mass, radius, color, plt, p);
    }

    /*
    @Override
    public void energy_consuption(float dt, GameTerrain terrain) {
        GamePatch patch = (GamePatch) terrain.world2Cell(pos.x, pos.y);
        if (patch.getEstado() == GameConstants.PatchType.OBSTACLES.ordinal()){
            energy -= 100f;
        }
    }
     */

    public GamePatch getPatch(GameTerrain terrain){
        return (GamePatch) terrain.world2Cell(pos.x, pos.y);
    }

    public GameObject reproduce(PApplet p, SubPlot plt) {
        GameObject o = new Rocks(this, false, p, plt);
        if (this instanceof Scissors)
            o = new Scissors(this, false, p , plt);
        else if (this instanceof Papers)
            o = new Papers(this, false, p, plt);
        else if (this instanceof  Rocks)
            o = new Rocks(this, false, p, plt);
        else
            System.out.println("AVISO: Objeto" + o.getClass() + " não pode ser reproduzido!");
        return o;
    }

    @Override
    public boolean die() {
        return (energy < 0);
    }

    @Override
    public void display(PApplet p, SubPlot plt) {
        p.pushMatrix();
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.translate(pp[0], pp[1]);
        p.rotate(-vel.heading());

        if (emoji != null) {
            float scaleFactor = .1f;
            p.scale(scaleFactor);
            p.imageMode(PApplet.CENTER);
            p.image(emoji, 0, 0);
        } else {
            System.out.println("AVISO: Imagens não carregadas!");
            float scaleFactor = .5f;
            p.scale(scaleFactor);
            super.display(p, plt);
        }
        p.popMatrix();
    }

}
