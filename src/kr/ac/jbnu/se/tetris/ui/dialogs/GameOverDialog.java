package kr.ac.jbnu.se.tetris.ui.dialogs;

import javax.swing.JDialog;
import javax.swing.JLabel;

import kr.ac.jbnu.se.tetris.game.TetrisGameManager;
import kr.ac.jbnu.se.tetris.ui.CustomButton;
import kr.ac.jbnu.se.tetris.ui.Select;

public class GameOverDialog extends JDialog {

    private TetrisGameManager manager;
    
    private JLabel gameOverText = new JLabel("게임 종료!");

    private CustomButton retryBtn = new CustomButton("재시작");
    
    private CustomButton homeBtn = new CustomButton("메인화면");

    public GameOverDialog(TetrisGameManager ownerFrame) {
        super(ownerFrame, true);
        manager = ownerFrame;
        setUndecorated(true);
        setSize(170, 135);
        setLocationRelativeTo(null);
        setLayout(null);

        setComponentBounds();
        addListener();
        addComponent();
    }

    private void setComponentBounds() {
        gameOverText.setBounds(60, 10, 150, 30);
        retryBtn.setBounds(10, 45, 150, 35);
        homeBtn.setBounds(10, 90, 150, 35);
    }

    private void addListener() {
        retryBtn.addActionListener(e->{
            int level = manager.getLevel();
            Select mainPage = manager.getMainPage();

            TetrisGameManager game = new TetrisGameManager(mainPage, level);
            game.run();
            
            setVisible(false);
            manager.dispose();
        });
        homeBtn.addActionListener(e->{
            Select mainPage = manager.getMainPage();
            mainPage.setVisible(true);

            setVisible(false);
            manager.dispose();            
        });
    }

    private void addComponent() {
        add(gameOverText);
        add(retryBtn);
        add(homeBtn);
    }
}
