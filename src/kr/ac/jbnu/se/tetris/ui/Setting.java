package kr.ac.jbnu.se.tetris.ui;

import kr.ac.jbnu.se.tetris.ResourcePath;

import javax.swing.*;

public class Setting extends JFrame {

    private Select home;
    
    private CustomButton musicButton, changekey, backselect;
    private boolean isMusicStarted = true;

    private KeyChange keyChange;

    public Setting(Select home) {
        this.home = home;
        keyChange = new KeyChange();
        setFrame();
    }

    private void setFrame() {
        setSize(home.getWidth(), home.getHeight());
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Wallpapers backgrounds = new Wallpapers(ResourcePath.WALL_PAPER, home.getWidth(), home.getHeight());
        setButton();
        add(musicButton); add(changekey); add(backselect); add(backgrounds.getPane());
    }

    private void addButton() {
        musicButton = new CustomButton(new ImageIcon(ResourcePath.BUTTON_MUSIC_ON));
        changekey = new CustomButton(new ImageIcon(ResourcePath.BUTTON_KEY_CHANGE));
        backselect = new CustomButton(new ImageIcon(ResourcePath.BUTTON_BACK));
    }

    private void setButton() {
        addButton();
        
        musicButton.setBounds(350, 40, 100, 100);
        changekey.setBounds(250, 250, 300, 150);
        backselect.setBounds(20, 20, 70, 58);

        buttonAction();
    }

    private void pauseMusic(){
        isMusicStarted = !isMusicStarted;
        if(isMusicStarted){
            musicButton.setIcon(new ImageIcon(ResourcePath.BUTTON_MUSIC_ON));
            home.getMusic().startMusic();
        }
        else{
            musicButton.setIcon(new ImageIcon(ResourcePath.BUTTON_MUSIC_OFF));
            home.getMusic().stopMusic();
        }
    }

    private void buttonAction() {
        musicButton.addActionListener(e-> pauseMusic() );

        changekey.addActionListener(e-> keyChange.setVisible(true) );

        backselect.addActionListener(e-> {
            setVisible(false);
            home.setVisible(true);
        });
    }
}
