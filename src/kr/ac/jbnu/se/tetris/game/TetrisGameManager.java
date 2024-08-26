package kr.ac.jbnu.se.tetris.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import kr.ac.jbnu.se.tetris.ResourcePath;
import kr.ac.jbnu.se.tetris.board.Board;
import kr.ac.jbnu.se.tetris.score.MaxScorePanel;
import kr.ac.jbnu.se.tetris.shape.Shape;
import kr.ac.jbnu.se.tetris.shape.Tetrominoes;
import kr.ac.jbnu.se.tetris.timer.TimerPanel;
import kr.ac.jbnu.se.tetris.ui.Select;
import kr.ac.jbnu.se.tetris.ui.dialogs.GameOverDialog;
import kr.ac.jbnu.se.tetris.ui.dialogs.PauseDialog;

public class TetrisGameManager extends JFrame {
    
    public static int p2_Up = 'w', p2_Down = 's', p2_Left = 'a', p2_Right = 'd',
                        p2_Up_Upper = 'W', p2_Down_Upper = 'S', p2_Left_Upper = 'A', p2_Right_Upper = 'D',
                        p2_Dropdown = KeyEvent.VK_SHIFT;

    private Select mainPage;

    private int level;

    private boolean isPaused = false;

    // 최대 점수 표기 (AI 대전)
    private MaxScorePanel maxScorePanel;

    // 타이머
    private TimerPanel timer;

    // 각 보드판 패널
    private Tetris player1Panel;
    private Tetris player2Panel;

    // 일시 정지, 게임 종료 UI
    private PauseDialog pauseDialog;
    private GameOverDialog gameOverDialog;

    public TetrisGameManager(Select mainPage, int level) {
        setFrame();

        this.mainPage = mainPage;
        this.level = level;

        addKeyListener(new PlayerKeyListener());
    }

    public int getLevel() {
        return level;
    }
    public Select getMainPage() { return mainPage; }
    public boolean isComputer(){
        return (level > 0);
    }
    public MaxScorePanel getMaxScorePanel(){
        return maxScorePanel;
    }
    public JDialog gameOverDialog(){
        return gameOverDialog;
    }

    /**
     * 게임 시작을 담당한다.
     */
    public void run() {
        maxScorePanel = new MaxScorePanel(level); // 1. 최대 점수 패널     
        timer = new TimerPanel(); // 2. 타이머

        pauseDialog = new PauseDialog(this); // 3. 일시정지 화면 설정
        gameOverDialog = new GameOverDialog(this); // 4. 게임종료 화면 설정

        player1Panel = new Tetris(this, false); // 5. 테트리스 패널 1
        player2Panel = new Tetris(this, isComputer()); // 6. 테트리스 패널 2

        // 각 테트리스 패널의 대결 상대 설정
        Board p1Board = player1Panel.getBoard();
        Board p2Board = player2Panel.getBoard();
        p1Board.setOpponent(p2Board);
        p2Board.setOpponent(p1Board);

        setLayoutLocation(); // 각 컴포넌트 위치 설정

        addComponent(isComputer()); // 각 컴포넌트 배치

        timer.startTimer(); // 타이머 가동

        setVisible(true);
    }

    /**
     * 게임을 일시정지 하는 메소드
     */
    public void pause() {
        Board p1Board = player1Panel.getBoard();
        Board p2Board = player2Panel.getBoard();

        if (!p1Board.isStarted() || !p2Board.isStarted())
            return;

        isPaused = !isPaused;

        if (isPaused) {
            p1Board.pause();
            p2Board.pause();
        } else {
            p1Board.start();
            p2Board.start();
        }

        pauseDialog.setVisible(isPaused);
    }

    /**
     * 기본 프레임 세팅
     */
    private void setFrame() {
        setTitle("Tetris");
        setSize(750, 620);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFocusable(true);
        getContentPane().setBackground(new Color(220, 220, 220));
    }
    private void setLayoutLocation() {
        timer.setBounds(325, 10, 100, 25);
        maxScorePanel.setBounds(30, 10, 90, 25);
        player1Panel.setBounds(20, 45, player1Panel.frameX(), player1Panel.frameY());
        player2Panel.setBounds(player2Panel.frameX() + 60, 45, player2Panel.frameX(), player2Panel.frameY());
    }
    private void addComponent(boolean isComputer) {
        if(isComputer) add(maxScorePanel); // AI 모드인 경우에만 최대 점수 패널을 추가
        add(timer);
        add(player1Panel);
        add(player2Panel);
    }
    
