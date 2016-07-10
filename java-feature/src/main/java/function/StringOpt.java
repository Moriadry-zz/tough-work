package function;

/**
 * Author: Moriatry
 * Date:   15-12-11
 */
public class StringOpt {
    public static void main(String[] args) {
        final String str = "w00t";
        str.chars().forEach(System.out::println);
        System.out.println("-------------------------------");
        str.chars().forEach(ch -> System.out.println((char) ch));
        System.out.println("-------------------------------");
        str.chars().mapToObj(ch-> Character.valueOf((char)ch)).forEach(System.out::println);
    }
}
