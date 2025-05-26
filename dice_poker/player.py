from abc import ABC, abstractmethod
from .score_table import ScoreTable

class Player(ABC):
    def __init__(self, name: str):
        self.name = name
        self.score_table = ScoreTable()

    @abstractmethod
    def play(self):
        pass
