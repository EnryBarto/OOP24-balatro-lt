package it.unibo.balatrolt.model.impl.modifier;

import java.util.function.Predicate;
import it.unibo.balatrolt.model.api.Modifier;

public class ConditionalModifier<X> extends ModifierDecorator {
    public ConditionalModifier(Predicate<X> condition, Modifier modifier) {
        super(modifier);
    }
}
