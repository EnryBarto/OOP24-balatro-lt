package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerCatalog;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerTier;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierBuilderImpl;

/**
 * A {@link JokerCatalog} containing rare jokers.
 * @author Nicolas Tazzieri
 */
public final class JokerCatalogRare extends AbstractJokerCatalog {
    private static final int MAX_RAND = 10;
    private static final String THE_TENTH_POINT = "the tenth point";
    private static final String THE_EIGHT_MULTIPLIER = "the eighth multiplier";
    private static final JokerTier TIER = JokerTier.RARE;
    private final JokerCatalog base = new JokerCatalogBase();
    private final JokerCatalog notCommon = new JokerCatalogNotCommon();

    private Joker generousHearther() {
        return super.getFactory()
                .merge("the generous hearther",
                    "it adds 12 multiplier if the played card contains a heart",
                    notCommon.getJoker("the hearther").get(),
                    base.getJoker(THE_EIGHT_MULTIPLIER).get(),
                    TIER);
    }

    private Joker madRand() {
        final Random r = new Random();
        return super.getFactory()
                .withModifierAndRandomPrice("the mad joker",
                    "it adds a random multiplier from 0 to 10 if you're holding an ace",
                    new ModifierBuilderImpl()
                            .addMultiplierModifier(m -> m + r.nextDouble(MAX_RAND))
                            .addHoldingCardBound(c -> checkContainsRank(c, Rank.ACE))
                            .build(),
                    TIER);
    }

    private Joker generousSpader() {
        return super.getFactory()
                .merge("the generous spader",
                    "it adds 20 base points if the played card contains a heart",
                    notCommon.getJoker("the spader").get(),
                    base.getJoker(THE_TENTH_POINT).get(),
                    TIER);
    }

    @Override
    protected Map<String, Joker> getJokersMap() {
        return Map.of(
            this.generousHearther().getName(), this.generousHearther(),
            this.generousSpader().getName(), this.generousSpader(),
            this.madRand().getName(), this.madRand()
        );
    }


    private boolean checkContainsRank(final Set<PlayableCard> cards, final Rank rank) {
        return cards.stream()
                .map(PlayableCard::getRank)
                .anyMatch(r -> r.equals(rank));
    }
}
