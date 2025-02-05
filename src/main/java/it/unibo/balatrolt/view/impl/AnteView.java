package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.AnteInfo;
/**
 * Displays the ante and relative blind info.
 */
public class AnteView extends JPanel {
    static final long serialVersionUID = 1L;

    /**
     * Builds the GUI.
     * @param controller master controller.
     * @param anteInfo info's of the current ante.
     */
    public AnteView(final MasterController controller, final AnteInfo anteInfo) {
        super(new BorderLayout());
        final JButton button = new JButton("START");
        button.addActionListener(a -> controller.handleEvent(BalatroEvent.SHOW_BLINDS, null));
        final JTextArea text = new JTextArea();
        text.setEditable(false);
        text.append("Current Ante: " + anteInfo.id());
        text.append("\n\nBLINDS:");
        for (int i = 0; i < anteInfo.blinds().size(); i++) {
            text.append("\n\nBlind " + anteInfo.blinds().get(i).id());
            if (i == anteInfo.currentBlindId()) {
                text.append(" (CURRENT BLIND)");
            }
            text.append("\n    Chips Required: " + anteInfo.blinds().get(i).minimumChips());
            text.append("\n    Reward: " + anteInfo.blinds().get(i).reward());
        }
        add(text, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        setVisible(true);
    }

}
