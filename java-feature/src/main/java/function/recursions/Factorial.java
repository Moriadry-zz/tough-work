package function.recursions;

import static function.recursions.TailCalls.call;
import static function.recursions.TailCalls.done;

/**
 * Author: Moriatry
 * Date:   15-12-17
 */

public class Factorial {

    public static int factorialRec(final int number) {
        if(number == 1)
            return number;
        else
            return number * factorialRec(number - 1);
    }

    public static int factorialRecTailWithoutLambda(final int result, final int number) {
        if(number == 1)
            return result;
        else
            return factorialRecTailWithoutLambda(result * number, number - 1);
    }

    public static TailCall<Integer> factorialTailRec(final int factorial, final int number) {
        if (number == 1)
            return done(factorial);
        else
            return call(() -> factorialTailRec(factorial * number, number - 1));
    }

    public static int factorial(final int number) {
        return factorialTailRec(1, number).invoke();
    }

    public static void main(final String[] args) {
        System.out.println(factorialRec(5));

        try {
            System.out.println(factorialRec(20000));
        } catch(StackOverflowError ex) {
            System.out.println(ex);
        }

        System.out.println(factorialTailRec(1, 5).invoke());
        System.out.println(factorialTailRec(1, 20000).invoke());

        System.out.println(factorial(5));
        System.out.println(factorial(20000));

        System.out.println("========================================");
        System.out.println(factorialRecTailWithoutLambda(1, 10));
    }
}
