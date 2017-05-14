package pl.musicplayer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        FileManagement fileManager = new FileManagement();
        InterfaceMain interaceMain =  new InterfaceMain(fileManager);

    }
}
