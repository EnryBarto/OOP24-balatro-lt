package it.unibo.balatrolt.model.api;

import java.util.Set;

import com.google.common.base.Optional;

/**
 * It models a supplier of statistics used to retrieve player, card and other statistics.
 * Although it's not required, it's preferable to pass immutable implementation of the provided classes.
 */
public interface ModifierStatsSupplier {
    /**
     * @return cards held by the player at the moment
     */
    Optional<Set<Card>> getHoldingCards();

    /**
     * @return cards played by the player in the last round. 
     */
    Optional<Set<Card>> getPlayedCards();

    /**
     * @return currency got by the player at the moment
     */
    Optional<Currency> getCurrentCurrency();

    /* [TODO: add combination getter] */
}
