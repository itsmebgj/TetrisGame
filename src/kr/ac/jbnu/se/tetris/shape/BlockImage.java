package kr.ac.jbnu.se.tetris.shape;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import kr.ac.jbnu.se.tetris.ResourcePath;

public class BlockImage {

    private BufferedImage image;

    public BlockImage(Tetrominoes shape) {
        image = getImage(getImageFile(shape));
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // 예외 발생 시 null 반환
    }

    public String getImageFile(Tetrominoes shape) {
        String imgPath = "";
        switch (shape) {
            case NO_SHAPE:
                imgPath = ResourcePath.NO_SHAPE;
                break;
            case Z_SHAPE:
                imgPath = ResourcePath.Z_SHAPE;
                break;
            case S_SHAPE:
                imgPath = ResourcePath.S_SHAPE;
                break;
            case LINE_SHAPE:
                imgPath = ResourcePath.LINE_SHAPE;
                break;
            case T_SHAPE:
                imgPath = ResourcePath.T_SHAPE;
                break;
            case SQUARE_SHAPE:
                imgPath = ResourcePath.SQUARE_SHAPE;
                break;
            case L_SHAPE:
                imgPath = ResourcePath.L_SHAPE;
                break;
            case MIRRORED_L_SHAPE:
                imgPath = ResourcePath.MIRRORED_L_SHAPE;
                break;
            case BOMB_BLOCK:
                imgPath = ResourcePath.BOMB_BLOCK;
                break;
            case LOCK_BLOCK:
                imgPath = ResourcePath.LOCK_BLOCK;
                break;
        }
        return imgPath;
    }
}
