package jogo;

public interface IObjects {
    //public GameObject reproduce(boolean mutate);
    public void eat(GameTerrain terrain);
    public void energy_consuption(float dt, GameTerrain terrain);
    public boolean die();
}
