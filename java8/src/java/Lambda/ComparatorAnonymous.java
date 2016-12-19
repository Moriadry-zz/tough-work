package lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by moriatry on 12/10/2015.
 */
public class ComparatorAnonymous {

    public static void main(String[] args) {

        //TODO Replace with Lambdas
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        };


        //  lambda expression
        /**Lamnda's are functional interfaces that belong to the java.util package
         *
         *Take parameter name, add lambda syntax and then return statement = lambda expression
         */

        Comparator<String> comparatorLambda =  (o1,o2) ->  Integer.compare(o1.length(), o2.length());

        List<String> list = Arrays.asList("big", "bigger", "largest", "more");
        Collections.sort(list, comparatorLambda);

        list.forEach(System.out::println);
    }

}
