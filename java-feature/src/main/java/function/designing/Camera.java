package function.designing;

import java.util.stream.Stream;
import java.util.function.Function;
import java.awt.Color;
import java.util.function.Consumer;

/**
 * Author: Moriatry
 * Date:   15-12-17
 */

@SuppressWarnings("unchecked")
public class Camera {

    private Function<Color, Color> filter;

    public Camera() {
        setFilters();
    }

    public void setFilters(final Function<Color, Color> ... filters) {
        filter = Stream.of(filters)
                .reduce((filter, next)->filter.compose(next))
                .orElse(color->color);
    }

    public Color capture(final Color inputColor) {
        final Color processedColor = filter.apply(inputColor);
        return processedColor;
    }

    public static void main(final String[] args) {

        final Camera camera = new Camera();
        final Consumer<String> printCaptured = (filterInfo)->
                System.out.println(String.format("with %s: %s", filterInfo, camera.capture(new Color(200, 100, 200))));

        System.out.println("//" + "START:NOFILTER_OUTPUT");
        printCaptured.accept("no filter");
        System.out.println("//" + "END:NOFILTER_OUTPUT");

        System.out.println("====================================");

        System.out.println("//" + "START:BRIGHT_OUTPUT");
        camera.setFilters(Color::brighter);
        printCaptured.accept("brighter filter");
        System.out.println("//" + "END:BRIGHT_OUTPUT");

        System.out.println("====================================");

        System.out.println("//" + "START:DARK_OUTPUT");
        camera.setFilters(Color::darker);
        printCaptured.accept("darker filter");
        System.out.println("//" + "END:DARK_OUTPUT");

        System.out.println("====================================");

        System.out.println("//" + "START:BOTH_OUTPUT");
        camera.setFilters(Color::brighter, Color::darker);
        printCaptured.accept("brighter & darker filter");
        System.out.println("//" + "END:BOTH_OUTPUT");
    }
}
