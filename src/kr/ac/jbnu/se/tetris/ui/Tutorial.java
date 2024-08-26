package kr.ac.jbnu.se.tetris.ui;
import javax.swing.JFrame;

import kr.ac.jbnu.se.tetris.ResourcePath;

public class Tutorial extends JFrame {
    
    public Tutorial() {
        setFrame();
    }

    private void setFrame() {
        Wallpapers backGround = new Wallpapers(ResourcePath.TUTORIAL, 299, 537);
        add(backGround.getPane());
        setSize(299, 537);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}