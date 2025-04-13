package steve;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Musica {
    private Clip clip;

    public void reproducir() {
        try {
            File archivo = new File("C:\\Users\\jriva\\Documents\\NetBeansProjects\\steve\\src\\steve\\himno.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivo);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void detener() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
