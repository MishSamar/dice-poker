package dicepoker.combination;

import dicepoker.model.CombinationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for dice poker combinations")
public class CombinationTest {

    @Test
    @DisplayName("One Pair detection and scoring")
    void testOnePair() {
        Combination combo = new Pair();
        List<Integer> dice = List.of(2, 2, 4, 5, 6);
        assertTrue(combo.matches(dice));
        assertEquals(4, combo.score(dice));

        dice = List.of(1, 2, 3, 4, 5);
        assertFalse(combo.matches(dice));
        assertEquals(0, combo.score(dice));
    }

    @Test
    @DisplayName("Two Pairs detection and scoring")
    void testTwoPairs() {
        Combination combo = new TwoPairs();
        List<Integer> dice = List.of(3, 3, 5, 5, 2);
        assertTrue(combo.matches(dice));
        assertEquals(16, combo.score(dice)); // (5+5)+(3+3)

        dice = List.of(3, 3, 3, 5, 5);
        assertTrue(combo.matches(dice));
        assertEquals(16, combo.score(dice));

        dice = List.of(1, 2, 3, 4, 6);
        assertFalse(combo.matches(dice));
        assertEquals(0, combo.score(dice));
    }

    @Test
    @DisplayName("Three of Kind detection and scoring")
    void testThreeOfKind() {
        Combination combo = new ThreeOfKind();
        List<Integer> dice = List.of(4, 4, 4, 2, 5);
        assertTrue(combo.matches(dice));
        assertEquals(12, combo.score(dice));

        dice = List.of(4, 4, 2, 2, 5);
        assertFalse(combo.matches(dice));
        assertEquals(0, combo.score(dice));
    }

    @Test
    @DisplayName("Four of Kind detection and scoring")
    void testFourOfKind() {
        Combination combo = new FourOfKind();
        List<Integer> dice = List.of(6, 6, 6, 6, 1);
        assertTrue(combo.matches(dice));
        assertEquals(24, combo.score(dice));

        dice = List.of(6, 6, 6, 1, 1);
        assertFalse(combo.matches(dice));
        assertEquals(0, combo.score(dice));
    }

    @Test
    @DisplayName("Five of Kind (Poker) detection and scoring")
    void testFiveOfKind() {
        Combination combo = new FiveOfKind();
        List<Integer> dice = List.of(5, 5, 5, 5, 5);
        assertTrue(combo.matches(dice));
        assertEquals(25, combo.score(dice));

        dice = List.of(5, 5, 5, 5, 4);
        assertFalse(combo.matches(dice));
        assertEquals(0, combo.score(dice));
    }

    @Test
    @DisplayName("Full House detection and scoring")
    void testFullHouse() {
        Combination combo = new FullHouse();
        List<Integer> dice = List.of(2, 2, 3, 3, 3);
        assertTrue(combo.matches(dice));
        assertEquals(13, combo.score(dice));

//        dice = List.of(2, 2, 2, 2, 3);
//        assertFalse(combo.matches(dice));
//        assertEquals(0, combo.score(dice));
    }

    @Test
    @DisplayName("Straights detection and scoring")
    void testStraights() {
        Combination small = new Straight(CombinationType.SMALL_STRAIGHT);
        Combination large = new Straight(CombinationType.LARGE_STRAIGHT);

        List<Integer> diceSmall = List.of(1, 2, 3, 4, 6);
        assertTrue(small.matches(diceSmall));
        assertEquals(10, small.score(diceSmall)); // sum
        assertFalse(large.matches(diceSmall));

        List<Integer> diceLarge = List.of(2, 3, 4, 5, 6);
        assertTrue(small.matches(diceLarge));
        assertTrue(large.matches(diceLarge));
        assertEquals(20, large.score(diceLarge));
    }

    @Test
    @DisplayName("Chance always matches and sums correctly")
    void testChance() {
        Combination combo = new Chance();
        List<Integer> dice = List.of(1, 3, 4, 6, 2);
        assertTrue(combo.matches(dice));
        assertEquals(16, combo.score(dice));
    }

    @Test
    @DisplayName("NumberCombination (Ones to Sixes) scoring")
    void testNumberCombination() {
        Combination ones = new NumberCombination(CombinationType.ONES, 1);
        Combination sixes = new NumberCombination(CombinationType.SIXES, 6);
        List<Integer> dice = List.of(1, 1, 2, 6, 6);

        assertTrue(ones.matches(dice));
        assertEquals(2, ones.score(dice));

        assertTrue(sixes.matches(dice));
        assertEquals(12, sixes.score(dice));
    }

    @Test
    @DisplayName("Factory creates all combinations")
    void testFactory() {
        var combos = CombinationFactory.createAll();
        // Expecting 15 types
        assertEquals(15, combos.size(), "Should create 15 combinations");
        // Check presence of each type
        for (var type : CombinationType.values()) {
            assertTrue(combos.stream().anyMatch(c -> c.getType() == type),
                    "Missing combination: " + type);
        }
    }
}
