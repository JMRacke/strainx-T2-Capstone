package ui.gui;

import gamecontrol.GameDifficulty;
import gamecontrol.GlobalVariables;
import gamemodel.mapengine.MainMap;
import gamemusic.AudioPlayer;
import ui.gui.components.MapPanel;
import ui.gui.components.StatusPanel;
import ui.gui.components.WrapperPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUIEntry {
    public static void start() throws IOException {

        // TEST StatsPanel
        JFrame jFrame = new JFrame();

//        jFrame.add(new WrapperPanel());
        jFrame.add(new TitlePanel());

        jFrame.setResizable(false);
        jFrame.setTitle("StrainX");
        //jFrame.setPreferredSize(new Dimension(1024,768));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}