package pl.musicplayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Damian on 029 29 kwietnia.
 */

/**
 * It may be putted into InterfaceMain class
 */


public class BListener implements ActionListener {
    JButton buttonTable[];
    BListener(JButton array[]){
        this.buttonTable = array;
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == buttonTable[0])
            System.out.println("do something 1");
        if(e.getSource() == buttonTable[1])
            System.out.println("do something 2");
        if(e.getSource() == buttonTable[2])
            System.out.println("do something 3");
    }

}
