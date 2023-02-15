package ui.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.System.exit;

public class ContinueButton extends JButton implements ActionListener {
    public ContinueButton() {
        this.setText("Continue..");
        this.setBackground(Color.GREEN);
        this.setOpaque(true);
        this.setBorderPainted(false);
        this.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Clear the contents of the Frame and then draw the main map UI (Lorenzo's classes) to the screen

        // Placeholder
        exit(0);
    }
}
