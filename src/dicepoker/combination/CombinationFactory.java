package dicepoker.combination;

import java.util.ArrayList;
import java.util.List;

import dicepoker.model.CombinationType;

public class CombinationFactory {
    public static List<Combination> createAll(){
        List<Combination> list=new ArrayList<>();
        list.add(new Pair()); list.add(new TwoPairs()); list.add(new ThreeOfKind());
        list.add(new FourOfKind()); list.add(new FiveOfKind()); list.add(new FullHouse());
        list.add(new Straight(CombinationType.SMALL_STRAIGHT));
        list.add(new Straight(CombinationType.LARGE_STRAIGHT));
        list.add(new Chance());
        list.add(new NumberCombination(CombinationType.ONES,1));
        list.add(new NumberCombination(CombinationType.TWOS,2));
        list.add(new NumberCombination(CombinationType.THREES,3));
        list.add(new NumberCombination(CombinationType.FOURS,4));
        list.add(new NumberCombination(CombinationType.FIVES,5));
        list.add(new NumberCombination(CombinationType.SIXES,6));
        return list;
    }
}
