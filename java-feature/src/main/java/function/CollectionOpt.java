package function;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Author: Moriatry
 * Date:   15-12-11
 */
public class CollectionOpt {
    public static void main(String[] args) {

        /**
         * iteration
         * */
        final List<String> nameList = Arrays.asList("A", "B", "C", "D", "E");
        System.out.println("iteration: ");
        nameList.stream().forEach(System.out::println);

        /**
         * map
         * */
        final List<String> countList = Arrays.asList("AAA", "BB", "C");
        System.out.println("map: ");
        countList.stream().map(name->name.length()).forEach(System.out::println);

        /**
         * filter
         * */
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
        final List<String> editors = Arrays.asList("Brian", "Jackie", "John", "Mike");
        final List<String> comrades = Arrays.asList("Kate", "Ken", "Nick", "Paula", "Zach");

        final Predicate<String> predicate = (name)->name.startsWith("N");
        final long friendsCount = friends.stream().filter(predicate).count();
        final long editorsCount = editors.stream().filter(predicate).count();
        final long comradesCount = comrades.stream().filter(predicate).count();

        System.out.println("filter: ");
        System.out.println("friednsCount = " + friendsCount + ", editorsCount = " + editorsCount + ", comradesCount = " + comradesCount);

        /**
         * Function
         * */
        final Function<String, Predicate<String>> startsWithLetter = (letter)->name->name.startsWith(letter);
        final long friendsStartWithNCount = friends.stream().filter(startsWithLetter.apply("N")).count();
        final long friendsStartWithSCount = friends.stream().filter(startsWithLetter.apply("S")).count();
        System.out.println("Function<T, R>: ");
        System.out.println("friendsStartWithNCount = " + friendsStartWithNCount + ", friendsStartWithSCount = " + friendsStartWithSCount);
    }
}
