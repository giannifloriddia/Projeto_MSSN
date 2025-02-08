package physics;

import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import tools.SubPlot;

import jogo.GameConstants;

import java.util.ArrayList;
import java.util.List;

public class Boid extends Body {

    private SubPlot plt;
    private PShape shape;
    protected DNA dna;
    protected Eye eye;
    protected List<Behavior> behaviors;
    protected float phiWander;
    private double[] window;
    private float sumWeights;

    public Boid(PVector pos, float mass, float radius, int color, SubPlot plt, PApplet p) {
        super(pos, new PVector(), mass, radius, color);
        dna = new DNA();
        behaviors = new ArrayList<Behavior>();
        this.plt = plt;
        window = GameConstants.WINDOW;
        setShape(p,plt);
    }

    public void mutateBehaviors(){
        for (Behavior behavior : behaviors){
            if (behavior instanceof AvoidObstacle){
                behavior.weight += DNA.random(-0.5f, 0.5f);
                behavior.weight = Math.max(0, behavior.weight);
            }
        }
        updateSumWeights();
    }

    public void setEye(Eye eye) {
        this.eye = eye;
    }

    public Eye getEye() {
        return eye;
    }

    public void setShape(PApplet p, SubPlot plt) {
        float[] rr = plt.getDimInPixel(radius,radius);
        shape = p.createShape();
        shape.beginShape();
        shape.noStroke();
        shape.fill(color);
        shape.vertex(-rr[0],rr[0]/2);
        shape.vertex(rr[0],0);
        shape.vertex(-rr[0], -rr[0]/2);
        shape.vertex(-rr[0]/2,0);
        shape.endShape(PConstants.CLOSE);
    }

    public void setShape(PApplet p, SubPlot plt,float radius, int color) {
        this.radius = radius;
        this.color = color;
        setShape(p,plt);
    }

    private void updateSumWeights(){
        sumWeights = 0;
        for (Behavior behavior : behaviors) {
            sumWeights += behavior.getWeight();
        }
    }

    public void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
        updateSumWeights();
    }

    public void removeBehavior(Behavior behavior) {
        if (behaviors.contains(behavior)) {
            behaviors.remove(behavior);
        }
        updateSumWeights();
    }

    public void applyBehavior(int i, float dt){
        if (eye != null){
            eye.look();
        }
        Behavior behavior = behaviors.get(i);
        PVector vd = behavior.getDesiredVelocity(this);
        move(dt,vd);
    }

    public void applyBehaviors(float dt){
        if(eye != null){
            eye.look();
        }

        PVector vd = new PVector();
        for (Behavior behavior : behaviors) {
            PVector vdd = behavior.getDesiredVelocity(this);
            vdd.mult(behavior.getWeight()/sumWeights);
            vd.add(vdd);
        }
        move(dt,vd);
    }

    private void move(float dt, PVector vd) {
        vd.normalize().mult(dna.maxSpeed);
        PVector fs = PVector.sub(vd,vel);
        applyForce(fs.limit(dna.maxForce));
        super.move(dt);
        if (pos.x < window[0]){
            pos.x += (float) (window[1] - window[0]);
        }
        if (pos.y < window[2]){
            pos.y += (float) (window[3] - window[2]);
        }
        if (pos.x >= window[1]){
            pos.x -= (float) (window[1] - window[0]);
        }
        if (pos.y >= window[3]){
            pos.y -= (float) (window[3] - window[2]);
        }
    }

    @Override
    public void display(PApplet p, SubPlot plt) {
        p.pushMatrix();
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.translate(pp[0], pp[1]);
        p.rotate(-vel.heading());
        p.shape(shape);
        p.popMatrix();
    }

    public void updateSpeed(int i){
        PVector velAtual = vel;
        if (i == 1){
            while (vel.mag()<10) {
                this.vel = vel.mult(1.3f);

            }
            vel = velAtual;
        }
        if (i == 2){
            while (vel.mag() > 1) {
                this.vel = vel.div(1.3f);
            }
            vel = velAtual;
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    public Object getDNA() {
        return dna;
    }

    public DNA getDna(){
        return dna;
    }
}
