from typing import List

# from dice_utils import small_straight_score
from .combination_type import CombinationType
from .dice_utils import count_faces, is_small_straight, is_large_straight, small_straight_score

class Combination:
    def get_type(self) -> CombinationType:
        raise NotImplementedError

    def matches(self, dices: List[int]) -> bool:
        raise NotImplementedError

    def score(self, dices: List[int]) -> int:
        raise NotImplementedError

class NumberCombination(Combination):
    def __init__(self, face: int, comb_type: CombinationType):
        self.face = face
        self.type = comb_type

    def get_type(self) -> CombinationType:
        return self.type

    def matches(self, dices: List[int]) -> bool:
        return True

    def score(self, dices: List[int]) -> int:
        return dices.count(self.face) * self.face

class Pair(Combination):
    def get_type(self): return CombinationType.ONE_PAIR
    def matches(self, dices): return any(cnt >= 2 for cnt in count_faces(dices).values())
    def score(self, dices): return max((f*2 for f,c in count_faces(dices).items() if c>=2), default=0)

class TwoPairs(Combination):
    def get_type(self): return CombinationType.TWO_PAIRS
    def matches(self, dices): return len([f for f,c in count_faces(dices).items() if c>=2]) >= 2
    def score(self, dices):
        faces = sorted([f for f,c in count_faces(dices).items() if c>=2], reverse=True)
        return (faces[0]*2 + faces[1]*2) if len(faces)>=2 else 0

class ThreeOfKind(Combination):
    def get_type(self): return CombinationType.THREE_OF_KIND
    def matches(self, dices): return any(c>=3 for c in count_faces(dices).values())
    def score(self, dices): return max((f*3 for f,c in count_faces(dices).items() if c>=3), default=0)

class FourOfKind(Combination):
    def get_type(self): return CombinationType.FOUR_OF_KIND
    def matches(self, dices): return any(c>=4 for c in count_faces(dices).values())
    def score(self, dices): return max((f*4 for f,c in count_faces(dices).items() if c>=4), default=0)

class FiveOfKind(Combination):
    def get_type(self): return CombinationType.FIVE_OF_KIND
    def matches(self, dices): return any(c>=5 for c in count_faces(dices).values())
    def score(self, dices): return max((f*5 for f,c in count_faces(dices).items() if c>=5), default=0)

class FullHouse(Combination):
    def get_type(self): return CombinationType.FULL_HOUSE
    def matches(self, dices):
        counts = sorted(count_faces(dices).values())
        return counts == [2,3]
    def score(self, dices): return sum(dices) if self.matches(dices) else 0

class SmallStraight(Combination):
    def get_type(self): return CombinationType.SMALL_STRAIGHT
    def matches(self, dices): return is_small_straight(dices)
    def score(self, dices): return small_straight_score(dices) if self.matches(dices) else 0

class LargeStraight(Combination):
    def get_type(self): return CombinationType.LARGE_STRAIGHT
    def matches(self, dices): return is_large_straight(dices)
    def score(self, dices): return sum(dices) if self.matches(dices) else 0

class Chance(Combination):
    def get_type(self): return CombinationType.CHANCE
    def matches(self, dices): return True
    def score(self, dices): return sum(dices)