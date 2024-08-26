package kr.ac.jbnu.se.tetris.score;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import kr.ac.jbnu.se.tetris.ResourcePath;

public class MaxScorePanel extends JPanel {

    private int level;

    private String maxScore;

    public MaxScorePanel(int level){
        this.level = level;
        setMaxScorePanel();
    }

    public int getMaxScore(){
        return Integer.parseInt(maxScore);
    }

    private void setMaxScorePanel() {  
        if(level == 0)
            return;

        try {
            try(BufferedReader scoreReading = new BufferedReader(new FileReader(ResourcePath.MAX_SCORE[level - 1]))) {
                maxScore = scoreReading.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel maxScoreLabel = new JLabel("최대 점수 : " + maxScore);
        add(maxScoreLabel, BorderLayout.CENTER);
    }

    public void fileWriter(int score) {
        if(level == 0)
            return;

        try {
            try(FileWriter scoreWriter = new FileWriter(ResourcePath.MAX_SCORE[level - 1])){
                scoreWriter.write(Integer.toString(score));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
