from typing import List
from .combination_type import CombinationType
from .dice_utils import count_faces, is_small_straight, is_large_straight

class Combination:
    def get_type(self) -> CombinationType:
        raise NotImplementedError

    def matches(self, dice: List[int]) -> bool:
        raise NotImplementedError

    def score(self, dice: List[int]) -> int:
        raise NotImplementedError

# Simple count-based combinations for school
class NumberCombination(Combination):
    def __init__(self, face: int, comb_type: CombinationType):
        self.face = face
        self.type = comb_type

    def get_type(self) -> CombinationType:
        return self.type

    def matches(self, dice: List[int]) -> bool:
        # Always matches, can't cross out
        return True

    def score(self, dice: List[int]) -> int:
        return dice.count(self.face) * self.face

class Pair(Combination):
    def get_type(self): return CombinationType.ONE_PAIR
    def matches(self, dice): return any(cnt >= 2 for cnt in count_faces(dice).values())
    def score(self, dice): return max((face*2 for face,cnt in count_faces(dice).items() if cnt>=2), default=0)

class TwoPairs(Combination):
    def get_type(self): return CombinationType.TWO_PAIRS
    def matches(self, dice): return len([f for f,c in count_faces(dice).items() if c>=2]) >= 2
    def score(self, dice):
        faces = sorted([f for f,c in count_faces(dice).items() if c>=2], reverse=True)
        return (faces[0]*2 + faces[1]*2) if len(faces)>=2 else 0

class ThreeOfKind(Combination):
    def get_type(self): return CombinationType.THREE_OF_KIND
    def matches(self, dice): return any(cnt>=3 for cnt in count_faces(dice).values())
    def score(self, dice): return max((face*3 for face,cnt in count_faces(dice).items() if cnt>=3), default=0)

class FourOfKind(Combination):
    def get_type(self): return CombinationType.FOUR_OF_KIND
    def matches(self, dice): return any(cnt>=4 for cnt in count_faces(dice).values())
    def score(self, dice): return max((face*4 for face,cnt in count_faces(dice).items() if cnt>=4), default=0)

class FiveOfKind(Combination):
    def get_type(self): return CombinationType.FIVE_OF_KIND
    def matches(self, dice): return any(cnt>=5 for cnt in count_faces(dice).values())
    def score(self, dice): return max((face*5 for face,cnt in count_faces(dice).items() if cnt>=5), default=0)

class FullHouse(Combination):
    def get_type(self): return CombinationType.FULL_HOUSE
    def matches(self, dice):
        counts = count_faces(dice)
        return sorted(counts.values()) == [2,3]
    def score(self, dice): return sum(dice) if self.matches(dice) else 0

class SmallStraight(Combination):
    def get_type(self): return CombinationType.SMALL_STRAIGHT
    def matches(self, dice): return is_small_straight(dice)
    def score(self, dice): return 30 if self.matches(dice) else 0

class LargeStraight(Combination):
    def get_type(self): return CombinationType.LARGE_STRAIGHT
    def matches(self, dice): return is_large_straight(dice)
    def score(self, dice): return 40 if self.matches(dice) else 0

class Chance(Combination):
    def get_type(self): return CombinationType.CHANCE
    def matches(self, dice): return True
    def score(self, dice): return sum(dice)