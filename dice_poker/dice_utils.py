import random
from collections import Counter
from typing import List, Dict


def roll(count: int) -> List[int]:
    """Roll `count` six-sided dices and return the results."""
    return [random.randint(1, 6) for _ in range(count)]


def count_faces(dices: List[int]) -> Dict[int, int]:
    """Count occurrences of each face value in a roll."""
    return dict(Counter(dices))


def has_count_at_least(dices: List[int], min_reps: int) -> bool:
    return any(cnt >= min_reps for cnt in count_faces(dices).values())


def kinds(dices: List[int], min_reps: int) -> List[int]:
    counts = count_faces(dices)
    return [face for face, cnt in counts.items() if cnt >= min_reps]


def is_small_straight(dices: List[int]) -> bool:
    unique = set(dices)
    return any(all(x in unique for x in seq) for seq in ([1, 2, 3, 4], [2, 3, 4, 5], [3, 4, 5, 6]))




def small_straight_score(dices: List[int]) -> int:
    unique = set(dices)
    return max(sum((x if x in unique else -100) for x in seq) for seq in ([1, 2, 3, 4], [2, 3, 4, 5], [3, 4, 5, 6])) if is_small_straight(dices) else 0

def is_large_straight(dices: List[int]) -> bool:
    unique = set(dices)
    return unique == {1, 2, 3, 4, 5} or unique == {2, 3, 4, 5, 6}
