from dice_poker.human_player import HumanPlayer

def main():
    n = int(input("Enter number of players (2-4): "))
    players = [HumanPlayer(input(f"Name {i+1}: ")) for i in range(n)]
    while not all(p.score_table.is_complete() for p in players):
        for p in players:
            p.play()
    print("Game over! Scores:")
    for p in players:
        print(f"{p.name}: {p.score_table.total()} points")

if __name__ == "__main__":
    main()
