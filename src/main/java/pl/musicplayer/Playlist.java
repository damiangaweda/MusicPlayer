package pl.musicplayer;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * List of chosen songs in another Frame
 */
public class Playlist extends JFrame {
    private Iterator<String> it;
    JTextArea playlist;

    /**
     * Sets it iterator to get list of songs
     * @param f FileManagement object
     */
    Playlist(FileManagement f){
        setFile(f.getSongList());
        createWindow();
    }

    /**
     * Sets list of songs
     *
     * @param ite iterator with list of songs
     */
    public void setFile(Iterator<String> ite){
        this.it = ite;
    }

    /**
     * Writes iterator contents into one String
     *
     * @return list of songs as one String wiht \n
     */
    public String convertToText(){
        StringBuilder stringBuilder = new StringBuilder();
        String tmp;
        if(it.hasNext()){
            while(it.hasNext()) {
                tmp = it.next();
                if (!tmp.endsWith(".ogg"))
                    stringBuilder.append(MP3Parser.getName(tmp) + "\n");
                else
                    stringBuilder.append(tmp + "\n");
            }
        }else
            stringBuilder.append("NO SONGS ADDED");

        return stringBuilder.toString();
    }

    /**
     * Sets elements and display window
     */
    private void createWindow() {
        JPanel displayPanel = new JPanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Point settingsPoint = new Point((int)width/2+300,(int)height/2-150);

        this.setSize(300, 400);

        this.setLocation(settingsPoint);
        this.setResizable(false);

        this.setTitle("Settings");

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //this.setUndecorated(true);
        //this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        //getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));

        playlist = new JTextArea();
        playlist.setPreferredSize(new Dimension(290,390));
        playlist.setEditable(false);
        playlist.setText(convertToText());
        displayPanel.add(playlist);
        this.add(displayPanel);

        this.setVisible(true);
    }

    /**
     * Delete window
     */
    public void closeWindow(){
        this.setVisible(false);
        this.dispose();
    }
}
