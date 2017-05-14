package pl.musicplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * Created by Damian on 029 29 kwietnia.
 */

public class InterfaceMain extends JFrame {

    FileManagement fileManagement;

    protected JButton prevButton;
    protected JButton nextButton;
    protected JButton playPauseButton;
    protected JButton settingsButton;
    protected JButton muteButton;
    protected JButton volumeUpButton;
    protected JButton volumeDownButton;
    protected JButton shuffleButton;
    protected JButton repeatButton;
    protected boolean wasPlayClicked = false;
    protected boolean wasShuffleClicked = false;
    protected boolean wasRepeatClicked = false;
    protected boolean wasMuteClicked = false;

    protected JProgressBar songTimeBar;

    protected JTextField songTextField;
    protected JTextField minSongTime;
    protected JTextField maxSongTime;

    protected GridBagConstraints gdcMain;
    protected GridBagConstraints gdcUpperButtons;
    protected GridBagConstraints gdcSongTimeBar;

    protected JPanel mainPanel;
    protected JPanel upperButtonsPanel;
    protected JPanel songTimeBarPanel;

    protected final ImageIcon playIcon = new ImageIcon("icons/03.png");
    protected final ImageIcon playHoverIcon = new ImageIcon("icons/03active.png");

    protected final ImageIcon pauseIcon = new ImageIcon("icons/03_1.png");
    protected final ImageIcon pauseHoverIcon = new ImageIcon("icons/03_1active.png");

    protected final ImageIcon prevIcon = new ImageIcon("icons/02_2.png");
    protected final ImageIcon prevHoverIcon = new ImageIcon("icons/02_2active.png");

    protected final ImageIcon nextIcon = new ImageIcon("icons/02.png");
    protected final ImageIcon nextHoverIcon = new ImageIcon("icons/02active.png");

    protected final ImageIcon settingsIcon = new ImageIcon("icons/12.png");
    protected final ImageIcon settingsHoverIcon = new ImageIcon("icons/12active.png");

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

    InterfaceMain(FileManagement pfileManagement){
        this.fileManagement = pfileManagement;
        createWindow();
    }

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

        //TODO It should be puted in separate method
        //PROPABLY IN WRONG PLACING ORDER. SHOULD BE 2ND
        gdcMain.gridx = 2;
        gdcMain.gridy = 1;
        gdcMain.ipadx = 300;
        gdcMain.ipady = 8;
        gdcMain.fill = GridBagConstraints.CENTER;
        gdcMain.fill = GridBagConstraints.REMAINDER;

        songTextField = new JTextField("PREVIEW");
        songTextField.setEditable(false);
        songTextField.setBorder(null);
        songTextField.setHorizontalAlignment(JTextField.CENTER);
        Font songTextFont = new Font("AppleGothic",Font.PLAIN,15);
        songTextField.setFont(songTextFont);
        mainPanel.add(songTextField,gdcMain);

        minSongTime = new JTextField("0:00");
        minSongTime.setEditable(false);
        minSongTime.setBorder(null);
        minSongTime.setHorizontalAlignment(JTextField.CENTER);

        gdcMain.gridx = 0;
        gdcMain.gridy = 3;
        mainPanel.add(minSongTime,gdcMain);

        maxSongTime = new JTextField("4:20");
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

        settingsButton = new JButton(settingsIcon);
        settingsButton.setToolTipText("Settings");
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);

        settingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                settingsButton.setIcon(settingsHoverIcon);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                settingsButton.setIcon(settingsIcon);
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

        BListener blistener = new BListener();
        prevButton.addActionListener(blistener);
        playPauseButton.addActionListener(blistener);
        nextButton.addActionListener(blistener);
        settingsButton.addActionListener(blistener);
        muteButton.addActionListener(blistener);
        volumeUpButton.addActionListener(blistener);
        volumeDownButton.addActionListener(blistener);
        shuffleButton.addActionListener(blistener);
        repeatButton.addActionListener(blistener);

        gdcMain.fill = GridBagConstraints.HORIZONTAL;;
        gdcMain.weightx = 100;

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
        upperButtonsPanel.add(settingsButton,gdcUpperButtons);

        mainPanel.add(upperButtonsPanel,gdcMain);

        songTimeBar = new JProgressBar(0,10); //MIN TO SONG LENGHT
        gdcMain.gridx = 1;
        gdcMain.gridy = 3;
        gdcMain.ipady = 10;
        gdcMain.gridwidth = 3;
        mainPanel.add(songTimeBar,gdcMain);

    }

    private void setSongText(String text){

    }
    /**
     * Response to button clicked
     */
    private class BListener implements ActionListener {

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == prevButton) //prev
                System.out.println("do something 1");

            if(e.getSource() == playPauseButton) { //playPause
                wasPlayClicked = !wasPlayClicked;
                if(wasPlayClicked) {
                    playPauseButton.setIcon(pauseHoverIcon);
                    //play.song(fileManagement.getCurrentSongName());
                }
                else {
                    //play.pause(true);
                    //TODO how to continue
                    playPauseButton.setIcon(playHoverIcon);
                }
            }

            if(e.getSource() == nextButton) {
                fileManagement.moveToNextSong();
                //play.stop();
                //play.song(fileManagement.getCurrentSongName());
                //play.song(fileManagement.getCurrentSongName());
            }

            if(e.getSource() == shuffleButton) { //playPause
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

            if(e.getSource() == repeatButton) { //playPause
                wasRepeatClicked = !wasRepeatClicked;
                if(wasRepeatClicked) {
                    repeatButton.setIcon(noRepeatHoverIcon);
                    //play.repeat(true);
                }
                else
                    repeatButton.setIcon(repeatHoverIcon);
            }

            if(e.getSource() == muteButton) { //playPause
                wasMuteClicked = !wasMuteClicked;
                if(wasMuteClicked)
                    muteButton.setIcon(unmuteHoverIcon);
                else
                    muteButton.setIcon(muteHoverIcon);
            }
        }

    }

}





