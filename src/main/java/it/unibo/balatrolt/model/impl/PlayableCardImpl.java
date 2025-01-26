package it.unibo.balatrolt.model.impl;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.PlayableCard;

public class PlayableCardImpl implements PlayableCard {

    private final Pair<Rank, Suit> card;
    private final Rank rank;
    private final Suit suit;

    public PlayableCardImpl(final Pair<Rank, Suit> card) {
        this.card = card;
        this.rank = card.get1();
        this.suit = card.get2();
    }

    @Override
    public Rank getRank() {
        return this.card.get1();
    }

    @Override
    public Suit getSuit() {
        return this.card.get2();
    }

    @Override
    public Optional<Modifier> getModifier() {
        return Optional.absent();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayableCardImpl other = (PlayableCardImpl) obj;
        if (rank != other.rank)
            return false;
        if (suit != other.suit)
            return false;
        return true;
    }
}
