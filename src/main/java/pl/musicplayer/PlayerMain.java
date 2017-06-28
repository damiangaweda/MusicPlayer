package pl.musicplayer;

import javax.sound.sampled.Mixer;

/**
 * Created by Damian on 002 2 maja.
 */

/**
 * Class used to keep control of the player thread
 */
public class PlayerMain {

    static Thread player;

    public static volatile boolean stop = false;
    public static volatile boolean pause = false;
    public static volatile boolean songStarted = false;
    public static volatile boolean incVol = false;
    public static volatile boolean decVol = false;
    public static volatile boolean mute = false;
    public static volatile boolean muteWasClicked = false;
    public static volatile boolean songEnded = false;

    /**
     * Invoke new player thread if there is no other threads running
     * and plays current song
     *
     * @param files FileManagement object
     */
    static void startSong(FileManagement files){
        if(!songStarted){
            //Error
            player = new Thread(new PlayerThread(files.getCurrentSongPath()));
            player.start();
            stop = false;
            songStarted = true;
        }
    }

    /**
     * Autoplays songs from playlist
     *
     * @param files FileManagement object to get current song
     * @param jProgressBarUpdate JProgressBarUpdate object used to reset timer
     */
    static void autoplay(FileManagement files, InterfaceMain.JProgressBarUpdate jProgressBarUpdate){
        stopCurrent();
        songStarted = false;
        startSong(files);
        jProgressBarUpdate.start();

    }

    /**
     * Sets flags to stop player thread
     */
    static void stopCurrent(){
        stop = true;
        pause = false;
    }

    /**
     * Reads flags and moves to next song, moves to next song and plays it depends on
     * what current flags says
     *
     * @param files FileManagement object to get next song
     * @param jProgressBarUpdate JProgressBarUpdate object used to reset timer
     */
    static void moveToNext(FileManagement files, InterfaceMain.JProgressBarUpdate jProgressBarUpdate){
        if(songStarted && !pause){
            stopCurrent();
            jProgressBarUpdate.stop();
            try {
                player.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            songStarted = false;
            stop = false;
            files.moveToNextSong();
            startSong(files);
            jProgressBarUpdate.start();
        }
        else if(songStarted && pause){
            stopCurrent();
            jProgressBarUpdate.stop();
            try {
                player.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            songStarted = false;
            stop = false;
            files.moveToNextSong();
        }
        else files.moveToNextSong();
    }

    /**
     * Reads flags and moves to next song, moves to previous song and plays it depends on
     * what current flags says
     *
     * @param files FileManagement object to get previous song
     * @param jProgressBarUpdate JProgressBarUpdate object used to reset timer
     */
    static void moveToPrev(FileManagement files, InterfaceMain.JProgressBarUpdate jProgressBarUpdate){
        if(songStarted && !pause){
            stopCurrent();
            jProgressBarUpdate.stop();
            try {
                player.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            songStarted = false;
            stop = false;
            files.moveToPrevSong();
            startSong(files);
            jProgressBarUpdate.start();
        }
        else if(songStarted && pause){
            stopCurrent();
            jProgressBarUpdate.stop();
            try {
                player.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            songStarted = false;
            stop = false;
            files.moveToPrevSong();
        }
        else files.moveToPrevSong();
    }

    /**
     * Sets increase volume flag
     */
    public static void increaseVolume(){
        incVol = true;
    }

    /**
     * Sets decrease volume flag
     */
    public static void decreaseVolume(){
        decVol = true;
    }

    /**
     * Sets flag to mute sound
     */
    public static void mute(){
        mute = true;
        muteWasClicked = true;
    }

    /**
     * Sets flag to unmute sound
     */
    public static void unmute(){
        mute = false;
        muteWasClicked = true;
    }

}
