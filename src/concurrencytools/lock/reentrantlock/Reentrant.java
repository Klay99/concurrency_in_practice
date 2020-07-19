package concurrencytools.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示ReentrantLock的可重入性
 */
public class Reentrant {
    private static ReentrantLock lock = new ReentrantLock();

    private static void method1() {
        System.out.println(lock.getHoldCount()); // 查看lock被持有的次数
        lock.lock();
        System.out.println(lock.getHoldCount());
        try {
            System.out.println(Thread.currentThread().getName() + " in method1");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " trying to go to method2");
            method2(); // method2需要同一把lock，如果是不可重入的话，就会发生死锁
            System.out.println(lock.getHoldCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
            System.out.println(lock.getHoldCount());
        }
    }

    private static void method2() {
        lock.lock();
        System.out.println(lock.getHoldCount());
        try {
            Thread.sleep(200);
            System.out.println(Thread.currentThread().getName() + " in method2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(Reentrant::method1).start();
    }
}
