package pl.musicplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * Music player interface
 */
public class InterfaceMain extends JFrame {

    private Playlist playlist;

    FileManagement fileManagement;

    private JButton prevButton;
    private JButton nextButton;
    private JButton playPauseButton;
    private JButton playlistButton;
    private JButton muteButton;
    private JButton volumeUpButton;
    private JButton volumeDownButton;
    private JButton shuffleButton;
    private JButton repeatButton;
    private JButton addButton;
    private JButton wipeButton;
    private boolean wasPlayClicked = false;
    private boolean wasShuffleClicked = false;
    private static volatile boolean wasRepeatClicked = false;
    private boolean wasMuteClicked = false;
    private boolean wasSettingsClicked = false;

    protected JProgressBar songTimeBar;

    protected JTextField songTextField;
    protected JTextField minSongTime;
    protected JTextField maxSongTime;

    protected GridBagConstraints gdcMain;
    protected GridBagConstraints gdcUpperButtons;

    protected JPanel mainPanel;
    protected JPanel upperButtonsPanel;

    JProgressBarUpdate jProgressBarUpdate;
    private static volatile boolean mustStop = false;
    private static volatile boolean mustPause = false;
    private static volatile boolean somethingWasPlaying = false;

    protected final ImageIcon playIcon = new ImageIcon("icons/03.png");
    protected final ImageIcon playHoverIcon = new ImageIcon("icons/03active.png");

    protected final ImageIcon pauseIcon = new ImageIcon("icons/03_1.png");
    protected final ImageIcon pauseHoverIcon = new ImageIcon("icons/03_1active.png");

    protected final ImageIcon prevIcon = new ImageIcon("icons/02_2.png");
    protected final ImageIcon prevHoverIcon = new ImageIcon("icons/02_2active.png");

    protected final ImageIcon nextIcon = new ImageIcon("icons/02.png");
    protected final ImageIcon nextHoverIcon = new ImageIcon("icons/02active.png");

    protected final ImageIcon playlistIcon = new ImageIcon("icons/12.png");
    protected final ImageIcon playlistIconHover = new ImageIcon("icons/12active.png");

    protected final ImageIcon muteIcon = new ImageIcon("icons/10_3.png");
    protected final ImageIcon muteHoverIcon = new ImageIcon("icons/10_3active.png");
    protected final ImageIcon unmuteIcon = new ImageIcon("icons/10.png");
    protected final ImageIcon unmuteHoverIcon = new ImageIcon("icons/10active.png");

    protected final ImageIcon volumeUpIcon = new ImageIcon("icons/11_1.png");
    protected final ImageIcon volumeUpHoverIcon = new ImageIcon("icons/11_1active.png");

    protected final ImageIcon volumeDownIcon = new ImageIcon("icons/11.png");
    protected final ImageIcon volumeDownHoverIcon = new ImageIcon("icons/11active.png");

    protected final ImageIcon shuffleIcon = new ImageIcon("icons/07_1.png");
    protected final ImageIcon shuffleHoverIcon = new ImageIcon("icons/07_1active.png");
    protected final ImageIcon unShuffleIcon = new ImageIcon("icons/07_3.png");
    protected final ImageIcon unShuffleHoverIcon = new ImageIcon("icons/07_3active.png");

    protected final ImageIcon repeatIcon = new ImageIcon("icons/07.png");
    protected final ImageIcon repeatHoverIcon = new ImageIcon("icons/07active.png");
    protected final ImageIcon noRepeatIcon = new ImageIcon("icons/07_2.png");
    protected final ImageIcon noRepeatHoverIcon = new ImageIcon("icons/07_2active.png");

    protected final ImageIcon addDirectoryIcon = new ImageIcon("icons/01.png");
    protected final ImageIcon addDirectoryHoverIcon = new ImageIcon("icons/01active.png");

