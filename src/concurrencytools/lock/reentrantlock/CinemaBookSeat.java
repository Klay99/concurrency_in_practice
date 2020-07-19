package concurrencytools.lock.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示多线程预定电影院座位
 */
public class CinemaBookSeat {
    private static Lock lock = new ReentrantLock();

    private static void bookSeat() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始预订座位");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "座位预定完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(CinemaBookSeat::bookSeat, "线程1").start();
        new Thread(CinemaBookSeat::bookSeat, "线程2").start();
        new Thread(CinemaBookSeat::bookSeat, "线程3").start();
        new Thread(CinemaBookSeat::bookSeat, "线程4").start();
    }
}
