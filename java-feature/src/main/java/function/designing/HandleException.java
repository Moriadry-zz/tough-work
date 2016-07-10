package function.designing;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Moriatry
 * Date:   15-12-17
 */
public class HandleException {

    public static void main(String[] args) {
        List<String> paths = Arrays.asList("/usr", "/tmp");

        paths.stream()
                .map(path -> {
                    try {
                        return new File(path).getCanonicalPath();
                    } catch (IOException e) {
                        return e.getMessage();
                    }
                })
                .forEach(System.out::println);
    }
}
