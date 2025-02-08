package jogo;

import physics.AvoidObstacle;
import physics.Body;
import physics.Eye;
import physics.Wander;
import processing.core.PApplet;
import processing.core.PVector;
import tools.PopulationLogger;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class GamePopulation{

    private List<GameObject> allObjects;
    private double[] window;
    private boolean mutate = true;
    private List<Body> obstaclesScissors,obstaclesRocks,obstaclesPapers;
    private int count;
    public PopulationLogger logger;

    public GamePopulation(PApplet p, SubPlot plt, GameTerrain terrain){

        window = plt.getWindow();
        allObjects = new ArrayList<GameObject>();
        this.count = 0;

        logger = new PopulationLogger();

        Scissors exempleScissor = new Scissors(new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[2], (float) window[3])), GameConstants.OBJECT_MASS, GameConstants.OBJECT_SIZE, p.color(GameConstants.SCIZOR_COLOR[0], GameConstants.SCIZOR_COLOR[1], GameConstants.SCIZOR_COLOR[2]), plt, p);
        Rocks exempleRocks = new Rocks(new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[2], (float) window[3])), GameConstants.OBJECT_MASS, GameConstants.OBJECT_SIZE, p.color(GameConstants.ROCK_COLOR[0], GameConstants.ROCK_COLOR[1], GameConstants.ROCK_COLOR[2]), plt, p);
        Papers exemplePapers = new Papers(new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[2], (float) window[3])), GameConstants.OBJECT_MASS, GameConstants.OBJECT_SIZE, p.color(GameConstants.PAPER_COLOR[0], GameConstants.PAPER_COLOR[1], GameConstants.PAPER_COLOR[2]), plt, p);

        obstaclesScissors = terrain.getObstacles(exempleScissor);
        obstaclesRocks = terrain.getObstacles(exempleRocks);
        obstaclesPapers = terrain.getObstacles(exemplePapers);

        for (int i = 0; i < GameConstants.INIT_EACH_POPULATION; i++){
            PVector pos = new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[2], (float) window[3]));
            GamePatch patch = (GamePatch) terrain.world2Cell(pos.x, pos.y);
            while (patch.getEstado() == GameConstants.PatchType.WATER.ordinal() || patch.getEstado() == GameConstants.PatchType.OBSTACLES.ordinal() || patch.getEstado() == GameConstants.PatchType.ROCKS.ordinal()){
                pos = new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[2], (float) window[3]));
                patch = (GamePatch) terrain.world2Cell(pos.x, pos.y);
            }
            int colorScissor = p.color(GameConstants.SCIZOR_COLOR[0], GameConstants.SCIZOR_COLOR[1], GameConstants.SCIZOR_COLOR[2]);
            GameObject tesoura = new Scissors(pos, GameConstants.OBJECT_MASS, GameConstants.OBJECT_SIZE, colorScissor, plt, p);
            tesoura.addBehavior(new Wander(1));
            tesoura.addBehavior(new AvoidObstacle(50));
            Eye eye = new Eye(tesoura, obstaclesScissors);
            tesoura.setEye(eye);
            allObjects.add(tesoura);
        }
        for (int i = 0; i < GameConstants.INIT_EACH_POPULATION; i++){
            PVector pos = new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[2], (float) window[3]));
            GamePatch patch = (GamePatch) terrain.world2Cell(pos.x, pos.y);
            while (patch.getEstado() == GameConstants.PatchType.OBSTACLES.ordinal() || patch.getEstado() == GameConstants.PatchType.WATER.ordinal() || patch.getEstado() == GameConstants.PatchType.GRASS.ordinal()){
                pos = new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[2], (float) window[3]));
                patch = (GamePatch) terrain.world2Cell(pos.x, pos.y);
            }
            int colorRock = p.color(GameConstants.ROCK_COLOR[0], GameConstants.ROCK_COLOR[1], GameConstants.ROCK_COLOR[2]);
            GameObject pedra = new Rocks(pos, GameConstants.OBJECT_MASS, GameConstants.OBJECT_SIZE, colorRock, plt, p);
            pedra.addBehavior(new Wander(1));
            pedra.addBehavior(new AvoidObstacle(50));
            Eye eye = new Eye(pedra, obstaclesRocks);
            pedra.setEye(eye);
            allObjects.add(pedra);
        }
        for (int i = 0; i < GameConstants.INIT_EACH_POPULATION; i++){
            PVector pos = new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[2], (float) window[3]));
            GamePatch patch = (GamePatch) terrain.world2Cell(pos.x, pos.y);
            while (patch.getEstado() == GameConstants.PatchType.OBSTACLES.ordinal() || patch.getEstado() == GameConstants.PatchType.ROCKS.ordinal() || patch.getEstado() == GameConstants.PatchType.GRASS.ordinal()){
                pos = new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[2], (float) window[3]));
                patch = (GamePatch) terrain.world2Cell(pos.x, pos.y);
            }
            int colorPaper = p.color(GameConstants.PAPER_COLOR[0], GameConstants.PAPER_COLOR[1], GameConstants.PAPER_COLOR[2]);
            GameObject papel = new Papers(pos, GameConstants.OBJECT_MASS, GameConstants.OBJECT_SIZE, colorPaper, plt, p);
            papel.addBehavior(new Wander(1));
            papel.addBehavior(new AvoidObstacle(50));
            Eye eye = new Eye(papel, obstaclesPapers);
            papel.setEye(eye);
            allObjects.add(papel);
        }

    }

    public void update(float dt, GameTerrain terrain){
        logger.logPopulation(getNumRock(), getNumPaper(), getNumScizor());
        move(terrain, dt);
        eat(terrain);
        if (GameConstants.DAMAGE_ON)
            energy_consumption(dt, terrain);
        die();
    }

    private void move(GameTerrain terrain, float dt){
        for(GameObject o : allObjects)
            o.applyBehaviors(dt);
    }

    private void eat(GameTerrain terrain){
        for (GameObject o : allObjects)
            if (o instanceof Scissors)
                o.eat(terrain);
    }

    private void energy_consumption(float dt, GameTerrain terrain){
        for (GameObject o : allObjects)
            o.energy_consuption(dt, terrain);
    }

    private void die(){
        for (int i = allObjects.size()-1; i>= 0; i--){
            GameObject o = allObjects.get(i);
            if (o.die()){
                allObjects.remove(o);
            }
        }
    }

    public void display(PApplet p, SubPlot plt){
        for (GameObject o : allObjects)
            o.display(p, plt);
    }

    public int getNumObjects() {
        return allObjects.size();
    }

    public float getMeanMaxSpeed(){
        float sum = 0;
        for (GameObject o : allObjects)
            sum += o.getDna().maxSpeed;
        return sum/allObjects.size();
    }

    public float getStdMaxSpeed(){
        float mean = getMeanMaxSpeed();
        float sum = 0;
        for (GameObject o : allObjects)
            sum += Math.pow(o.getDna().maxSpeed - mean, 2);
        return (float) Math.sqrt(sum/allObjects.size());
    }

    public float[] getMeanWeights(){
        float[] sums = new float[2];
        for (GameObject o : allObjects){
            sums[0] += o.getBehaviors().get(0).getWeight();
            sums[1] += o.getBehaviors().get(1).getWeight();
        }
        sums[0] /= allObjects.size();
        sums[1] /= allObjects.size();

        return sums;
    }

    public void objectColisions(PApplet p, SubPlot plt, GameTerrain terrain){
        GameObject wins;
        if (allObjects.size() < GameConstants.MAX_NUM_OBJECTS){
            for (int i = 0; i < allObjects.size(); i++){
                GameObject o1 = allObjects.get(i);
                for (int j = i+1; j< allObjects.size(); j++){
                    GameObject o2 = allObjects.get(j);
                    if (o1.getPatch(terrain)==o2.getPatch(terrain)){
                        wins = winner(o1,o2);
                        if (wins == o1){
                            allObjects.remove(o2);
                            allObjects.add(wins.reproduce(p, plt));
                        } else if (wins == o2) {
                            allObjects.remove(o1);
                            allObjects.add(wins.reproduce(p, plt));
                        }
                        break;
                    }
                }
            }
        }
    }

    private GameObject winner(GameObject o1, GameObject o2){
        if (o1 instanceof Papers){
            if(o2 instanceof Scissors){
                return o2;
            } else if (o2 instanceof Rocks) {
                return o1;
            }
        } else if (o1 instanceof Scissors) {
            if(o2 instanceof Papers){
                return o1;
            } else if (o2 instanceof Rocks) {
                return o2;
            }
        } else {
            if(o2 instanceof Scissors){
                return o1;
            } else if (o2 instanceof Papers) {
                return o2;
            }
        }
        return null;
    }

    public GameObject checkFinalWinner() {
        if (allObjects.isEmpty()) {
            return null;
        }

        GameObject firstObject = allObjects.get(0);
        for (GameObject obj : allObjects) {
            if (!obj.getClass().equals(firstObject.getClass())) {
                return null;
            }
        }

        return firstObject;
    }

    public int getNumRock(){
        int num = 0;
        for (GameObject o : allObjects){
            if (o instanceof Rocks)
                num++;
        }
        return num;
    }

    public int getNumScizor(){
        int num = 0;
        for (GameObject o : allObjects){
            if (o instanceof Scissors)
                num++;
        }
        return num;
    }

    public int getNumPaper(){
        int num = 0;
        for (GameObject o : allObjects){
            if (o instanceof Papers)
                num++;
        }
        return num;
    }

    public void printInfo(PApplet p){

        if (checkFinalWinner() == null && count == 0) {
            System.out.println("Numero de objetos: " + getNumObjects());
            System.out.println("Numero de objetos de papel: " + getNumPaper());
            System.out.println("Numero de objetos de pedra: " + getNumRock());
            System.out.println("Numero de objetos de tesoura: " + getNumScizor());
            System.out.println("-------------------------------------------------");
        } else if (count == 0) {
            System.out.println("Vencedor final: " + checkPlayerWinner() + " (" + checkFinalWinner().getClass().getSimpleName() + ")");
            logger.exportToCSV("population_log.csv");
            logger.printLog();
            count++;
        } else {
            alertWinner(checkPlayerWinner(), p);
        }

    }

    private String checkPlayerWinner(){
        String vencedor = "";
        if (checkFinalWinner() instanceof Papers)
            vencedor = "Papel";
        else if (checkFinalWinner() instanceof Rocks)
            vencedor = "Pedra";
        else if (checkFinalWinner() instanceof Scissors)
            vencedor = "Tesoura";

        if (vencedor.equals(GameConstants.Player1))
            return "Player1";
        else if (vencedor.equals(GameConstants.Player2))
            return "Player2";
        else
            return "Empate";
    }

    public void alertWinner(String vencedor, PApplet p){
        p.pushStyle();
        p.fill(255.0F, 0.0F, 0.0F);
        p.textSize(24.0F);
        p.textAlign(3, 3);
        p.text("Vencedor final: " + checkPlayerWinner() + " (" + checkFinalWinner().getClass().getSimpleName() + ")", (float)p.width / 2.0F, (float)p.height / 2.0F);
        p.popStyle();
    }

}
