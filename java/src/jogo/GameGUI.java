package jogo;

import processing.core.PApplet;
import setup.IProcessingApp;
import setup.ProcessingSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class GameGUI extends JPanel{

    private static IProcessingApp app;
    private JFrame frame;
    private ImageIcon icon;

    private int width, height;
    private JButton btnStart;
    private JRadioButton btnSound;
    private JRadioButton btndano;
    private JComboBox comboBoxPly1;
    private JComboBox comboBoxPly2;
    private JTextArea numObjects;
    String[] opcoes = {"Pedra", "Papel", "Tesoura"};

    public GameGUI(int width, int height, IProcessingApp app) {
        frame=new JFrame();
        this.width=width;
        this.height=height;
        this.app=app;
        btnStart=new JButton("Play");
        btnSound=new JRadioButton("Sound");
        btndano=new JRadioButton("Dano do Terreno");
        comboBoxPly1=new JComboBox(opcoes);
        comboBoxPly2=new JComboBox(opcoes);
        numObjects=new JTextArea();
        numObjects.setLineWrap(false);
    }


    public void setUpGui() {
        icon = new ImageIcon("java/imagens/icon.png");
        // Definir a imagem como ícone da janela
        frame.setIconImage(icon.getImage());

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        // Title row
        // Title row
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4; // Span across 4 columns
        frame.add(new JLabel("     Pedra Papel ou Tesoura", JLabel.CENTER), gbc);

        // Adicionar imagem ao lado direito do Player 1
        gbc.gridx = 2; // Posicionar no próximo espaço após Player 1
        gbc.gridy = 0;
        ImageIcon player1Image = new ImageIcon("java/imagens/ImageIcon.png");
        JLabel player1ImageLabel = new JLabel(player1Image);
        frame.add(player1ImageLabel, gbc);

        // Reset gridwidth for next components
        gbc.gridwidth = 1;

        // Player 1 row
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("Player1:"), gbc);

        gbc.gridx = 1;
        frame.add(comboBoxPly1, gbc);

        // Player 2 row
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("Player2:"), gbc);

        gbc.gridx = 1;
        frame.add(comboBoxPly2, gbc);

        // NºObjetos row
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JLabel("NºObjetos:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make the text area expand horizontally
        numObjects.setColumns(2); // Set a preferred width for the text area
        numObjects.setText("15");
        frame.add(numObjects, gbc);
        gbc.fill = GridBagConstraints.NONE; // Reset fill to default

        // Dano do Terreno row
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        frame.add(btndano, gbc);

        // Play button
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridheight = 2; // Span across 2 rows
        frame.add(btnStart, gbc);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void vazio(int espacos){
        for (int i=1; i<=espacos; i++){
            frame.add(new JLabel());
        }
    }

    public void setUpButtonListener(){
        ActionListener buttonPlayListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                GameConstants.INIT_EACH_POPULATION = Integer.parseInt(numObjects.getText());
                GameConstants.DAMAGE_ON = btndano.isSelected();
                GameConstants.Player1 = (String) comboBoxPly1.getSelectedItem();
                GameConstants.Player2 = (String) comboBoxPly2.getSelectedItem();

                app = new GameApp();
                frame.setVisible(false);
                PApplet.main(ProcessingSetup.class);
            }
        };
        ActionListener buttonSoundListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("desligar/ligar som");
            }
        };

        btnStart.addActionListener(buttonPlayListener);
        btnSound.addActionListener(buttonSoundListener);
    }
}