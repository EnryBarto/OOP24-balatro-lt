package it.unibo.balatrolt.model.impl.combination;

import it.unibo.balatrolt.model.api.combination.BasePoints;

/**
 * Immutable class for representing {@link BasePoints}.
 * Simply is a wrapper of integers that represents
 * the amount of points scored.
 * @author Justin Carideo
 * @param basePoints
 */
public record BasePointsImpl(int basePoints) implements BasePoints {
}