    protected final ImageIcon wipeIcon = new ImageIcon("icons/01_1.png");
    protected final ImageIcon wipeHoverIcon = new ImageIcon("icons/01_1active.png");

    /**
     * Create FileManagement object and invoke createWindow();
     */
    InterfaceMain(){
        fileManagement = new FileManagement();
        createWindow();
    }

    /**
     * Sets most important elements and displays Frame
     */
    private void createWindow(){

        this.setSize(600,250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.setTitle("Music Player 1.0");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        gdcMain = new GridBagConstraints();
        upperButtonsPanel = new JPanel();
        upperButtonsPanel.setLayout(new GridBagLayout());
        gdcUpperButtons = new GridBagConstraints();

        gdcMain.gridx = 2;
        gdcMain.gridy = 1;
        gdcMain.ipadx = 300;
        gdcMain.ipady = 8;
        gdcMain.fill = GridBagConstraints.CENTER;
        gdcMain.fill = GridBagConstraints.REMAINDER;


        if(!fileManagement.isFileEmpty())
            songTextField = new JTextField(fileManagement.getSongName(),30);
        else
            songTextField = new JTextField("No songs added!");
        songTextField.setEditable(false);
        songTextField.setBorder(null);
        songTextField.setHorizontalAlignment(JTextField.CENTER);
        Font songTextFont = new Font("AppleGothic",Font.PLAIN,15);
        songTextField.setFont(songTextFont);
        gdcMain.gridx = 1;
        gdcMain.gridy = 1;
        gdcMain.gridwidth = 3;
        mainPanel.add(songTextField,gdcMain);

        minSongTime = new JTextField("0:00");
        minSongTime.setEditable(false);
        minSongTime.setBorder(null);
        minSongTime.setHorizontalAlignment(JTextField.CENTER);

        gdcMain.gridwidth = 1;
        gdcMain.gridx = 0;
        gdcMain.gridy = 3;
        mainPanel.add(minSongTime,gdcMain);

        if(!fileManagement.isFileEmpty())
            maxSongTime = new JTextField(fileManagement.getSongDuration());
        else
            maxSongTime = new JTextField("0:00");
        maxSongTime.setEditable(false);
        maxSongTime.setBorder(null);
        maxSongTime.setHorizontalAlignment(JTextField.CENTER);

        gdcMain.gridx = 4;
        gdcMain.gridy = 3;
        mainPanel.add(maxSongTime,gdcMain);
        ///////////////////////////////////////////////
        addButtons();

        this.add(mainPanel);

        this.setVisible(true);
    }

    /**
     * Sets and adds all the buttons to the main panel
     * Sets listeners to buttons
     */

    private void addButtons(){
        /**
         * prev button icons and actions
         */

        prevButton = new JButton(prevIcon);
        prevButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        prevButton.setToolTipText("Replay current or move to previous song");
        prevButton.setBorderPainted(false);
        prevButton.setContentAreaFilled(false);
        prevButton.setFocusPainted(false);

        prevButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                prevButton.setIcon(prevHoverIcon);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                prevButton.setIcon(prevIcon);
            }
        });

        /**
         * playPause button icons and actions
         */

        playPauseButton = new JButton(playIcon);
        playPauseButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        playPauseButton.setToolTipText("Play or pause");
        playPauseButton.setBorderPainted(false);
        playPauseButton.setContentAreaFilled(false);
        playPauseButton.setFocusPainted(false);

        playPauseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(wasPlayClicked)
                    playPauseButton.setIcon(pauseHoverIcon);
                else
                    playPauseButton.setIcon(playHoverIcon);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(wasPlayClicked)
                    playPauseButton.setIcon(pauseIcon);
                else
                    playPauseButton.setIcon(playIcon);
            }
        });

        nextButton = new JButton(nextIcon);
        nextButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        nextButton.setToolTipText("Skip to next song");
        nextButton.setBorderPainted(false);
        nextButton.setContentAreaFilled(false);
        nextButton.setFocusPainted(false);

        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextButton.setIcon(nextHoverIcon);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextButton.setIcon(nextIcon);
            }
        });

        playlistButton = new JButton(playlistIcon);
        playlistButton.setToolTipText("Playlist");
        playlistButton.setBorderPainted(false);
        playlistButton.setContentAreaFilled(false);
        playlistButton.setFocusPainted(false);

        playlistButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playlistButton.setIcon(playlistIconHover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                playlistButton.setIcon(playlistIcon);
            }
        });

        muteButton = new JButton(muteIcon);
        muteButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        muteButton.setToolTipText("Mute sound");
        muteButton.setBorderPainted(false);
        muteButton.setContentAreaFilled(false);
        muteButton.setFocusPainted(false);

        muteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {

                if(wasMuteClicked)
                    muteButton.setIcon(unmuteHoverIcon);
                else
                    muteButton.setIcon(muteHoverIcon);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {

                if(wasMuteClicked)
                    muteButton.setIcon(unmuteIcon);
                else
                    muteButton.setIcon(muteIcon);
            }
        });

        volumeUpButton = new JButton(volumeUpIcon);
        volumeUpButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        volumeUpButton.setToolTipText("Volume Up");
        volumeUpButton.setBorderPainted(false);
        volumeUpButton.setContentAreaFilled(false);
        volumeUpButton.setFocusPainted(false);

        volumeUpButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                volumeUpButton.setIcon(volumeUpHoverIcon);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                volumeUpButton.setIcon(volumeUpIcon);
            }
        });

        volumeDownButton = new JButton(volumeDownIcon);
        volumeDownButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        volumeDownButton.setToolTipText("Volume Down");
        volumeDownButton.setBorderPainted(false);
        volumeDownButton.setContentAreaFilled(false);
        volumeDownButton.setFocusPainted(false);

        volumeDownButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                volumeDownButton.setIcon(volumeDownHoverIcon);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                volumeDownButton.setIcon(volumeDownIcon);
            }
        });

        shuffleButton = new JButton(shuffleIcon);
        shuffleButton.setToolTipText("Shuffle playlist");
        shuffleButton.setBorderPainted(false);
        shuffleButton.setContentAreaFilled(false);
        shuffleButton.setFocusPainted(false);

        shuffleButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {

                if(wasShuffleClicked)
                    shuffleButton.setIcon(unShuffleHoverIcon);
                else
                    shuffleButton.setIcon(shuffleHoverIcon);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {

                if (wasShuffleClicked)
                    shuffleButton.setIcon(unShuffleIcon);
                else
                    shuffleButton.setIcon(shuffleIcon);
            }
        });

        repeatButton = new JButton(repeatIcon);
        repeatButton.setToolTipText("Repeat current song");
        repeatButton.setBorderPainted(false);
        repeatButton.setContentAreaFilled(false);
        repeatButton.setFocusPainted(false);

        repeatButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {

                if(wasRepeatClicked)
                    repeatButton.setIcon(noRepeatHoverIcon);
                else
                    repeatButton.setIcon(repeatHoverIcon);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {

                if(wasRepeatClicked)
                    repeatButton.setIcon(noRepeatIcon);
                else
                    repeatButton.setIcon(repeatIcon);
            }
        });

        addButton = new JButton(addDirectoryIcon);
        addButton.setToolTipText("Add songs directory");
        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false);
        addButton.setFocusPainted(false);

        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addButton.setIcon(addDirectoryHoverIcon);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addButton.setIcon(addDirectoryIcon);
            }
        });

        wipeButton = new JButton(wipeIcon);
        wipeButton.setToolTipText("Wipe playlist");
        wipeButton.setBorderPainted(false);
        wipeButton.setContentAreaFilled(false);
        wipeButton.setFocusPainted(false);

        wipeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                wipeButton.setIcon(wipeHoverIcon);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                wipeButton.setIcon(wipeIcon);
            }
        });

        BListener blistener = new BListener();
        prevButton.addActionListener(blistener);
        playPauseButton.addActionListener(blistener);
        nextButton.addActionListener(blistener);
        playlistButton.addActionListener(blistener);
        muteButton.addActionListener(blistener);
        volumeUpButton.addActionListener(blistener);
        volumeDownButton.addActionListener(blistener);
        shuffleButton.addActionListener(blistener);
        repeatButton.addActionListener(blistener);
        addButton.addActionListener(blistener);
        wipeButton.addActionListener(blistener);

        gdcMain.fill = GridBagConstraints.HORIZONTAL;;
        gdcMain.weightx = 100;

        gdcMain.gridx = 0;
        gdcMain.gridy = 0;
        mainPanel.add(addButton,gdcMain);

        gdcMain.gridx = 1;
        gdcMain.gridy = 0;
        mainPanel.add(wipeButton,gdcMain);

        gdcMain.gridx = 1;
        gdcMain.gridy = 2;
        mainPanel.add(prevButton,gdcMain);

        gdcMain.gridx = 2;
        gdcMain.gridy = 2;
        mainPanel.add(playPauseButton,gdcMain);

        gdcMain.gridx = 3;
        gdcMain.gridy = 2;
        mainPanel.add(nextButton,gdcMain);

        gdcMain.gridx = 0;
        gdcMain.gridy = 2;
        mainPanel.add(shuffleButton,gdcMain);

        gdcMain.gridx = 4;
        gdcMain.gridy = 2;
        mainPanel.add(repeatButton,gdcMain);

        gdcMain.gridx = 3;
        gdcMain.gridy = 0;
        gdcMain.gridwidth = 2;


        gdcUpperButtons.gridx = 0;
        upperButtonsPanel.add(volumeDownButton,gdcUpperButtons);

        gdcUpperButtons.gridx = 1;
        upperButtonsPanel.add(volumeUpButton,gdcUpperButtons);

        gdcUpperButtons.gridx = 2;
        upperButtonsPanel.add(muteButton,gdcUpperButtons);

        gdcUpperButtons.gridx = 3;
        upperButtonsPanel.add(playlistButton,gdcUpperButtons);

        mainPanel.add(upperButtonsPanel,gdcMain);

        songTimeBar = new JProgressBar(0,10); //MIN TO SONG LENGHT
        jProgressBarUpdate = new JProgressBarUpdate();
        gdcMain.gridx = 1;
        gdcMain.gridy = 3;
        gdcMain.ipady = 10;
        gdcMain.gridwidth = 3;
        mainPanel.add(songTimeBar,gdcMain);

    }

    /**
     * Runnable class used to update JProgressBar
     */
    public class JProgressBarUpdate implements Runnable{
        Thread timer;

        /**
         * Stops thread and sets flags
         */
        public void stop(){
            mustStop = true;
            somethingWasPlaying = false;
            try {
                timer.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        /**
         * Checks if thread is not running and starts new thread
         */
        public void start(){
            mustStop = false;
            if(!somethingWasPlaying) {
                timer = new Thread(new JProgressBarUpdate());
                timer.start();
                somethingWasPlaying = true;
            }
        }

        /**
         * Sets flag to pause thread
         */
        public void pause(){
            mustPause = true;
        }

        /**
         * Sets flag to resume thread
         */
        public void resume(){
            mustPause = false;
        }

        /**
         * Runs thread and update JProgressBar every second
         * If songs end starts next one or repeat current
         * using autoplay method from PlayerMain class
         */
        @Override
        public void run() {
            int i = 1;
            int max = fileManagement.timeToInt();
            songTimeBar.setMinimum(0);
            songTimeBar.setMaximum(max);
            try {
                while (i <= max) {
                    while(mustPause) {
                        Thread.sleep(100);
                        if(mustStop)
                            break;
                    }
                    if(mustStop)
                        break;
                    songTimeBar.setValue(i);
                    i++;
                    Thread.sleep(1000);
                }
                if(i >= max){
                    somethingWasPlaying = false;
                    if(!wasRepeatClicked) {
                        fileManagement.moveToNextSong();
                        maxSongTime.setText(fileManagement.getSongDuration());
                        songTextField.setText(fileManagement.getSongName());
                    }
                    PlayerMain.autoplay(fileManagement,jProgressBarUpdate);
                }
            } catch (InterruptedException ex) {
                songTimeBar.setValue(songTimeBar.getMaximum());
            }
        }
    }

    /**
     * Button listeners
     */
    private class BListener implements ActionListener {

        /**
         *
         * @param e event fired by one of the buttons
         */
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == prevButton) { //prev
                PlayerMain.moveToPrev(fileManagement,jProgressBarUpdate);
                if(!fileManagement.isFileEmpty()) {
                    maxSongTime.setText(fileManagement.getSongDuration());
                    songTextField.setText(fileManagement.getSongName());
                }
            }

            if(e.getSource() == playPauseButton) { //playPause
                wasPlayClicked = !wasPlayClicked;
                if(wasPlayClicked) {
                    playPauseButton.setIcon(pauseHoverIcon);
                    PlayerMain.pause = false;
                    if(!fileManagement.isFileEmpty()) {
                        maxSongTime.setText(fileManagement.getSongDuration());
                        songTextField.setText(fileManagement.getSongName());
                        jProgressBarUpdate.start();
                        jProgressBarUpdate.resume();
                        PlayerMain.startSong(fileManagement);
                    }

                }
                else {
                    jProgressBarUpdate.pause();
                    PlayerMain.pause = true;
                    playPauseButton.setIcon(playHoverIcon);
                }
            }

            if(e.getSource() == nextButton) {
                PlayerMain.moveToNext(fileManagement,jProgressBarUpdate);
                if(!fileManagement.isFileEmpty()) {
                    maxSongTime.setText(fileManagement.getSongDuration());
                    songTextField.setText(fileManagement.getSongName());
                }

            }

            if(e.getSource() == shuffleButton) { //shuffle
                wasShuffleClicked = !wasShuffleClicked;
                if(wasShuffleClicked) {
                    shuffleButton.setIcon(unShuffleHoverIcon);
                    fileManagement.shuffleList();
                }
                else {
                    shuffleButton.setIcon(shuffleHoverIcon);
                    fileManagement.sortList(true);
                }
            }

            if(e.getSource() == repeatButton) { //repeat
                wasRepeatClicked = !wasRepeatClicked;
                if(wasRepeatClicked) {
                    repeatButton.setIcon(noRepeatHoverIcon);
                    //play.repeat(true);
                }
                else
                    repeatButton.setIcon(repeatHoverIcon);
            }

            if(e.getSource() == muteButton) { //mute
                wasMuteClicked = !wasMuteClicked;
                if(wasMuteClicked){
                    muteButton.setIcon(unmuteHoverIcon);
                    PlayerMain.mute();
                }

                else {
                    muteButton.setIcon(muteHoverIcon);
                    PlayerMain.unmute();
                }
            }

            if(e.getSource() == playlistButton) { //playlist
                wasSettingsClicked = !wasSettingsClicked;
                if(wasSettingsClicked)
                    playlist = new Playlist(fileManagement);
                else
                    playlist.closeWindow();

            }

            if(e.getSource() == addButton) { //add
                fileManagement.addFiles();

            }

            if(e.getSource() == volumeUpButton) { //volume up
                PlayerMain.increaseVolume();
            }

            if(e.getSource() == volumeDownButton) { //volume down
                PlayerMain.decreaseVolume();
            }

            if(e.getSource() == wipeButton) { //wipe playlist
                fileManagement.wipePlaylist();
            }
        }

    }

}





