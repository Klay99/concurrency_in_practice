package threadcoreknowledge.multithreadserror;

/**
 * 多线程带来的问题：第二种，演示死锁
 */
public class MultiThreadsError2 implements Runnable {
    private int flag = 1;
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    @Override
    public void run() {
        System.out.println("flag: " + flag);
        if (flag == 1) {
            synchronized (lock1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("1"); // 永远不会执行
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
                    System.out.println("0"); // 永远不会执行
                }
            }
        }
    }

    public static void main(String[] args) {
        MultiThreadsError2 instance1 = new MultiThreadsError2();
        MultiThreadsError2 instance2 = new MultiThreadsError2();
        instance1.flag = 1;
        instance2.flag = 0;
        new Thread(instance1).start();
        new Thread(instance2).start();
    }
}
