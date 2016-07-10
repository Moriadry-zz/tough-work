package function.resources;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: Moriatry
 * Date:   15-12-17
 */
public class Locking {
    Lock lock = new ReentrantLock();

    protected void setLock(final Lock lock) {
        this.lock = lock;
    }

    public void doOp1() {
        lock.lock();
        try {
            System.out.println(" enter doOp1 critical code ");
        } finally {
            lock.unlock();
        }
    }

    public void doOp2() {
        Locker.runLocked(lock, () -> System.out.println(" enter doOp2 cirtical code "));
    }

    public void doOp3() {
        Locker.runLocked(lock, ()->System.out.println(" enter doOp3 cirtical code "));
    }

    public static void main(String ... args) {
        Locking locking = new Locking();
        locking.doOp1();
        locking.doOp2();
        locking.doOp3();
    }
}
