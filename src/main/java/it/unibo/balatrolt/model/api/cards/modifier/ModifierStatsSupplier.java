package it.unibo.balatrolt.model.api.cards.modifier;

import java.util.Set;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;

/**
 * It models a supplier of statistics used to retrieve player, card and other statistics.
 * It's used by {@link CombinationModifier} to verify whether the modifiers can be issued or not.
 * Although it's not required, it's preferable to use immutable implementation of the required classes.
 */
public interface ModifierStatsSupplier {
    /**
     * @return cards held by the player at the moment
     */
    Optional<Set<PlayableCard>> getHoldingCards();

    /**
     * @return cards played by the player in the last round.
     */
    Optional<Set<PlayableCard>> getPlayedCards();

    /**
     * @return currency got by the player at the moment
     */
    Optional<Integer> getCurrentCurrency();

    /**
     * @return current combination type
     */
    Optional<CombinationType> getCurrentCombinationType();
}
