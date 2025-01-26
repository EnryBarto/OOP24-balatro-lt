package it.unibo.balatrolt.model.impl;

import java.util.Iterator;
import java.util.stream.Collectors;

import it.unibo.balatrolt.model.api.CombinationRecognizer;
import it.unibo.balatrolt.model.api.CombinationRecognizerHelpers;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;

public class CombinationRecognizerHelpersImpl implements CombinationRecognizerHelpers {

    @Override
    public CombinationRecognizer highCardRecognizer() {
        return hand -> !hand.isEmpty();
    }

    @Override
    public CombinationRecognizer pairRecognizer() {
        return hand -> hand.size() >= 2L &&
            hand.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()))
                .entrySet()
                .stream()
                .anyMatch(e -> e.getValue() == 2L);
    }

    @Override
    public CombinationRecognizer twoPairRecognizer() {
        return hand -> hand.size() >= 4L &&
            hand.stream()
            .collect(Collectors.groupingBy(p -> p, Collectors.counting()))
            .entrySet()
            .stream()
            .filter(e -> e.getValue() == 2L)
            .toList().size() == 2L;
    }

    @Override
    public CombinationRecognizer threeOfAKindRecognizer() {
        return hand -> hand.size() >= 3L  &&
            hand.stream()
                .collect(Collectors.groupingBy(p -> p.getRank(), Collectors.counting()))
                .entrySet()
                .stream()
                .anyMatch(e -> e.getValue() == 3L);
    }

    @Override
    public CombinationRecognizer straightRecognizer() {
        return hand -> {
            Iterator<Rank> sorted = SortingPlayableHelpers.sortingByRank(hand)
                .stream()
                .map(p -> p.getRank())
                .iterator();
            return false;
        };
    }

    @Override
    public CombinationRecognizer flushRecognizer() {
        return hand ->  hand.stream()
            .collect(Collectors.groupingBy(p -> p.getSuit(), Collectors.counting()))
            .entrySet()
            .stream()
            .anyMatch(e -> e.getValue() == 5);
    }

    @Override
    public CombinationRecognizer fullHouseRecognizer() {
        return hand -> pairRecognizer().recognize(hand) &&
            threeOfAKindRecognizer().recognize(hand);
    }

    @Override
    public CombinationRecognizer fourOfAKindRecognizer() {
        return hand -> !hand.isEmpty() &&
            hand.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()))
                .entrySet()
                .stream()
                .anyMatch(e -> e.getValue() == 4L);
    }

    @Override
    public CombinationRecognizer straightFlushRecognizer() {
        return hand -> straightRecognizer().recognize(hand) &&
            flushRecognizer().recognize(hand);
    }

    @Override
    public CombinationRecognizer royalFlushRecognizer() {
        return hand -> !hand.isEmpty();
    }
}
