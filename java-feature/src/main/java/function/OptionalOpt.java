package function;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Author: Moriatry
 * Date:   15-12-11
 */
public class OptionalOpt {

    public static void pickName(final List<String> names, final String startLetter) {
        final Optional<String> foundName = names.stream().filter(name->name.startsWith(startLetter)).findFirst();
        System.out.println(String.format("A name start with %s: %s", startLetter, foundName.orElse("No name found")));
    }

    public static void main(String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
        pickName(friends, "N");
        pickName(friends, "Z");
    }
}
