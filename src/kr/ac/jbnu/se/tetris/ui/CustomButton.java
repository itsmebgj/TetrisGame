package kr.ac.jbnu.se.tetris.ui;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CustomButton extends JButton {

    public CustomButton() {
        super();
    }
    public CustomButton(String title) {
        super(title);
    }
    public CustomButton(ImageIcon icon) {
        super(icon);
        setButtonBorder();
    }

    private void setButtonBorder() { // 버튼의 이미지를 나타내기 위해 테두리 등을 제거 (부드럽게 나타내기 .. )
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
    }
}
