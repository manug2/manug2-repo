package unit

import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class UniqueCombinationsTests extends Specification {

    def "solver can create combinations recursively for 0 items" () {
        List<Integer[]> nestedList = generateSequence([].toArray(new Integer[0]));
        expect :
        [[]] == nestedList
    }

    def "solver can create combinations recursively for 1 items" () {
        List<Integer[]> nestedList = generateSequence([0].toArray(new Integer[0]));
        expect :
        [[0]] == nestedList
    }

    def "solver can create combinations recursively for 2 items" () {
        List<Integer[]> nestedList = generateSequence([0, 1].toArray(new Integer[0]));
        expect :
        [[0, 1], [1, 0]] == nestedList
    }

    def "solver can create combinations recursively for 3 items" () {
        List<Integer[]> nestedList = generateSequence([0, 1, 2].toArray(new Integer[0]));
        expect :
                [[0, 1, 2], [0, 2, 1],
                [1, 0, 2], [2, 0, 1],
                [1, 2, 0], [2, 1, 0]] == nestedList
    }

    def "solver can create combinations recursively for 4 items" () {
        List<Integer[]> nestedList = generateSequence([0, 1, 2, 3].toArray(new Integer[0]));
        expect :
        [[0, 1, 2, 3], [0, 1, 3, 2], [0, 2, 1, 3], [0, 3, 1, 2], [0, 2, 3, 1], [0, 3, 2, 1],
                [1, 0, 2, 3], [1, 0, 3, 2], [2, 0, 1, 3], [3, 0, 1, 2], [2, 0, 3, 1], [3, 0, 2, 1],
                [1, 2, 0, 3], [1, 3, 0, 2], [2, 1, 0, 3], [3, 1, 0, 2], [2, 3, 0, 1], [3, 2, 0, 1],
                [1, 2, 3, 0], [1, 3, 2, 0], [2, 1, 3, 0], [3, 1, 2, 0], [2, 3, 1, 0], [3, 2, 1, 0]] == nestedList
    }


    public static List<Integer[]> generateSequence(Integer[] items) {
        Integer[] placesInt = new Integer[items.length];
        List<Integer[]> results = new ArrayList<Integer[]>(items.length*items.length);
        sequenceInt(0, placesInt.clone(), items, results);
        return results;
    }
    private static void sequenceInt(int level, Integer[] holder, final Integer[] items, List<Integer[]> results) {
        if (level >= items.length) {
            results.add(holder);
            return;
        }

        Integer val = items[level];
        Integer[] inrHolder;
        for (int c = 0; c < holder.length; c++) {
            inrHolder = holder.clone();
            if (inrHolder[c] == null) {
                inrHolder[c] = val;
                sequenceInt(level + 1, inrHolder.clone(), items, results);
            }
        }
        return;
    }

}
