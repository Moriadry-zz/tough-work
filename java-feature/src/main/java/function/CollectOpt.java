package function;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Author: Moriatry
 * Date:   15-12-11
 */
public class CollectOpt {

    public static void main(String[] args) {
        final List<Person> people = Arrays.asList(
                new Person("John", 20),
                new Person("Sara", 21),
                new Person("Jane", 21),
                new Person("Greg", 35));

        List<Person> olderThan20 = people.stream().filter(person -> person.getAge() > 20)
                                         .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("People older than 20: " + olderThan20);

        List<Person> olderThan20_1 = people.stream().filter(person -> person.getAge() > 20)
                                                    .collect(Collectors.toList());
        System.out.println("People older than 20: " + olderThan20_1);

        Map<Integer, List<Person>> peopleByAge = people.stream().collect(Collectors.groupingBy(Person::getAge));
        System.out.println("Grouped by age: " + peopleByAge);

        Map<Integer, List<String>> nameOfPeopleByAge = people.stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.toList())));
        System.out.println("People name grouped by age: " + nameOfPeopleByAge);


        Comparator<Person> byAge = Comparator.comparing(Person::getAge);
        Map<Character, Optional<Person>> oldestPersonOfEachCharacter = people.stream()
                .collect(Collectors.groupingBy(person -> person.getName().charAt(0), Collectors.reducing(BinaryOperator.maxBy(byAge))));
        System.out.println("Oldest person of each letter: " + oldestPersonOfEachCharacter);
    }
}
