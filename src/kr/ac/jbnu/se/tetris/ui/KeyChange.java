package kr.ac.jbnu.se.tetris.ui;
import kr.ac.jbnu.se.tetris.game.TetrisGameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyChange extends JFrame implements ActionListener {

    private final int
            bt_Size = 30, txf_Width = 100, txf_Height = 30, frame_Size = 450,
            txf_X = 140, bt_X = 260;
    private String up, down, left, right, dropdown;

    private char invert1, invert2, invert3, invert4, invert5;

    private JTextField up_in, down_in, left_in, right_in, dropdown_in;

    private TextArea explain;

    private CustomButton bt1, bt2, bt3, bt4, bt5, exit;

    public KeyChange() {
        setFrame();
    }

    private void setFrame() {
        setTextfield();
        setButton();
        addComponent();
        setSize(frame_Size,frame_Size);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void addComponent() {
        add(up_in); add(down_in); add(left_in); add(right_in); add(dropdown_in);
        add(bt1); add(bt2); add(bt3); add(bt4); add(bt5); add(exit);
        add(explain);
    }

    private void addTextField() {
        up_in = new JTextField();
        down_in = new JTextField();
        left_in = new JTextField();
        right_in = new JTextField();
        dropdown_in = new JTextField();
        explain = new TextArea("2P만 변경 가능합니다. \n순서대로 위,아래,왼쪽,오른쪽,DropDown(한번에 내리기)입니다. \n하나씩 변경이 가능하고, " +
                "\nESC,SHIFT등 Function Key는 입력이 불가능합니다.\n 그리고 소문자만 입력 가능합니다.");
    }

    private void setTextfield() {
        addTextField();
        up_in.setBounds(txf_X,80,txf_Width,txf_Height);
        down_in.setBounds(txf_X,130,txf_Width,txf_Height);
        left_in.setBounds(txf_X,180,txf_Width,txf_Height);
        right_in.setBounds(txf_X,230,txf_Width,txf_Height);
        dropdown_in.setBounds(txf_X,280,txf_Width,txf_Height);
        explain.setBounds(0,320,450,450);
        textFieldAction();
    }

    private void textFieldAction() {
        up = up_in.getText();
        down = down_in.getText();
        left = left_in.getText();
        right = right_in.getText();
        dropdown = dropdown_in.getText();
    }

    private void addButton() {
        bt1 = new CustomButton();
        bt2 = new CustomButton();
        bt3 = new CustomButton();
        bt4 = new CustomButton();
        bt5 = new CustomButton();
        exit = new CustomButton("종료");
    }

    private void setButton() {
        addButton();
        bt1.setBounds(bt_X,80,bt_Size,bt_Size);
        bt2.setBounds(bt_X,130,bt_Size,bt_Size);
        bt3.setBounds(bt_X,180,bt_Size,bt_Size);
        bt4.setBounds(bt_X,230,bt_Size,bt_Size);
        bt5.setBounds(bt_X,280,bt_Size,bt_Size);
        exit.setBounds(0,0,bt_Size+30,bt_Size);
        addAction();
    }

    private void addAction() {
        bt1.addActionListener(this);
        bt2.addActionListener(this);
        bt3.addActionListener(this);
        bt4.addActionListener(this);
        bt5.addActionListener(this);
        exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // String to Char , 버튼 입력 이벤트
        textFieldAction();

        if(e.getSource() == bt1) {
            invert1 = up.charAt(0);
            TetrisGameManager.p2_Up = invert1;

            invert1 = Character.toUpperCase(invert1);
            TetrisGameManager.p2_Up_Upper = invert1;

            JOptionPane.showMessageDialog(null, "UP, 변경된 키 : " + invert1);
        }
        else if(e.getSource() == bt2) {
            invert2 = down.charAt(0);
            TetrisGameManager.p2_Down = invert2;

            invert2 = Character.toUpperCase(invert2);
            TetrisGameManager.p2_Down_Upper = invert2;

            JOptionPane.showMessageDialog(null, "DOWN, 현재 변경된 키 : " + invert2);
        }
        else if(e.getSource() == bt3) {
            invert3 = left.charAt(0);
            TetrisGameManager.p2_Left = invert3;

            invert3 = Character.toUpperCase(invert3);
            TetrisGameManager.p2_Left_Upper = invert3;

            JOptionPane.showMessageDialog(null, "LEFT, 현재 변경된 키 : " + invert3);
        }
        else if(e.getSource() == bt4) {
            invert4 = right.charAt(0);
            TetrisGameManager.p2_Right = invert4;

            invert4 = Character.toUpperCase(invert4);
            TetrisGameManager.p2_Right_Upper = invert4;

            JOptionPane.showMessageDialog(null, "RIGHT, 현재 변경된 키 : " + invert4);
        }
        else if(e.getSource() == bt5) {
            invert5 = dropdown.charAt(0);
            TetrisGameManager.p2_Dropdown = invert5;

            invert5 = Character.toUpperCase(invert5);
            TetrisGameManager.p2_Dropdown = invert5;

            JOptionPane.showMessageDialog(null, "DROPDOWN, 현재 변경된 키 : " + invert5);
        }
        else if(e.getSource() == exit){
            dispose();
        }
    }
}
