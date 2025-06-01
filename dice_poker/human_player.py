from .player import Player
from .dice_utils import roll
from .combination_factory import create_all
from .combination_type import CombinationType
from .game_config import GameConfig


class HumanPlayer(Player):
    """
    HumanPlayer — игрок, управляющийся вводом в терминале.

    Игровой процесс:
    1) Бросок пяти кубиков.
    2) До двух перебросов: вводите индексы кубиков для переброса.
    3) Выбор комбинации из 15 возможных (школа + нижний раздел), с учётом штрафа за повторный выбор.
    4) Если вы не перебрасывали после первого броска и правило FIRST_THROW_DOUBLE включено,
       нижние комбинации (не «школа») дают удвоенные очки.
    """

    def __init__(self, name: str):
        super().__init__(name)
        # список всех 15 комбинаций в порядке из combination_factory
        self.combinations = create_all()

    def play(self):
        print(f"\n=== Ход игрока: {self.name} ===")

        # --- 1. Первый бросок ---
        dice = roll(5)
        if GameConfig.SORTING_VALUES:
            dice.sort()
        print(f"Бросок 1: {dice}")

        first_no_reroll = False

        # --- 2. До двух перебросов ---
        for throw_num in range(1, 3):
            # спрашиваем, какие позиции перебросить
            # если просто Enter — больше не перебрасываем

            # парсим и валидируем ввод
            while True:
                raw = input(
                    f"Введите номера кубиков для переброса слитно или Enter, чтобы оставить все: ").strip()
                parts = raw
                try:
                    idxs = sorted({int(p) - 1 for p in parts})
                    if not any(i < 0 or i > 4 for i in idxs):
                        break
                except ValueError:
                    pass

            if raw == "":
                if throw_num == 1:
                    first_no_reroll = True
                break

            # собираем новые и старые кубики
            kept = [dice[i] for i in range(5) if i not in idxs]
            new = roll(len(idxs))
            dice = kept + new
            if GameConfig.SORTING_VALUES:
                dice.sort()

            print(f"Бросок {throw_num + 1}: {dice}")

        print(f"Финальные кубики: {dice}")

        # --- 3. Показ всех 15 комбинаций и выбор ---
        for i, comb in enumerate(self.combinations, start=1):
            used_mark = f"(yet {self.score_table.get(comb.get_type())})" if self.score_table.used(
                comb.get_type()) else ""
            double_mark = "*2" if self.double_score(first_no_reroll, comb) else ""
            print(f"{i:2}. {comb.get_type().name:15} → {comb.score(dice):2}{double_mark} {used_mark}")

        while True:
            choice = input(f"Выберите комбинацию (1–{len(self.combinations)}): ").strip()
            if not choice.isdigit():
                print("Ввод должен быть числом.")
                continue
            idx = int(choice) - 1
            if idx < 0 or idx >= len(self.combinations):
                print("Число вне диапазона.")
                continue
            comb = self.combinations[idx]
            if self.score_table.used(comb.get_type()):
                print("Эта комбинация уже использована. Выберите другую.")
                continue
            break

        # --- 4. Запись очков и бонус за первый бросок ---
        score = comb.score(dice)
        # двойной счёт, если не было переброса после первого броска и правило включено
        if self.double_score(first_no_reroll, comb):
            score *= 2
            print("Бонус: удвоенный счёт за первый бросок!")

        self.score_table.record(comb.get_type(), score)
        print(f"Записано {score} очков в {comb.get_type().name}\n")

    def double_score(self, first_no_reroll, comb) -> bool:
        return first_no_reroll \
            and GameConfig.FIRST_THROW_DOUBLE \
            and comb.get_type() not in {
                CombinationType.ONES, CombinationType.TWOS, CombinationType.THREES,
                CombinationType.FOURS, CombinationType.FIVES, CombinationType.SIXES
            }
