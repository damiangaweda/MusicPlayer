package pl.musicplayer;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javazoom.spi.PropertiesContainer;

/**
 * Created by Damian on 002 2 maja.
 */

public class OGGPlayer {
    public final String fileName;

    public boolean mustStop = false;


    public OGGPlayer(String pFileName) {
        fileName = pFileName;
    }

    public void play() throws Exception {
        mustStop = false;
        File file = new File(fileName);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        if (audioInputStream == null) {
            throw new Exception("Unable to get audio input stream");
        }
        AudioFormat baseFormat = audioInputStream.getFormat();
        AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
        AudioInputStream decodedAudioInputStream = AudioSystem.getAudioInputStream(decodedFormat,
                audioInputStream);
        if (!(decodedAudioInputStream instanceof PropertiesContainer)) {
            throw new Exception("Wrong PropertiesContainer instance");
        }

        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, decodedFormat);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(decodedFormat);

        byte[] tempBuffer = new byte[4096];

        // Start
        sourceDataLine.start();
        int nbReadBytes = 0;
        while (nbReadBytes != -1) {
            if (mustStop) {
                break;
            }
            nbReadBytes = decodedAudioInputStream.read(tempBuffer, 0, tempBuffer.length);
            if (nbReadBytes != -1)
                sourceDataLine.write(tempBuffer, 0, nbReadBytes);
        }

        // Stop
        sourceDataLine.drain();
        sourceDataLine.stop();
        sourceDataLine.close();
        decodedAudioInputStream.close();
        audioInputStream.close();
    }

    public void setMustStop(boolean pMustStop) {
        mustStop = pMustStop;
    }

    public void stop() {
        mustStop = true;
    }

}
