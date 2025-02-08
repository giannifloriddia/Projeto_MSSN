package physics;

public class DNA {
    public float maxSpeed;
    public float maxForce;
    public float visionDistance;
    public float visionSafeDistance;
    public float visionAngle;
    public float deltaTPursuit;
    public float radiusArrive;
    public float deltaTWander;
    public float radiusWander;
    public float deltaPhiWander;

    public DNA() {
        //Physics
        maxSpeed = random(1f,2f);
        maxForce = random(4f,7f);
        //Vision
        visionDistance = random(1.5f,2.5f);
        visionSafeDistance = (float) (0.4*visionDistance);
        visionAngle = (float) Math.PI * 0.2f;
        //Pursuit
        deltaTPursuit = random(0.5f,1f);
        //Arrive
        radiusArrive = random(3,5);
        //Wander
        deltaTWander = random(.3f,.6f);
        radiusWander = random(1f,3f);
        deltaPhiWander = (float)Math.PI/8;

    }

    public DNA(DNA dna, boolean mutate){
        maxSpeed = dna.maxSpeed;
        maxForce = dna.maxForce;

        visionDistance = dna.visionDistance;
        visionSafeDistance = dna.visionSafeDistance;
        visionAngle = dna.visionAngle;

        deltaTPursuit = dna.deltaTPursuit;
        radiusArrive = dna.radiusArrive;

        deltaTWander = dna.deltaTWander;
        deltaPhiWander = dna.deltaPhiWander;
        radiusWander = dna.radiusWander;

        if (mutate)
            mutate();
    }

    private void mutate() {
        maxSpeed += random(-1, 1);
        maxSpeed = Math.max(0, maxSpeed);
    }

    public float getMaxSpeed(){
        return maxSpeed;
    }

    public static float random(float min, float max) {
        return (float) (min + (max-min)*Math.random());
    }
}
