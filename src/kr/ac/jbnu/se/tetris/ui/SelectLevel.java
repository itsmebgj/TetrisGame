package kr.ac.jbnu.se.tetris.ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import kr.ac.jbnu.se.tetris.ResourcePath;
import kr.ac.jbnu.se.tetris.game.GameConfig;
import kr.ac.jbnu.se.tetris.game.TetrisGameManager;

public class SelectLevel extends JFrame {

    private Select home;

    private int btnWidth = 120;
    private int btnHeight = 35;

    private CustomButton[] level;

    private CustomButton backSelect;

    public SelectLevel(Select home){
        this.home = home;

        level = new CustomButton[GameConfig.LEVEL_NUMBER];
        for(int i = 1; i <= GameConfig.LEVEL_NUMBER; i++){
            level[i - 1] = new CustomButton(new ImageIcon(ResourcePath.BUTTON_LEVEL[i - 1]));
        }

        backSelect = new CustomButton(new ImageIcon(ResourcePath.BUTTON_BACK));

        setFrame();
    }

    private void setFrame() {
        setSize(home.getWidth(), home.getHeight());
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setButton();

        for(int i = 0; i < GameConfig.LEVEL_NUMBER; i++){
            add(level[i]);
        }

        Wallpapers backgrounds = new Wallpapers(ResourcePath.WALL_PAPER, home.getWidth(), home.getHeight());
        
        add(backSelect);
        add(backgrounds.getPane());
    }

    private void setButton() {
        for(int i = 0; i < GameConfig.LEVEL_NUMBER; i++){
            level[i].setBounds(10 + (30 * (i + 1)) + i * btnWidth, home.getHeight() - 150, btnWidth, btnHeight);
        }
        backSelect.setBounds(20, 20, 70, 58);
        addButtonAction();
    }

    private void addButtonAction() {
        for(int i = 0; i < GameConfig.LEVEL_NUMBER; i++) {
            int index = i;
            level[index].addActionListener(e->{
                dispose();

                TetrisGameManager game = new TetrisGameManager(home, index + 1);
                game.run();
            });
        }
        backSelect.addActionListener(e->{
            dispose();
            home.setVisible(true);
        });
    }   
}
