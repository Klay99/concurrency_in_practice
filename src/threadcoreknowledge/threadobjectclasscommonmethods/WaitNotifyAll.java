package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 3个线程，线程1和线程2被阻塞，线程3来唤醒它们。
 * 线程3分别用notify()和notifyAll()来唤醒，看有什么区别。
 * 同时观察，start()先执行，不代表线程先启动
 */
public class WaitNotifyAll implements Runnable {
    private static final Object resourceA = new Object();

    @Override
    public void run() {
        synchronized (resourceA) {
            System.out.println(Thread.currentThread().getName() + " got resourceA lock.");
            try {
                System.out.println(Thread.currentThread().getName() + " waits to start and release resourceA lock.");
                resourceA.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is waiting to end.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable r = new WaitNotifyAll();
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);
        Thread thread3 = new Thread(() -> {
            synchronized (resourceA) {
                if (Math.random() > 0.5) {
                    resourceA.notifyAll(); // 唤醒所有
                    System.out.println(Thread.currentThread().getName() + " notified all.");
                } else {
                    resourceA.notify(); // 随机唤醒其中一个
                    System.out.println(Thread.currentThread().getName() + " notified one.");
                }
            }
        });
        thread1.start();
        thread2.start();
        //Thread.sleep(200); // 先让thread1和thread2进入wait，thread3再notifyAll
        thread3.start();
    }
}