    /**
     * 이미지 페인트
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if(!isComputer())
            return;

        try {  
            BufferedImage image = ImageIO.read(new File(ResourcePath.KEY_INPUT_MANUAL));
            g.drawImage(image, 0, getHeight() - 30, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // 키 입력 관리
    public class PlayerKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            Board p1Board = player1Panel.getBoard();
            Board p2Board = player2Panel.getBoard();
            
            if (!p1Board.isStarted() || !p2Board.isStarted()) {
                return;
            }

            int keycode = e.getKeyCode();

            if (keycode == KeyEvent.VK_ESCAPE) {
                pause();
                return;
            }
            if (isPaused)
                return;

            p1KeyInput(keycode, p1Board);
            p2KeyInput(keycode, p2Board);
        }
        
        /**
         * Player 1 (GUI 화면 상 왼쪽) 의 키 입력을 처리하는 메소드
         * 
         * @param keycode 입력된 키
         * @param p1Board Player 1 의 보드
         */
        private void p1KeyInput(int keycode, Board p1Board){
            Shape p1CurPiece = p1Board.getCurPiece();

            if(p1CurPiece.getShape() == Tetrominoes.NO_SHAPE)
                return;

            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    p1Board.move(p1CurPiece, p1CurPiece.curX() - 1, p1CurPiece.curY());
                    break;
                case KeyEvent.VK_RIGHT:
                    p1Board.move(p1CurPiece, p1CurPiece.curX() + 1, p1CurPiece.curY());
                    break;
                case KeyEvent.VK_UP:
                    Shape leftRotated = p1CurPiece.rotateLeft();
                    p1Board.move(leftRotated, p1CurPiece.curX(), p1CurPiece.curY());
                    break;
                case KeyEvent.VK_DOWN:
                    Shape rightRotated = p1CurPiece.rotateRight();
                    p1Board.move(rightRotated, p1CurPiece.curX(), p1CurPiece.curY());
                    break;
                case KeyEvent.VK_SPACE:
                    p1Board.dropDown();
                    break;
                case 'm':
                case 'M':
                    p1Board.oneLineDown();
                    break;
                case '/':
                    player1Panel.useBomb();
                    break;
                default:
                    break;
            }
        }

        /**
         * Player 2 (GUI 화면 상 오른쪽) 의 키 입력을 처리하는 메소드
         * 
         * @param keycode 입력된 키
         * @param p2Board Player 2 의 보드
         */
        private void p2KeyInput(int keycode, Board p2Board){
            if(isComputer())
                return;

            Shape p2CurPiece = p2Board.getCurPiece();

            if(p2CurPiece.getShape() == Tetrominoes.NO_SHAPE)
                return;

            if (keycode == p2_Left || keycode == p2_Left_Upper) {
                p2Board.move(p2CurPiece, p2CurPiece.curX() - 1, p2CurPiece.curY());
            }
            if (keycode == p2_Right || keycode == p2_Right_Upper) {
                p2Board.move(p2CurPiece, p2CurPiece.curX() + 1, p2CurPiece.curY());
            }     
            if (keycode == p2_Up || keycode == p2_Up_Upper) {
                Shape leftRotated = p2CurPiece.rotateLeft();
                p2Board.move(leftRotated, p2CurPiece.curX(), p2CurPiece.curY());
            }
            if (keycode == p2_Down || keycode == p2_Down_Upper) {
                Shape rightRotated = p2CurPiece.rotateRight();
                p2Board.move(rightRotated, p2CurPiece.curX(), p2CurPiece.curY());
            }
            if (keycode == p2_Dropdown) {
                p2Board.dropDown();
            }
            if (keycode == KeyEvent.VK_CONTROL) {
                p2Board.oneLineDown();
            }
            if(keycode == 'q' || keycode == 'Q'){
                player2Panel.useBomb();
            }
        }
    }
}
