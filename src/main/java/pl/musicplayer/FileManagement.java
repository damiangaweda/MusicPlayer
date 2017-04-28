package pl.musicplayer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Damian on 028 28 kwietnia.
 */
public class FileManagement {
    String directoryLocation;
    List<String> listOfFiles;

    FileManagement(){
        directoryLocation = new String();
        listOfFiles = new ArrayList<String>();
    };

    /**
     * Method used to set directory with music files
     * @param dir
     */
    void getDirectory(String dir){
        this.directoryLocation = dir;
    }

    /**
     * Method used to get list of files
     * Files with .mp3 .ogg extensions will be used by program later on
     */
    void setListOfFiles(){
        File[] files = new File(directoryLocation).listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return (name.endsWith(".ogg") || name.endsWith(".mp3"));
            }
        });;

        for (File file : files) {
            if (file.isFile()) {
                listOfFiles.add(file.getName());
            }
        }
    }
}
