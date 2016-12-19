package streams;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by moriatry on 06/10/2015.
 */
public class StreamExample {

    public static void main(String[] args) {


        /**
         * Stream is a typed interface / an object define operations and doesn't hold data
         *
         * DIFFERENCE with collection api
         * - Doesn't hold data
         * - Doesn't change data it processes
         *
         * Can process data in parallel efficiently and in one pass by performing multiple operations on it ...map/filter/reduce
         * Stream is new and not a Collection api because the Java didn't want to change collection api
         *
         */
        Stream<Integer> numbers = Stream.of(8, 7, 6, 5, 4, 3, 2, 1);

        Stream<Integer> numbers1 = Stream.of(8, 7, 6, 5, 4, 3, 2, 1);

        System.out.println(numbers);

        Predicate<Integer> predicate1 = (s -> s.equals(8));

        Predicate<Integer> predicate2 = Predicate.isEqual(2);

        Predicate<Integer> predicate3 = Predicate.isEqual(4);

        numbers.filter(predicate2.or(predicate3))
                .forEach(s -> System.out.println(s));


        numbers1.filter(predicate2)
                .forEach(s -> System.out.println(s));
    }
}
