from typing import List
from .combination import (
    Pair, TwoPairs, ThreeOfKind, FourOfKind, FiveOfKind,
    FullHouse, SmallStraight, LargeStraight, Chance, NumberCombination, Combination
)
from .combination_type import CombinationType

def create_all() -> List[Combination]:
    combos: List[Combination] = []
    # Upper section
    combos.append(NumberCombination(1, CombinationType.ONES))
    combos.append(NumberCombination(2, CombinationType.TWOS))
    combos.append(NumberCombination(3, CombinationType.THREES))
    combos.append(NumberCombination(4, CombinationType.FOURS))
    combos.append(NumberCombination(5, CombinationType.FIVES))
    combos.append(NumberCombination(6, CombinationType.SIXES))
    # Lower section
    combos.append(Pair())
    combos.append(TwoPairs())
    combos.append(ThreeOfKind())
    combos.append(FourOfKind())
    combos.append(FiveOfKind())
    combos.append(FullHouse())
    combos.append(SmallStraight())
    combos.append(LargeStraight())
    combos.append(Chance())
    return combos