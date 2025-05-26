import random
from collections import Counter
from typing import List, Dict


def roll(count: int) -> List[int]:
    """Roll `count` six-sided dice and return the results."""
    return [random.randint(1, 6) for _ in range(count)]


def count_faces(dice: List[int]) -> Dict[int, int]:
    """Count occurrences of each face value in a roll."""
    return dict(Counter(dice))


def has_count_at_least(dice: List[int], min_reps: int) -> bool:
    return any(cnt >= min_reps for cnt in count_faces(dice).values())


def kinds(dice: List[int], min_reps: int) -> List[int]:
    counts = count_faces(dice)
    return [face for face, cnt in counts.items() if cnt >= min_reps]


def is_small_straight(dice: List[int]) -> bool:
    unique = set(dice)
    return any(all(x in unique for x in seq) for seq in ([1, 2, 3, 4], [2, 3, 4, 5], [3, 4, 5, 6]))


def is_large_straight(dice: List[int]) -> bool:
    unique = set(dice)
    return unique == {1, 2, 3, 4, 5} or unique == {2, 3, 4, 5, 6}
