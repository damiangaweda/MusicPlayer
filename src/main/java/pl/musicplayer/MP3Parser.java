package pl.musicplayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.tritonus.share.sampled.file.TAudioFileFormat;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Created by Damian on 025 25 czerwca.
 * Parses MP3 tags
 */
public class MP3Parser {

    /**
     * Reads mp3 tags and returns name of the track
     *
     * @param fl location of mp3 file
     * @return name of track
     */
    public static String getName(String fl){
        String fileLocation = fl;
        try {

            InputStream input = new FileInputStream(new File(fileLocation));
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();

            return metadata.get("xmpDM:artist") + ": " + metadata.get("title");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }

        return "Something went wrong";
    }

    /**
     * Reads mp3 tags and returns duration of the track
     * @param fl location of mp3 file
     * @return track length as String
     */
    public static String getDuration(String fl){
        File file = new File(fl);
        try{
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
        if (fileFormat instanceof TAudioFileFormat) {
            Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
            String key = "duration";
            Long microseconds = (Long) properties.get(key);
            int mili = (int) (microseconds / 1000);
            int sec = (mili / 1000) % 60;
            int min = (mili / 1000) / 60;
            return min + ":" + sec;
        }
    }catch(Exception e){
        e.printStackTrace();
        }
        return "-1";
    }
}