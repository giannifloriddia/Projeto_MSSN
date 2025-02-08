package jogo;
public class GameConstants {
    //Players
    public static String Player1;
    public static String Player2;

    //World
    public final static double[] WINDOW = {0, 10, 0, 10};

    //Terrain
    public final static int NROWS = 45, NCOLS = 60;
    public final static int radius = 2;

    public static enum PatchType{
        WATER, OBSTACLES, FERTILE, GRASS, ROCKS, UNFERTILE
    }

    public final static double[] PATCH_TYPE_PROB = {0.19f, 0.15f, 0f, 0.23f, 0.195f, 0.235f};
    public final static int NSTATES = PatchType.values().length;
    public static int[][] TERRAIN_COLORS = {
            {37, 109, 123}, {0, 0, 0}, {169, 156, 149}, {86, 125, 70}, {128,128,128},{169, 156, 149}
    };
    public final static float[] REGENERATION_TIME = {20f, 60f}; //seconds

    //Game Population
    public static boolean DAMAGE_ON = false;
    public final static float OBJECT_SIZE = .2f;
    public final static float OBJECT_MASS = 1f;
    public static int INIT_EACH_POPULATION;
    public final static float INIT_OBJECT_ENERGY = 500f;
    public final static float ENERGY_TOOK_BIOME = 4f;
    public final static int MAX_NUM_OBJECTS = 250;
    public static int[] ROCK_COLOR = {46, 46, 46};
    public static int[] PAPER_COLOR = {244, 243, 239};
    public static int[] SCIZOR_COLOR = {205, 92, 92};
    /*
        public final static int INIT_ROCK_POPULATION = 25;
    public final static int INIT_PAPER_POPULATION = 25;
    public final static int INIT_SCIZOR_POPULATION = 25;
     */
}
