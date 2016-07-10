package proxy.pattern;

/**
 * Author: Moriatry
 * Date:   16-1-4
 */
public class ProxySubject implements Subject {

    private RealSubject realSubject;

    public ProxySubject(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public void request() {
        System.out.println("prev: from ProxySubject class ... ");
        realSubject.request();
        System.out.println("post: from ProxySubject class ... ");
    }
}
