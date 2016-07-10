package function.exercise;

/**
 * Author: Moriatry
 * Date:   15-12-18
 */
@FunctionalInterface
public interface FInterface<T> {
    T get();

    default String desc() {
        return "Functional Interface, like Consumer.";
    }
}
