package pl.musicplayer;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Damian on 028 28 kwietnia.
 */
public class FileManagement {
    private String directoryLocation;
    private List<String> listOfFiles;
    private ListIterator<String> it;

    FileManagement(){
        directoryLocation = new String("songs/");
        listOfFiles = new ArrayList<String>();
        setListOfFiles();
        it = listOfFiles.listIterator();
    };

    /**
     * Method used to set directory with music files
     * @param dir
     */
    private void getDirectory(String dir){
        this.directoryLocation = dir;
    }

    /**
     * Method used to get list of files
     * Files with .mp3 .ogg extensions will be used by program later on
     */
    private void setListOfFiles(){
        File[] files = new File(directoryLocation).listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return (name.endsWith(".ogg") || name.endsWith(".mp3"));
            }
        });

        if(files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    listOfFiles.add(file.getName());
                }
            }
            sortList(true);
        }

    }

    void sortList(boolean ascending){
        if(ascending)
            Collections.sort(listOfFiles);
        else
            Collections.sort(listOfFiles,Collections.<String>reverseOrder());
    }

    void shuffleList(){
        Collections.shuffle(listOfFiles);
    }

    public List<String> getSongList(){
        return this.listOfFiles;
    }

    public void moveToNextSong(){
        if(it.hasNext())
            it.next();
        else
            it = listOfFiles.listIterator();
    }

    public void moveToPrevSong(){
        if(it.hasPrevious())
            it.previous();
        else
            while(it.hasNext())
                it.next();
    }

    public String getCurrentSongName(){
        return it.toString();
    }
}
