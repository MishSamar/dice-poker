from typing import Dict
from .combination_type import CombinationType


class ScoreTable:
    def __init__(self):
        self.scores: Dict[CombinationType, int] = {}

    def record(self, comb_type: CombinationType, score: int):
        self.scores[comb_type] = score

    def used(self, comb_type: CombinationType) -> bool:
        return comb_type in self.scores

    def is_complete(self) -> bool:
        return len(self.scores) == len(CombinationType)

    def total(self) -> int:
        base = sum(self.scores.values())
        upper = sum(self.scores.get(t, 0) for t in [
            CombinationType.ONES, CombinationType.TWOS, CombinationType.THREES,
            CombinationType.FOURS, CombinationType.FIVES, CombinationType.SIXES
        ])
        bonus = 50 if upper >= 63 else 0
        return base + bonus
