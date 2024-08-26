package kr.ac.jbnu.se.tetris.ui.dialogs;

import javax.swing.JDialog;
import javax.swing.JLabel;

import kr.ac.jbnu.se.tetris.game.TetrisGameManager;
import kr.ac.jbnu.se.tetris.ui.CustomButton;
import kr.ac.jbnu.se.tetris.ui.Select;

public class PauseDialog extends JDialog {

    private TetrisGameManager manager;

    private JLabel pauseText = new JLabel("일시 정지");

    private CustomButton resumeBtn = new CustomButton("계속하기");
    
    private CustomButton retryBtn = new CustomButton("재시작");
    
    private CustomButton homeBtn = new CustomButton("메인화면");

    public PauseDialog(TetrisGameManager ownerFrame){
        super(ownerFrame, true);
        manager = ownerFrame;

        setUndecorated(true);
        setSize(170, 175);
        setLocationRelativeTo(null); 
        setLayout(null);

        setComponentBounds();
        addListener();
        addComponent();
    }

    private void setComponentBounds(){
        pauseText.setBounds(60, 5, 150, 30);
        resumeBtn.setBounds(10, 40, 150, 35);
        retryBtn.setBounds(10, 85, 150, 35);
        homeBtn.setBounds(10, 130, 150, 35);
    }

    private void addListener(){
        resumeBtn.addActionListener( e-> manager.pause() );

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
        add(pauseText);
        add(resumeBtn);
        add(retryBtn);
        add(homeBtn);
    }
}
