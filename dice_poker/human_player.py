from .player import Player
from .dice_utils import roll
from .combination_factory import create_all
from .combination_type import CombinationType

class HumanPlayer(Player):
    def __init__(self, name: str):
        super().__init__(name)
        self.combinations = create_all()

    def play(self):
        print(f"{self.name}'s turn")
        dice = roll(5)
        for i in range(2):
            print(f"Roll {i+1}: {dice}")
            keep = input("Enter positions (1-5) to keep, separated by spaces (or press Enter to reroll all): ").strip()
            if keep:
                idx = [int(x)-1 for x in keep.split() if x.isdigit() and 1 <= int(x) <= 5]
                new_count = 5 - len(idx)
                new = roll(new_count)
                # rebuild dice
                kept = [dice[i] for i in idx]
                dice = kept + new
            else:
                dice = roll(5)
        print(f"Final dice: {dice}")
        # Show available combos
        available = [c for c in self.combinations if not self.score_table.used(c.get_type())]
        for idx, comb in enumerate(available, 1):
            name = comb.get_type().name
            possible = comb.score(dice)
            print(f"{idx}. {name} --> score {possible}")
        choice = int(input(f"Choose combination (1-{len(available)}): ")) - 1
        comb = available[choice]
        score = comb.score(dice)
        self.score_table.record(comb.get_type(), score)
        print(f"Recorded {score} in {comb.get_type().name}\n")
