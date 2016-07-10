package function;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Author: Moriatry
 * Date:   15-12-11
 */
public class CompratorOpt {

    public static void printPeople(final String msg, final List<Person> people) {
        System.out.println(msg);
        people.stream().forEach(System.out::println);
    }

    public static void main(String[] args) {
        final List<Person> people = Arrays.asList(
                new Person("John", 20),
                new Person("Sara", 21),
                new Person("Jane", 21),
                new Person("Greg", 35));

        List<Person> asendingAge = people.stream().sorted((person1, person2) -> person1.ageDifference(person2))
                                                  .collect(Collectors.toList());
        printPeople("Sorted in ascending order by age: ", asendingAge);

        List<Person> asendingAge_1 = people.stream().sorted(Person::ageDifference)
                                                    .collect(Collectors.toList());
        printPeople("Sorted in ascending order by age: ", asendingAge_1);

        Comparator<Person> compareAscending = (person1, person2)->person1.ageDifference(person2);
        Comparator<Person> compareDescending = compareAscending.reversed();
        printPeople("Sorted in ascending order by age: ", people.stream().sorted(compareAscending).collect(Collectors.toList()));
        printPeople("Sorted in descending order by age: ", people.stream().sorted(compareDescending).collect(Collectors.toList()));

        printPeople("Sorted in ascending order by name: ", people.stream()
                .sorted((person1, person2) -> person1.getName().compareTo(person2.getName()))
                .collect(Collectors.toList()));

        people.stream().min(Person::ageDifference).ifPresent(youngest -> System.out.println("Youngest: " + youngest));
        people.stream().max(Person::ageDifference).ifPresent(eldest->System.out.println("Eldest: " + eldest));

        final Function<Person, Integer> byAge = person->person.getAge();
        final Function<Person, String> byTheirName = person->person.getName();
        printPeople("Sorted in ascending order by age and name: ",
                    people.stream().sorted(Comparator.comparing(byAge).thenComparing(byTheirName))
                            .collect(Collectors.toList()));
    }
}
