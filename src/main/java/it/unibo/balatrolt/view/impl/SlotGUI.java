package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;

/**
 * Creates the part of the GUI containing the
 * slots for the cards (any type).
 * @author Bendetti Nicholas
 */
public final class SlotGUI extends JPanel {
    private static final float BASE_WEIGHT = 0.2f;
    private static final int RIDIM = 28;
    private static final int MAX_SPECIAL_SLOT = 5;
    private static final int MAX_CARD_SLOT = 5;
    private static final int MAX_HAND_SLOT = 7;
    private final MasterController controller;

    /**
     * Sets the controller and the number of
     * hand and special card slot.
     */
    public SlotGUI(MasterController controller, List<PlayableCardInfo> cards, List<SpecialCardInfo> specialCards) throws IOException {
        super(new GridBagLayout());
        this.setBackground(Color.green.darker().darker().darker());
        this.controller = Preconditions.checkNotNull(controller);

        buildSpecialSlot(specialCards.stream()
            .map(card -> card.name())
            .toList());
        buildCardSlot(List.of());
        buildHandSlot(cards.stream()
            .map(card -> card.rank() + card.suit())
            .toList());
    }

    /**
     * Updates the special slot with the new cards.
     */
    public void updateSpecialSlot(List<String> specialCards) {
        buildSpecialSlot(specialCards);
    }

    /**
     * Updates the card slot with the new cards.
     */
    public void updateCardSlot(List<String> playedCards) {
        buildCardSlot(playedCards);
    }

    /**
     * Updates the hand slot with the new cards.
     * @param hand the new cards to show.
     */
    public void updateHand(List<String> hand) {
        buildHandSlot(hand);
    }

    /**
     * Builds the special slot.
     */
    private void buildSpecialSlot(List<String> specialCards) {
        Preconditions.checkNotNull(specialCards);
        Preconditions.checkArgument(specialCards.size() <= MAX_SPECIAL_SLOT);
        final JPanel speciaSlot = new JPanel(new GridLayout(1, specialCards.size()));
        final GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < MAX_SPECIAL_SLOT; i++) {
            final JButton card = new JButton();
            try {
                final Image img = ImageIO.read(getClass().getResource("/JOKER.png"));
                card.setIcon(new ImageIcon(img));
                speciaSlot.add(card);
            } catch (IOException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = BASE_WEIGHT;
        gbc.weighty = BASE_WEIGHT;
        gbc.insets = new Insets(RIDIM, RIDIM, RIDIM, RIDIM);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(speciaSlot, gbc);
    }

    /**
     * Builds the card slot.
     */
    private void buildCardSlot(List<String> playedCards) {
        Preconditions.checkNotNull(playedCards);
        Preconditions.checkArgument(playedCards.size() <= MAX_CARD_SLOT);
        final JPanel cardSlot = new JPanel(new GridLayout(1, playedCards.size()));
        final GridBagConstraints gbc = new GridBagConstraints();

        for (String cardName : playedCards) {
            final JButton card = new JButton();
            card.setBackground(getBackground());
            try {
                final Image img = ImageIO.read(getClass().getResource("/" + cardName + ".png"));
                card.setIcon(new ImageIcon(img));
                cardSlot.add(card);
            } catch (IOException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = BASE_WEIGHT;
        gbc.weighty = BASE_WEIGHT;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, RIDIM, RIDIM, RIDIM);
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(cardSlot, gbc);
    }

    /**
     * Builds the hand slot.
     * @param hand the cards to show.
     */
    private void buildHandSlot(List<String> hand) {
        Preconditions.checkNotNull(hand);
        Preconditions.checkArgument(hand.size() <= MAX_HAND_SLOT);
        final JPanel handSlot = new JPanel(new GridLayout(1, MAX_HAND_SLOT));
        final GridBagConstraints gbc = new GridBagConstraints();

        for (final String cardName : hand) {
            final JButton card = new JButton();
            card.setBackground(getBackground());
            try {
                final Image img = ImageIO.read(getClass().getResource("/" + cardName + ".png"));
                card.setIcon(new ImageIcon(img));
                handSlot.add(card);
            } catch (IOException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = BASE_WEIGHT;
        gbc.weighty = BASE_WEIGHT;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, RIDIM, RIDIM, RIDIM);
        gbc.anchor = GridBagConstraints.PAGE_END;
        this.add(handSlot, gbc);
    }
}
