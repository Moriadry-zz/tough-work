package proxy.JdkProxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Author: Moriatry
 * Date:   16-1-4
 */
public class Main {

    public static void main(String ... args) throws Throwable {
        RealSubject rs = new RealSubject();
        InvocationHandler ds = new DynamicSubject(rs);

        Class rs_class = rs.getClass();
        Subject subject = (Subject) Proxy.newProxyInstance(rs_class.getClassLoader(), rs_class.getInterfaces(), ds);
        subject.request();
    }
}
