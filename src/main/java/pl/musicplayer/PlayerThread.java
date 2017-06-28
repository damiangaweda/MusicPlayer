package pl.musicplayer;

import java.io.File;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;


import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;



/**
 * Created by Damian on 002 2 maja.
 * Read file as bytes and plays it
 */
public class PlayerThread implements Runnable {

    String filePath;
    PlayerThread(String fn){
        this.filePath = fn;
    }

    float currentVolume = 0.0f;

    /**
     * Reads file, converts it to bytes and reads it until
     * stop flag from PlayerMain is set to true
     * Can be paused by setting pause from PlayerMain to true
     */
        public void play() {
            final File file = new File(filePath);

            try {
                final AudioInputStream in = getAudioInputStream(file);
                final AudioFormat outFormat = getOutFormat(in.getFormat());
                final Info info = new Info(SourceDataLine.class, outFormat);

                try {
                    final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                    if (line != null) {
                        line.open(outFormat);
                        if(PlayerMain.mute)
                            mute(line,true);
                        line.start();
                        stream(getAudioInputStream(outFormat, in), line);
                        line.drain();
                        line.stop();
                    }


                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

    /**
     * Changes volume by gaining control of line and setting
     * new volume
     *
     * @param increase decide if increase of decrease volume
     * @param line currently used line
     */
    void changeVolume(int increase, SourceDataLine line){
            if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volume = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);

                if(increase == 1 && (volume.getValue() >= -40f && volume.getValue() < 0f))
                    volume.setValue(volume.getValue()+4f);

                if(increase == 2 && (volume.getValue() > -40f && volume.getValue() <= 0f))
                    volume.setValue(volume.getValue()-4f);

                currentVolume = volume.getValue();
            }
        }

    /**
     * Mutes/Unmutes sound depending on mute flag
     *
     * @param line currently used line
     * @param mute if true mute, if false unmute
     */
    void mute(SourceDataLine line,boolean mute){
            if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volume = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                if(mute)
                    volume.setValue(-70f);
                if(!mute)
                    volume.setValue(currentVolume);
                PlayerMain.muteWasClicked = false;
            }
        }


    /**
     * Reads file format and converts it to new readable one
     *
     * @param inFormat input format
     * @return new AudioFormat
     */
    private AudioFormat getOutFormat(AudioFormat inFormat) {
            final int ch = inFormat.getChannels();
            final float rate = inFormat.getSampleRate();
            return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
        }

    /**
     * Reads audio file and plays it
     *
     * @param in input audio stream
     * @param line current line
     * @throws Exception
     */
        private void stream(AudioInputStream in, SourceDataLine line)
                throws Exception {
            final byte[] buffer = new byte[65536];
            int n;
            for (n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
                while (PlayerMain.pause)
                    Thread.sleep(1);
                if(PlayerMain.stop)
                    break;
                if(PlayerMain.incVol) {
                    changeVolume(1, line);
                    PlayerMain.incVol = false;
                }
                if(PlayerMain.decVol) {
                    changeVolume(2, line);
                    PlayerMain.decVol = false;
                }
                if(PlayerMain.mute && PlayerMain.muteWasClicked)
                    mute(line,true);

                if(!PlayerMain.mute && PlayerMain.muteWasClicked)
                    mute(line,false);

                line.write(buffer, 0, n);
            }
            if(n == -1)
                PlayerMain.songEnded = true;
        }

    /**
     * Runnable run function
     */
    @Override
    public void run() {
        try {
            play();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}