package kr.ac.jbnu.se.tetris.audio;

import javax.sound.sampled.*;

import kr.ac.jbnu.se.tetris.ResourcePath;

import java.io.File;
import java.io.IOException;

public class Music {

    private Clip clip;

    public Music() {
        setMusic();
    }

    public void setMusic() {
        File file = new File(ResourcePath.BGM);
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    
    public void setVolume(float volume){
        if(clip != null){
            if (volume < 0f) volume = 0f;
            else if (volume > 1f) volume = 1f;
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            volumeControl.setValue(dB);
        }
    }

    public void stopMusic() {
        clip.stop();
    }

    public void startMusic() {
        clip.start();
        setVolume(0.3f);
        clip.loop(10);
    }

}


