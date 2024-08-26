package kr.ac.jbnu.se.tetris.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wallpapers extends JFrame {

    private transient BufferedImage image = null;
    
    private JLayeredPane layeredPane = new JLayeredPane();
    
    public Wallpapers(String image, int x, int y) {
        drawBackGround(image, x, y);
    }

    public JLayeredPane getPane(){
        return layeredPane;
    }

    private void drawBackGround(String imagePath, int x, int y) { // LayerdPane에 이미지를 덮어씌우는 메소드
        layeredPane.setSize(x, y);
        layeredPane.setLayout(null);

        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        BackG background = new BackG();
        background.setSize(x, y);
        layeredPane.add(background);
    }

    class BackG extends JPanel { // Panel에 이미지를 나타내기 위해서
        @Override
        public void paint(Graphics g){
            g.drawImage(image, 0, 0, null);
        }
    }
}
