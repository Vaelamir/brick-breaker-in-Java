package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * Class that is responsible for game sounds
 */
public class Sound {
    Clip clip;
    URL soundURL [] = new URL[30];

    /**
     * Constructor that contains paths for sound files
     * and stores the locations within the array
     */
    public Sound(){
        soundURL[0] = getClass().getResource("/sound/paddleHit.wav");
        soundURL[1] = getClass().getResource("/sound/paddleMiss.wav");
        soundURL[2] = getClass().getResource("/sound/brickHit.wav");
    }

    /**
     * Instantiates an audio input stream object
     * with the sound array
     * @param i
     */
    public void setFile(int i){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        }catch (Exception e){

        }
    }

    /**
     * plays the chosen audio clip
     */
    public void play(){
        clip.start();
    }
}
