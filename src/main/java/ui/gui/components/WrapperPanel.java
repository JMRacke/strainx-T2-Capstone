package ui.gui.components;

import gamecontrol.GlobalVariables;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WrapperPanel extends JPanel {

    public WrapperPanel(){
        setBorder(new LineBorder(Color.CYAN));
        setPreferredSize( new Dimension(1024,768));
        setLayout(new BorderLayout());
        add(new StatusPanel(GlobalVariables.mySquad),BorderLayout.NORTH);
        add(new MapPanel(),BorderLayout.CENTER);
        addComponentListener( adjustSubPanelDimensions(this) );
    }

    public ComponentAdapter adjustSubPanelDimensions(JPanel parent){
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
//                super.componentResized(e);
                int thisHeight = parent.getHeight();
                int thisWidth = parent.getWidth();

                parent.getComponent(0).setPreferredSize(new Dimension(thisWidth, (int) ((thisHeight)*.10)));
                parent.getComponent(1).setPreferredSize(new Dimension(thisWidth, (int) (thisHeight*.90)));
                parent.revalidate();
            }
        };
    }

}