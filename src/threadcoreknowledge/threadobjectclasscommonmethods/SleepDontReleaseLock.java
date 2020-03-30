package threadcoreknowledge.threadobjectclasscommonmethods;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 展示sleep不释放lock（lock本身是需要手动释放的）
 */
public class SleepDontReleaseLock implements Runnable {
    private static final Lock lock = new ReentrantLock();
    @Override
    public void run() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "获取到了锁.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally { // unlock要放到finally，无论线程是否执行完，都必须释放锁
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放了锁.");
        }
    }

    public static void main(String[] args) {
        SleepDontReleaseLock lock = new SleepDontReleaseLock();
        new Thread(lock).start();
        new Thread(lock).start();
    }
}
