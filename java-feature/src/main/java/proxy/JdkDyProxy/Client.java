package proxy.JdkDyProxy;

/**
 * Author: Moriatry
 * Date:   16-1-4
 */
public class Client {

    public static void main(String[] args) {
        BookFacadeProxy proxy = new BookFacadeProxy();
        BookFacade facade = (BookFacade)proxy.bind(new BookFacadeImpl());
        facade.addBook();
    }
}
