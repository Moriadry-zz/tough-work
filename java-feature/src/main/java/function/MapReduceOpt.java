package function;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: Moriatry
 * Date:   15-12-11
 */
public class MapReduceOpt {

    public static void main(String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
        System.out.println("Total number of characters in all names: " + friends.stream().mapToInt(name -> name.length()).sum());

        final Optional<String> aLongName = friends.stream().reduce((name1, name2)->name1.length() >= name2.length() ? name1 : name2);
        aLongName.ifPresent(name -> System.out.println(String.format("Longest name: %s", name)));

        final String aDefaultLongName = friends.stream().reduce("A", (name1, name2)->name1.length() >= name2.length() ? name1 : name2);
        System.out.println("A default Long name is " + aDefaultLongName);

        /**
         * Join names
         * */
        System.out.println(String.join(", ", friends));
        System.out.println(friends.stream().map(name->name.toUpperCase()).collect(Collectors.joining(" | ")));
    }
}
