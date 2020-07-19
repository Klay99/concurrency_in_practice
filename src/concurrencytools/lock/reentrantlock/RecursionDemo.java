package concurrencytools.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示ReentrantLock的递归调用，可重入性
 * 适用于需要反复处理同一操作，用可重入锁就避免了死锁以及一次次的加解锁
 */
public class RecursionDemo {
    private static ReentrantLock lock = new ReentrantLock();

    private static void accessResource() {
        lock.lock();
        try {
            System.out.println("处理资源");
            if (lock.getHoldCount() < 5) {
                accessResource();
            } else {
                System.out.println("资源都已处理完毕");
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(RecursionDemo::accessResource).start();
    }
}
