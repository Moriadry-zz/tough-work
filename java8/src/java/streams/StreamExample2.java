package streams;

import java.util.Arrays;
import java.util.List;

/**
 * Created by moriatry on 12/10/2015.
 */
public class StreamExample2 {

    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> list2 = Arrays.asList(2, 4, 6, 8);
        List<Integer> list3 = Arrays.asList(1, 3, 5);

        List<List<Integer>> lists = Arrays.asList(list1, list2, list3);

        System.out.println(lists);

        lists.stream()
                .map(l -> l.size())
                .forEach(System.out::println);
    }

}
