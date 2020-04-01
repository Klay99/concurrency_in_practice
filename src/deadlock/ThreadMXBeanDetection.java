package deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 用ThreadMXBean检测/定位死锁
 */
public class ThreadMXBeanDetection implements Runnable {
    private int flag = 1;
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    @Override
    public void run() {
        System.out.println("flag = " + flag);
        if (flag == 1) {
            synchronized (lock1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("线程1成功拿到两把锁");
                }
            }
        }
        if (flag == 0) {
            synchronized (lock2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("线程2成功拿到两把锁");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadMXBeanDetection r1 = new ThreadMXBeanDetection();
        ThreadMXBeanDetection r2 = new ThreadMXBeanDetection();
        r1.flag = 1;
        r2.flag = 0;
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        Thread.sleep(2000); // 等待死锁发生
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadLockedThreads = threadMXBean.findDeadlockedThreads();
        if (deadLockedThreads != null && deadLockedThreads.length > 0) { // 定位到死锁后，可以进行一些后续操作
            System.out.println("发现死锁了！陷入死锁的线程有：");
            for (long deadLockedThread : deadLockedThreads) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadLockedThread);
                System.out.println(threadInfo.getThreadName());
            }
        }
    }
}
