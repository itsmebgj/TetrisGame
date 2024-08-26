package kr.ac.jbnu.se.tetris.game;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.util.List;
import java.util.ArrayList;

import kr.ac.jbnu.se.tetris.board.Board;
import kr.ac.jbnu.se.tetris.board.BoardAI;
import kr.ac.jbnu.se.tetris.board.BoardPlayer;
import kr.ac.jbnu.se.tetris.shape.BlockImage;
import kr.ac.jbnu.se.tetris.shape.BlockPreview;
import kr.ac.jbnu.se.tetris.shape.Tetrominoes;

public class Tetris extends JPanel {

    private TetrisGameManager parentManager;

    final int frame_X = 335, frame_Y = 500, status_X = 60, status_Y = 30;

    private boolean isComputer;
    
    // 체력바
    private JProgressBar healthBar;

    // 보드
    private Board board;

    // Next 블록 프리뷰
    private BlockPreview blockPreview;

    // 점수 패널
    private JPanel statusPanel;
    private JLabel statusbar;
    
    // 폭탄
    private JLabel bombLabel;
    private int bombCount;

    public Tetris(TetrisGameManager parent, boolean isComputer) {
        this.parentManager = parent;
        this.isComputer = isComputer;
        setTetrisLayout();
    }
    
    /**
     * 폭탄 개수를 1 카운트하는 메소드
     */
    public void acquireBomb() {
        String bomb = Integer.toString(++bombCount);
        bombLabel.setText("X " + bomb);
    }

    /**
     * 폭탄을 사용하는 메소드.
     * 
     * 폭탄 개수를 1 줄이고, 다음에 올 블록을 폭탄 아이템으로 변경한다.
     */
    public void useBomb() {
        if(bombCount > 0) {
            String bomb = Integer.toString(--bombCount);
            bombLabel.setText("X " + bomb);
            board.getNextPiece().setShape(Tetrominoes.BOMB_BLOCK);
            blockPreview.setNextPiece(board.getNextPiece());
        }
    }

    // -------------------------------------- get 메소드 --------------------------------------

    public TetrisGameManager gameManager() {
        return parentManager;
    }
    public JLabel getStatusBar() {
        return statusbar;
    }
    public Board getBoard() {
        return board;
    }
    public JProgressBar getHealthBar() {
        return healthBar;
    }
    public BlockPreview getBlockPreview(){
        return blockPreview;
    }
    public int frameX(){
        return frame_X;
    }
    public int frameY(){
        return frame_Y;
    }

    // -------------------------------------- 컴포넌트 레이아웃 설정 --------------------------------------

    private void setTetrisLayout() {
        setPreferredSize(new Dimension(frame_X, frame_Y));
        setBackground(new Color(210, 210, 210));
        setLayout(null);

        board = isComputer ? new BoardAI(this) : new BoardPlayer(this); // 1. 보드판
        blockPreview =  new BlockPreview(); // 2. Next 블록 프리뷰
        setHealthBar(); // 3. 체력바
        setScorePanel(); // 4. 스코어 패널
        setBombLabel(); // 5. 폭탄 개수

        setLayoutLocation(); // 각 컴포넌트 위치 조정

        addComponent(isComputer); // 각 컴포넌트 배치
    }

    private void setHealthBar() {
        healthBar = new JProgressBar(0, 100); // 최소 값과 최대 값을 설정
        healthBar.setValue(100); // 초기 체력 설정
        healthBar.setStringPainted(true); // 백분율 표시 활성화
        healthBar.setForeground(new Color(220, 120, 120)); // 체력 바의 색상 변경
        healthBar.setUI(new BasicProgressBarUI() {
            @Override
            protected Color getSelectionForeground() {
                return Color.WHITE; // 글자 색 변경
            }
        });
    }
    
    private void setScorePanel(){
        statusbar = new JLabel("0");
        statusPanel = new JPanel();
        statusPanel.add(statusbar, BorderLayout.CENTER);
    }

    private void setBombLabel() { // 폭탄 개수 텍스트
        bombLabel = new JLabel();
        bombLabel.setFont(bombLabel.getFont().deriveFont(17.0f));
        bombLabel.setText("X 1");
        bombCount = 1;
    }
    
    private void setLayoutLocation() {
        healthBar.setBounds(20, 15, board.panelWidth(), 15);
        board.setBounds(20, 40, board.panelWidth(), board.panelHeight());
        blockPreview.setBounds(35 + board.panelWidth(), 40, blockPreview.panelWidth(), blockPreview.panelHeight());
        statusPanel.setBounds(35 + board.panelWidth(), 40 + board.panelHeight() - status_Y, status_X, status_Y);
        bombLabel.setBounds(64 + board.panelWidth(), 40 + board.panelHeight() - status_Y - 49, 30, 30);
    }

    private void addComponent(boolean isComputer){
        add(board);
        add(blockPreview);
        add(healthBar);
        add(statusPanel);
        if(!isComputer) add(bombLabel); // 플레이어 패널만 폭탄 카운트 생성
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(!isComputer) { // 플레이어 패널만 폭탄 아이콘 생성
            BlockImage image = new BlockImage(Tetrominoes.BOMB_BLOCK);
            g.drawImage(image.getImage(), 32 + board.panelWidth(), 40 + board.panelHeight() - status_Y - 52,
                        30, 30, null);
        }
    }
}

