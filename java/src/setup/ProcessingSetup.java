package setup;

import jogo.GameApp;
import jogo.GameGUI;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet {

    private static IProcessingApp app;
    private int lastUpdateTime;
    private static GameGUI gui;

    @Override
    public void setup() {
        app.setup(this);
        lastUpdateTime = millis();
    }

    @Override
    public void draw() {
        int now = millis();
        float dt = (now - lastUpdateTime) / 1000f;
        lastUpdateTime = now;
        app.draw(this, dt);
    }

    @Override
    public void mousePressed() {
        app.mousePressed(this);
    }

    @Override
    public void keyPressed() {
        app.keyPressed(this);
    }

    @Override
    public void settings() {
        size(800, 600);
    }

    public static void main(String[] args) {
        app = new GameApp();
        gui = new GameGUI(400, 400, app);
        gui.setUpGui();
        gui.setUpButtonListener();
    }

}