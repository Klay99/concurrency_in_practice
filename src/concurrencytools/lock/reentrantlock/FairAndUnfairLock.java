package concurrencytools.lock.reentrantlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 以ReentrantLock为例，演示公平锁与非公平锁
 */
public class FairAndUnfairLock {
    public static void main(String[] args) throws InterruptedException {
        PrintQueue printQueue = new PrintQueue();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Print(printQueue));
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
            Thread.sleep(100); // 暂停一段时间，保证线程按顺序启动
        }
    }
}
class Print implements Runnable {
    private PrintQueue printQueue;
    public Print(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始打印资料");
        printQueue.printJob();
        System.out.println(Thread.currentThread().getName() + "打印完毕");
    }
}
class PrintQueue {
//    private Lock queueLock = new ReentrantLock(true); // 公平锁
    private Lock queueLock = new ReentrantLock(); // 非公平锁

    public void printJob() {
        queueLock.lock();
        try {
            int duration = new Random().nextInt(5) + 1;
            System.out.println(Thread.currentThread().getName() + "正在打印第一份资料，需要" + duration + "秒");
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            queueLock.unlock();
        }
        // 如果是公平锁，线程打印完第一份资料后，要想打印第二份资料就需要重新排队
        // 如果是非公平锁，线程打印完第一份资料后，由于唤醒下一个线程需要时间，所以可以不用排队而直接打印第二份资料
        queueLock.lock();
        try {
            int duration = new Random().nextInt(5) + 1;
            System.out.println(Thread.currentThread().getName() + "正在打印第二份资料，需要" + duration + "秒");
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            queueLock.unlock();
        }
    }
}
