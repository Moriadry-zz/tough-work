package function.designing;

import java.util.function.Consumer;

/**
 * Author: Moriatry
 * Date:   15-12-17
 */
public class FluentMailer {

    private FluentMailer() {}

    public FluentMailer from(final String address) {return this;}
    public FluentMailer to(final String adderss) {return this;}
    public FluentMailer subject(final String line) {return this;}
    public FluentMailer body(final String message) {return this;}

    public static void send(final Consumer<FluentMailer> block) {
        final FluentMailer mailer = new FluentMailer();
        block.accept(mailer);
        System.out.println("sending ...");
    }

    public static void main(String[] args) {
        FluentMailer.send(mailer->mailer.from("123@123.com").to("456@123.com").subject("subject").body("body"));
    }
}
