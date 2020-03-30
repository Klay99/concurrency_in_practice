package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 证明wait只释放当前的锁
 */
public class WaitNotifyReleaseOwnMonitor {
    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println("Thread1 got resourceA lock.");
                synchronized (resourceB) {
                    System.out.println("Thread1 got resourceB lock.");
                    try {
                        System.out.println("Thread1 releases resourceA lock.");
                        resourceA.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000); // 让线程1先执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (resourceA) {
                System.out.println("Thread2 got resourceA lock.");
                System.out.println("Thread2 tries to get resourceB lock.");
                synchronized (resourceB) {
                    System.out.println("Thread2 got resourceB lock.");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
