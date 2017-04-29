package pl.musicplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Damian on 029 29 kwietnia.
 */
public class InterfaceMain extends JFrame {
    protected JButton prevButton;
    protected JButton nextButton;
    protected JButton playPauseButton;

    protected JPanel mainPanel;

    InterfaceMain(){
        createWindow();
    }

    private void createWindow(){
        this.setSize(400,400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.setTitle("Music Player 1.0");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        addButtons();
        this.add(mainPanel);

        this.setVisible(true);
    }

    private void addButtons(){
        ImageIcon playIcon = new ImageIcon("icons/Play.png");

        prevButton = new JButton();

        playPauseButton = new JButton(playIcon);
        playPauseButton.setBorderPainted(false);
        playPauseButton.setContentAreaFilled(false);
        playPauseButton.setFocusPainted(false);

        nextButton = new JButton();

        JButton buttonTable[] = {prevButton,playPauseButton,nextButton};
        BListener blistener = new BListener(buttonTable);
        prevButton.addActionListener(blistener);
        playPauseButton.addActionListener(blistener);
        nextButton.addActionListener(blistener);

        mainPanel.add(prevButton);
        mainPanel.add(playPauseButton);
        mainPanel.add(nextButton);
    }



}



