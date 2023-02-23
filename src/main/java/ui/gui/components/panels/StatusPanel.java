package ui.gui.components.panels;

import gamecontrol.contents.CrewMember;
import ui.gui.components.HealthBar;
import ui.gui.components.dialogs.HelpMapDialog;
import ui.gui.components.dialogs.InventoryDialog;
import ui.gui.components.LoadImage;
import ui.gui.components.buttons.SettingsButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StatusPanel extends JPanel{

    private JPanel playerContainer;
    private JPanel buttonContainer;

    private static InventoryDialog inventoryDialog;

    private HelpMapDialog helpMapDialog;

    public StatusPanel(List<CrewMember> players ){
        setLayout(new BorderLayout());
        add(addContainerPlayerSubPanels(players), BorderLayout.WEST);
        add(addContainerOfButtons(this), BorderLayout.EAST);
    }

    private JPanel addContainerPlayerSubPanels(List<CrewMember> players ){
        JPanel playerContainer = new JPanel();
        playerContainer.setLayout(new GridLayout(2, 2));
        JPanel container = addCrew(players, playerContainer);
        setPlayerContainer(container);
        return container;
    }
    private JPanel addContainerOfButtons(StatusPanel statusPanel){
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(3, 1));
        container.add(inventoryButton(statusPanel));
        container.add(helpButton(statusPanel));
        container.add(new SettingsButton());

        setButtonContainer(container);
        return container;
    }

    private JButton helpButton(StatusPanel statusPanel){
        JButton helpButton = new JButton();
        helpButton.setText("Help");
        helpButton.setVisible(true);
        add(helpButton);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (helpMapDialog == null || !helpMapDialog.isDisplayable()) {
                    helpMapDialog = new HelpMapDialog((JFrame) statusPanel.getTopLevelAncestor());
                }
            }
        });
        return helpButton;
    }

    private JButton inventoryButton(StatusPanel statusPanel) {
        JButton invButton = new JButton();
        invButton.setText("Inventory");
        invButton.setVisible(true);
        add(invButton);
        invButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getInventoryDialog() == null || !getInventoryDialog().isDisplayable()) {
                    setInventoryDialog(new InventoryDialog((JFrame) statusPanel.getTopLevelAncestor()));
                }
            }
        });
        return invButton;
    }
    private JPanel addCrew(List<CrewMember> players, JPanel container){
        int n = 0;
        int playerNum = 1;
        for (CrewMember crewMember : players) {
            JPanel p = new JPanel();
            p.setLayout(new FlowLayout());

            JLabel nameLabel = new JLabel(String.format(playerNum + ". %s %s | Attack : %d | HP: ",
                    crewMember.getRank(),crewMember.getName(),
                    (crewMember.getAttack() + crewMember.getWeapon().getWeapon_base_dmg())));
            nameLabel.setIcon(LoadImage.getIcon("images/soldier.png"));
            p.add(nameLabel);
            if(crewMember.getHP() < 0) { crewMember.setHP(0);}
            p.add(new HealthBar(crewMember));
            container.add(p);
            playerNum++;
        }
        return container;
    }

    public void setPlayerContainer(JPanel playerContainer) {this.playerContainer = playerContainer;}
    public void setButtonContainer(JPanel buttonContainer) {this.buttonContainer = buttonContainer;}
    public static InventoryDialog getInventoryDialog() { return inventoryDialog; }
    public static void setInventoryDialog(InventoryDialog inventoryDialog) { StatusPanel.inventoryDialog = inventoryDialog; }
}