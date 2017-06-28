package pl.musicplayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Choosing path to find playable music files
 */
public class FileManagement extends JPanel {
    private File directoryLocation;
    private LinkedList<String> listOfFiles;
    private ListIterator<String> it;
    private JFileChooser chooser;

    private boolean wasAdded = false;

    FileManagement() {
        listOfFiles = new LinkedList<>();
        loadFiles();
    }

    public void addFiles() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select a folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            directoryLocation = new File(chooser.getSelectedFile().toString());
            wasAdded = true;
            setListOfFiles();
        }
    }

    /**
     * Method used to get list of files
     * Files with .mp3 .ogg extensions will be used by program later on
     *
     * Sorts files and saves the list
     */

    private void setListOfFiles() {
        if (wasAdded) {
            File[] files = new File(directoryLocation.toString()).listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return (name.endsWith(".ogg") || name.endsWith(".mp3"));
                }
            });

            if (files != null && listOfFiles.isEmpty()) {
                for (File file : files) {
                    if (file.isFile()) {
                        listOfFiles.add(file.getParentFile().toString() + "\\" + file.getName());
                    }
                }
                it = listOfFiles.listIterator();
            }
            if (files != null) {

                for (File file : files) {
                    if (file.isFile() && !wasItAddedBefore(file.getParentFile().toString() + "\\" + file.getName())) {
                        listOfFiles.add(file.getParentFile().toString() + "\\" + file.getName());
                    }
                }
            }
            sortList(true);
            saveFiles();
        } else
            JOptionPane.showMessageDialog(this, "No songs added", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Checks if files in dir was added before
     *
     * @param path filepath
     * @return true if file was added
     */
    boolean wasItAddedBefore(String path) {
        ListIterator<String> it = listOfFiles.listIterator();
        while (it.hasNext())
            if (path.equals(it.next()))
                return true;
        return false;
    }

    /**
     * Checks if file is empty
     *
     * @return true if file is empty
     */
    boolean isFileEmpty(){
        File file = new File("songlist.txt");
        boolean empty = file.length() == 0;
        return empty;
    }

    /**
     * Saves playlist
     */
    void saveFiles() {
        ListIterator<String> it = listOfFiles.listIterator(0);
        try {
            PrintWriter out = new PrintWriter("songlist.txt");
            while (it.hasNext())
                out.println(it.next());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load playlist
     */
    void loadFiles() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("songlist.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                listOfFiles.add(line);
                wasAdded = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        it = listOfFiles.listIterator(0);
    }

    /**
     * Sorts listOfFiles
     *
     * @param ascending if true sort ascending
     */
    void sortList(boolean ascending) {
        if (wasAdded) {
            if (ascending)
                Collections.sort(listOfFiles);
            else
                Collections.sort(listOfFiles, Collections.<String>reverseOrder());
        } else
            JOptionPane.showMessageDialog(this, "No songs added", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shuffles listOfFiles
     */
    public void shuffleList() {
        if (wasAdded)
            Collections.shuffle(listOfFiles);
        else
            JOptionPane.showMessageDialog(this, "No songs added", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Return listOfFiles
     * @return listOfFiles
     */
    public Iterator<String> getSongList() {
        it = listOfFiles.listIterator();
        return this.it;
    }

    /**
     * Moves iterator to next song
     */
    public void moveToNextSong() {
        if (wasAdded) {
            if (it.hasNext())
                it.next();
            else
                it = listOfFiles.listIterator();
        } else
            JOptionPane.showMessageDialog(this, "No songs added", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Moves iterator to previous song
     */
    public void moveToPrevSong() {
        if (wasAdded) {
            if (it.hasPrevious())
                it.previous();
            else
                while (it.hasNext())
                    it.next();
        } else
            JOptionPane.showMessageDialog(this, "No songs added", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @return current song path
     */
    public String getCurrentSongPath() {
        String name;
        if (wasAdded) {
            if (it.hasNext()) {
                name = it.next();
                it.previous();
                return name;
            } else {
                it = listOfFiles.listIterator();
                name = it.next();
                it.previous();
                return name;
            }
        } else {
            JOptionPane.showMessageDialog(this, "No songs added", "Error", JOptionPane.ERROR_MESSAGE);
            return "NO SONGS ADDED";
        }
    }

    /**
     * Return song name as String if file is .mp3
     *
     * @return song name as String if file is .mp3
     */
    public String getSongName(){
        if(getCurrentSongPath().endsWith(".ogg"))
            return "Unknown name";
        String name = MP3Parser.getName(getCurrentSongPath());
        if (name.equals("null: null"))
            return "Unknown name";
        else
            return name;
    }

    /**
     * Return song duration
     *
     * @return song duration as String
     */
    public String getSongDuration(){
        try {
            return MP3Parser.getDuration(getCurrentSongPath());
        }catch(Exception e){
            e.printStackTrace();
        }
        return "Something went wrong";
    }

    /**
     * Converts String duration of track to int as seconds
     *
     * @return int as seconds
     */
    int timeToInt(){
        if(!isFileEmpty()) {
            boolean wasSplit = false;
            char[] charArray = MP3Parser.getDuration(getCurrentSongPath()).toCharArray();
            StringBuilder minB = new StringBuilder();
            StringBuilder secB = new StringBuilder();
            for (char i : charArray) {
                if (i == ':') {
                    wasSplit = true;
                    continue;
                }
                if (!wasSplit)
                    minB.append(i);
                else
                    secB.append(i);
            }
            int min = Integer.parseInt(minB.toString());
            int sec = Integer.parseInt(secB.toString());

            return min * 60 + sec;
        }
        else return 0;
    }

    /**
     * Wipes contents of listOfFiles and songlist.txt
     */
    public void wipePlaylist(){
        try {
            listOfFiles = new LinkedList<>();
            PrintWriter writer = new PrintWriter("songlist.txt");
            writer.print("");
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
