package kr.ac.jbnu.se.tetris.shape;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BlockPreview extends JPanel {

    protected final int panelWidth = 60, panelHeight = 80;
    protected final int previewBoardWidth = 3, previewBoardHeight = 4;

    private Tetrominoes[][] nextPiece;

    private transient Shape piece;

    public BlockPreview() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        nextPiece = new Tetrominoes[previewBoardHeight][previewBoardWidth];
        initNextPiece();
    }
    
    public int panelWidth(){
        return panelWidth;
    }
    public int panelHeight(){
        return panelHeight;
    }

    private int squareWidth(){
        return panelWidth / previewBoardWidth;
    }
    private int squareHeight(){
        return panelHeight / previewBoardHeight;
    }

    /**
     * Next 블록 배열을 초기화 한다.
     */
    private void initNextPiece(){
        for(int i = 0; i < previewBoardHeight; i++){
            for(int j = 0; j < previewBoardWidth; j++){
                nextPiece[i][j] = Tetrominoes.NO_SHAPE;
            }
        }
    }

    /**
     * Next 블록 배열을 설정한다.
     * @param piece 패널에 보여줄 블록 모양
     */
    public void setNextPiece(Shape piece){
        initNextPiece();
        this.piece = piece;
        for(int i = 0; i < 4; i++){
            int x = 1 + piece.x(i);
            int y = 1 + piece.y(i);
            nextPiece[y][x] = piece.getShape();
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(int i = 0; i < previewBoardHeight; i++){
            for(int j = 0; j < previewBoardWidth; j++){
                if(nextPiece[i][j] != Tetrominoes.NO_SHAPE){
                    drawSquare(g, 0 + j * squareWidth(), i * squareHeight(), piece.getShape());
                }
            }
        }
    }

    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
        BlockImage block = new BlockImage(shape);
        
        int imageSize = squareWidth(); // 이미지 크기를 블록 크기에 맞게 조정합니다.

        if(shape == Tetrominoes.BOMB_BLOCK) {
            imageSize = 30;
            g.drawImage(block.getImage(), x - 2, y - 6, imageSize, imageSize, null);
        }
        else
            g.drawImage(block.getImage(), x, y, imageSize, imageSize, null);
    }
}
