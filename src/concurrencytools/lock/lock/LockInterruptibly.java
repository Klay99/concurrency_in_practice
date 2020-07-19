package concurrencytools.lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示interruptibly方法，相当于设置了无限时间的tryLock，获取锁的期间可以被中断
 */
public class LockInterruptibly implements Runnable {
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "尝试获取锁");
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + "获取到了锁");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "睡眠期间被中断了");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放了锁");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "等锁期间被中断了");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockInterruptibly lockInterruptibly = new LockInterruptibly();
        Thread thread1 = new Thread(lockInterruptibly, "线程1");
        Thread thread2 = new Thread(lockInterruptibly, "线程2");
        thread1.start();
        thread2.start();
        Thread.sleep(2000);

//        thread1.interrupt(); // 如果线程1先启动，则线程1睡眠期间被中断，否则等锁期间被中断
        thread2.interrupt(); // 同上
    }
}
