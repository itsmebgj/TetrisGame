package kr.ac.jbnu.se.tetris.board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import kr.ac.jbnu.se.tetris.game.GameConfig;
import kr.ac.jbnu.se.tetris.game.Tetris;

public class BoardPlayer extends Board implements ActionListener {

    private int cutlineCheck;

    public BoardPlayer(Tetris parent) {
        super(parent);
        cutlineCheck = GameConfig.BOMB_CUTLINE_SCORE;
        timer = new Timer(GameConfig.PLAYER_MOVE_DELAY, this);        
        start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isBlockOvered)
            gameOver();
            
        if (isFallingFinished) {
            if(numLinesRemoved >= cutlineCheck) { // 점수 기준 넘어가면 폭탄 획득
                parentTetris.acquireBomb();
                cutlineCheck += GameConfig.BOMB_CUTLINE_SCORE;
            }
            newPiece();
        } else {
            oneLineDown();
        }
    }
}