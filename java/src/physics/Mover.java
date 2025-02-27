package physics;
import processing.core.PVector;

public abstract class Mover {

    protected PVector pos;
    protected PVector vel;
    protected PVector accel;
    protected float mass;
    protected static double G=6.67e-11;

    public Mover(PVector pos , PVector vel, float mass){
        this.pos = pos.copy();
        this.vel = vel;
        this.mass = mass;
        accel = new PVector();
    }

    public void applyForce(PVector force){
        accel.add(PVector.div(force,mass));
    }

    public PVector attraction(Mover m) {
        PVector r = PVector.sub(pos,m.pos);
        float dist = r.mag();
        float strength = (float) ((G*mass*m.mass) / Math.pow(dist,2));
        return r.normalize().mult(strength);
    }

    public void move(float dt){
        vel.add(accel.mult(dt));
        pos.add(PVector.mult(vel,dt));
        accel.mult(0);
    }

    public void setPos(PVector pos){
        this.pos = pos;
    }

    public PVector getPos(){
        return pos;
    }

    public void setVel(PVector vel){
        this.vel = vel;
    }

    public PVector getVel(){
        return vel;
    }
}
