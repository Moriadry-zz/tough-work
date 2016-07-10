package function.exercise;

/**
 * Author: Moriatry
 * Date:   15-12-18
 */
public class FInstance {

    public static String apply(FInterface<String> block) {
        return block.get();
    }

    public static void main(String ... args) {
        FInterface<String> instance = ()->"returned value from get method.";

        System.out.println(apply(instance));
    }
}
