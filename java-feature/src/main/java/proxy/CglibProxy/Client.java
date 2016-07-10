package proxy.CglibProxy;

/**
 * Author: Moriatry
 * Date:   16-1-4
 */
public class Client {

    public static void main(String[] args) {
        BookFacadeCglib cglib = new BookFacadeCglib();
        BookFacadeImpl bookFacade = (BookFacadeImpl)cglib.getInstance(new BookFacadeImpl());
        bookFacade.addBook();
    }
}
