package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 用程序实现两个线程交替打印0-100的奇偶数，1：synchronized实现，效率很差
 * 偶线程打印0
 * 奇线程打印1
 * 偶线程打印2
 * . . . . . .
 */
public class TwoThreadsPrintOddAndEvenSyn {
    private static int num = 0;
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (num < 100) {
                // 在满足条件并执行完后，下次一定不满足条件，直到在竞争中失败，另一个线程执行了+1操作才能再次执行
                // 两个线程的配合度很差，会浪费大量时间
                synchronized (lock) {
                    if ((num & 1) == 0) {
                        System.out.println("even thread print:" + num++);
                    }
                }
            }
        }).start();
        new Thread(() -> {
            while (num < 100) {
                synchronized (lock) {
                    if ((num & 1) == 1) {
                        System.out.println("odd  thread print:" + num++);
                    }
                }
            }
        }).start();
    }
}