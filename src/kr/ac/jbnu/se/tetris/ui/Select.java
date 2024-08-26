package kr.ac.jbnu.se.tetris.ui;

import javax.swing.*;

import kr.ac.jbnu.se.tetris.ResourcePath;
import kr.ac.jbnu.se.tetris.audio.Music;
import kr.ac.jbnu.se.tetris.game.TetrisGameManager;

public class Select extends JFrame {
    
    private transient Music music;

    private SelectLevel selectLevel;

    private Setting setting;

    private Tutorial tutorial;

    private CustomButton ai;
    private CustomButton versus;
    private CustomButton settingBtn;
    private CustomButton tutorialBtn;

    public Select() {
        setFrame();

        selectLevel = new SelectLevel(this);
        setting = new Setting(this);
        tutorial = new Tutorial();
        
        music = new Music();
        music.startMusic();
    }

    public Music getMusic(){
        return music;
    }

    private void setFrame() {
        int frameWidth = 800;
        int frameHeight = 453;

        setSize(frameWidth, frameHeight);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Wallpapers wallpaper = new Wallpapers(ResourcePath.WALL_PAPER, frameWidth, frameHeight);
        setButton();

        add(ai); add(versus); add(settingBtn); add(tutorialBtn);
        add(wallpaper.getPane());
    }

    private void setButton() {
        int btnWidth = 172;
        int btnHeight = 50;

        int btnY = getHeight() - 150;

        ai = new CustomButton(new ImageIcon(ResourcePath.BUTTON_AI_MODE));
        ai.setBounds(71, btnY, btnWidth, btnHeight);
        
        versus = new CustomButton(new ImageIcon(ResourcePath.BUTTON_2P_MODE));
        versus.setBounds(142 + btnWidth, btnY, btnWidth, btnHeight);

        settingBtn = new CustomButton(new ImageIcon(ResourcePath.BUTTON_SETTING));
        settingBtn.setBounds(213 + btnWidth * 2, btnY, btnWidth, btnHeight);
        
        tutorialBtn = new CustomButton(new ImageIcon(ResourcePath.BUTTON_TUTORIAL));
        tutorialBtn.setBounds(getWidth() - 150, 30, 120, 35);

        buttonAction();
    }

    private void buttonAction() {
        ai.addActionListener(e->{
            setVisible(false);
            selectLevel.setVisible(true);
        });

        versus.addActionListener(e->{
            setVisible(false);

            TetrisGameManager game = new TetrisGameManager(this, 0); // 플레이어 대전인 경우 0 레벨 설정
            game.run();
        });

        settingBtn.addActionListener(e->{
            setVisible(false);
            setting.setVisible(true);
        });

        tutorialBtn.addActionListener(e->
            tutorial.setVisible(true)
        );
    }
}