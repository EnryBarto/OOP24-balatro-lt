package it.unibo.balatrolt.view.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.google.common.base.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.view.api.ShopInnerLogic;
import it.unibo.balatrolt.view.api.ShopView;

/**
 * Implementation of {@link ShopView}.
 * It also extends a {@link JPanel}, so it can be used to replace an existing one.
 */
@SuppressFBWarnings(
    justification = """
        Since we extend JPanel (which is Serializable), it's required to make the class Serializable,
        otherwhise an exception will be thrown when serializing this class.
        Anyway we are sure that we will never serialize this class, because if we want to save the game
        we will only save the informations stored in the model, creating a new View when needed.
        """,
    value = "SE_BAD_FIELD"
)
public final class ShopViewImpl extends JPanel implements ShopView {
    private static final int HORIZONTAL_PADDING = 20;
    private static final int VERTICAL_PADDING = 10;
    static final long serialVersionUID = 1L;
    private static final String FONT = "COPPER_BLACK";
    private static final float TITLE_SIZE = 100f;
    private static final float PRICE_SIZE = 23f;
    private static final float BUTTON_SIZE = 18f;
    private static final int INFO_Y = 3;
    private static final int NAME_Y = 2;
    private static final int CARD_Y = 1;
    private static final int N_SLOT_ROWS = 1;
    private static final float GBC_WEIGHT = 0.2f;

    private final JPanel innerPanel = new JPanel(new FlowLayout());
    private final List<JButton> cardButtons = new LinkedList<>();
    private final FontFactory fontFactory = new FontFactory();
    private final MasterController controller;
    private final ShopInnerLogic logic;
    private final JButton buyButton;

    /**
     * Constructor.
     * @param controller controller to attach.
     */
    public ShopViewImpl(final MasterController controller) {
        super(new BorderLayout());
        this.setBackground(Color.GREEN.darker().darker().darker().darker());
        final var shopTitle = new JLabel("Shop");
        shopTitle.setForeground(Color.WHITE);
        shopTitle.setFont(this.fontFactory.getFont(FONT, TITLE_SIZE, this));
        final var titlePanel = new JPanel(new FlowLayout());
        titlePanel.setBackground(this.getBackground());
        titlePanel.add(shopTitle);
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(innerPanel, BorderLayout.CENTER);
        this.logic = new ShopInnerLogicImpl();
        this.controller = checkNotNull(controller);
        this.buyButton = new JButton("Buy");
        this.buyButton.addActionListener(e -> {
            this.controller.handleEvent(BalatroEvent.BUY_CARD, this.logic.getSelectedCard());
        });
        this.add(this.getBuyOrContinuePanel(), BorderLayout.SOUTH);
    }

    @Override
    public void updateCards(final Set<SpecialCardInfo> toSell) {
        checkNotNull(toSell);
        this.cardButtons.clear();
        this.logic.reset();
        this.buildInnerPanel(toSell);
        this.redraw();
    }

    private void buildInnerPanel(final Set<SpecialCardInfo> toSell) {
        this.innerPanel.removeAll();
        this.innerPanel.setBackground(this.getBackground());
        final JPanel cardContainer = new JPanel(new GridLayout(N_SLOT_ROWS, toSell.size()));
        for (final var card : toSell) {
            cardContainer.add(this.getCardWithPriceLblPanel(card.name(), card.description(), card.price()));
        }
        this.innerPanel.add(cardContainer);
    }

    private JPanel getCardWithPriceLblPanel(final String name, final String desc, final int price) {
        final JPanel panel = new JPanel(new GridBagLayout());
        final JButton card = getIconButton("/img/JOKER.png", e -> {
            this.logic.hitCard(new SpecialCardInfo(name, desc, price));
            this.redraw();
            final var btn = (JButton) e.getSource();
            if (this.logic.isCardSelected()) {
                btn.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        });
        this.cardButtons.add(card);
        final JButton info = getIconButton("/img/INFO.png", e -> {
            JOptionPane.showMessageDialog(this, desc, name, JOptionPane.INFORMATION_MESSAGE);
        });
        info.setBorder(BorderFactory.createEmptyBorder());
        panel.add(getPriceLable(price), getGBConstraints(0, 0));
        final JLabel nameLabel = new JLabel(name, JLabel.CENTER);
        nameLabel.setForeground(Color.WHITE);
        panel.add(card, getGBConstraints(0, CARD_Y));
        panel.add(nameLabel, getGBConstraints(0, NAME_Y));
        panel.add(info, getGBConstraints(0, INFO_Y));
        panel.setBackground(this.getBackground());
        panel.setBorder(BorderFactory.createEmptyBorder(
            VERTICAL_PADDING,
            HORIZONTAL_PADDING,
            VERTICAL_PADDING,
            HORIZONTAL_PADDING
        ));
        return panel;
    }

    private JButton getIconButton(final String path, final ActionListener action) {
        final var btn = new JButton();
        btn.addActionListener(action);
        try {
            final Image img = ImageIO.read(getClass().getResource(path));
            btn.setIcon(new ImageIcon(img));
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(this, "Image could not be loaded: " + path, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        btn.setContentAreaFilled(false);
        return btn;
    }

    private JLabel getPriceLable(final int price) {
        final var lbl = new JLabel(Integer.toString(price) + "$");
        lbl.setFont(this.fontFactory.getFont(FONT, PRICE_SIZE, this));
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    private JPanel getBuyOrContinuePanel() {
        final var panel = new JPanel(new FlowLayout());
        final JButton continueGame = new JButton("Continue");
        continueGame.setFont(this.fontFactory.getFont(FONT, BUTTON_SIZE, this));
        this.buyButton.setFont(continueGame.getFont());
        this.buyButton.setBackground(Color.decode("#C1121F"));
        this.buyButton.setForeground(Color.WHITE);
        this.buyButton.setPreferredSize(continueGame.getPreferredSize());
        continueGame.setBackground(Color.decode("#2274A5"));
        continueGame.setForeground(Color.WHITE);
        continueGame.addActionListener(e -> {
            this.controller.handleEvent(BalatroEvent.CLOSE_SHOP, Optional.absent());
        });
        panel.add(buyButton);
        panel.add(continueGame);
        panel.setBackground(this.getBackground());
        return panel;
    }

    private GridBagConstraints getGBConstraints(final int x, final int y) {
        final var gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weighty = GBC_WEIGHT;
        gbc.weightx = GBC_WEIGHT;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    private void redraw() {
        this.buyButton.setEnabled(this.logic.isCardSelected());
        this.cardButtons.forEach(e -> e.setBorder(
            BorderFactory.createLineBorder(e.getParent().getBackground())));
        this.repaint();
    }
}
