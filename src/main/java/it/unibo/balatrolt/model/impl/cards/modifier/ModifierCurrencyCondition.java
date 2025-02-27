package it.unibo.balatrolt.model.impl.cards.modifier;

import static com.google.common.base.Preconditions.checkState;

import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;

/**
 * Implementation of ConditionalModifier checking if the currency held by the player satisfies the specified condition.
 * @author Nicolas Tazzieri
 */
public final class ModifierCurrencyCondition extends ConditionalModifier<Integer> {

    /**
     * @param modifier base modifier
     * @param condition condition on currency to satisfy
     */
    public ModifierCurrencyCondition(final CombinationModifier modifier, final Predicate<Integer> condition) {
        super(modifier, condition);
    }

    @Override
    protected boolean checkCondition() {
        final var getCurrentCurrency = super.getStats().get().getCurrentCurrency();
        checkState(getCurrentCurrency.isPresent(), "Current currency is required");
        return super.getCondition().test(getCurrentCurrency.get());
    }
}
