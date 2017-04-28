package pl.musicplayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Created by Damian on 028 28 kwietnia.
 */
public class MP3Player {
    //TODO Use constructor to get name/directory of file to play

    //A sample way to play a mp3 file
    static void playMusic(){
        String bip = "bip.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }
}
