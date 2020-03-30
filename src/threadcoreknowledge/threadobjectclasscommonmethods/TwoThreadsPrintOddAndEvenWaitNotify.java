package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 用程序实现两个线程交替打印0-100的奇偶数，2：wait/notify实现
 * 偶线程打印0
 * 奇线程打印1
 * 偶线程打印2
 * . . . . . .
 */
public class TwoThreadsPrintOddAndEvenWaitNotify {
    private static int num = 0;
    private static Object lock = new Object();

    // 1.拿到锁，就打印
    // 2.打印后，先唤醒，再休眠
    // 两个线程，每个线程执行一次后进入waiting等待另一个线程notify

    public static void main(String[] args) throws InterruptedException {
        TwoThreadsPrintOddAndEvenWaitNotify t = new TwoThreadsPrintOddAndEvenWaitNotify();
        Thread even = new Thread(new TurningRunner(), "even");
        Thread odd = new Thread(new TurningRunner(), "odd ");
        even.start();
        Thread.sleep(10);
        odd.start();
    }

    static class TurningRunner implements Runnable {
        @Override
        public synchronized void run() {
            while (num <= 100) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " print:" + num++);
                    lock.notify();
                    if (num <= 100) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}