package kr.ac.jbnu.se.tetris.board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import kr.ac.jbnu.se.tetris.ai.TetrisAI;
import kr.ac.jbnu.se.tetris.game.GameConfig;
import kr.ac.jbnu.se.tetris.game.Tetris;

public class BoardAI extends Board implements ActionListener {

    private transient TetrisAI computer;

    private String bestRoute = "";

    private int index = 0;

    private int moveDelay;

    public BoardAI(Tetris parent) {
        super(parent);

        int level = parentTetris.gameManager().getLevel();
        moveDelay = GameConfig.AI_BASE_MOVE_DELAY - GameConfig.AI_DELAY_REDUCE_PER_LEVEL * level;

        computer = new TetrisAI(this);
        timer = new Timer(moveDelay, this);
        
        start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isBlockOvered)
            gameOver();
            
        if (isFallingFinished) {
            index = 0;
            newPiece();
            bestRoute = computer.getBestRoute();    
        } 
        else
            moveToBestRoute(index++);
    }

    private void moveToBestRoute(int i) {
        if(i >= bestRoute.length()){
            dropDown();
            return;
        }

        switch (bestRoute.charAt(i)) {
            case '0':
                move(curPiece, curPiece.curX() - 1, curPiece.curY());
                break;
            case '1':
                move(curPiece, curPiece.curX() + 1, curPiece.curY());
                break;
            case '2':
                move(curPiece, curPiece.curX(), curPiece.curY() - 1);
                break;
            case '3':
                move(curPiece.rotateRight(), curPiece.curX(), curPiece.curY());
                break;
            default:
                throw new IllegalArgumentException("route number is 0 ~ 3");
        }            
    }
}
