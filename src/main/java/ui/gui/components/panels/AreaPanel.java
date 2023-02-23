package ui.gui.components.panels;

import gamemodel.mapengine.SubArea;
import ui.gui.components.buttons.SubareaButton;
import ui.maps.UIEnterMainMap;
import ui.gui.components.buttons.SubareaButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class AreaPanel extends JPanel {

    public AreaPanel(Integer keyNumber, List<SubArea> subareasList){
        setBorder(new LineBorder(Color.darkGray));
        createSubareaPanels(this,subareasList, keyNumber);
        setLayout(new BoxLayout( this,BoxLayout.Y_AXIS));
    }

    private Integer createSubareaPanels(JPanel area,List<SubArea> subAreaList,Integer areaNumber){
        UIEnterMainMap.threatLvlMapInitialize();

        area.add(new JLabel(String.format("Area %d",areaNumber)));
        for (SubArea subArea : subAreaList) {

            SubareaPanel subareaPanel = new SubareaPanel(subArea);// pass subarea obj to subarea panel
            area.add(subareaPanel);
            JButton toggleSubarea = new SubareaButton(subArea,subareaPanel); // btn to toggle show/hide subarea panel
            subareaPanel.add(toggleSubarea);

            area.add(toggleSubarea);
            area.add(subareaPanel);
        }
        return 1;
    }

}