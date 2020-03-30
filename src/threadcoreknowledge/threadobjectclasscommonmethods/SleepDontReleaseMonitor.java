package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 展示线程sleep时不释放synchronized的monitor，等sleep时间到了以后，正常结束后才会释放锁
 */
public class SleepDontReleaseMonitor implements Runnable {
    @Override
    public void run() {
        syn();
    }

    private synchronized void syn() {
        System.out.println("线程" + Thread.currentThread().getName() + "拿到了monitor");
        try {
            Thread.sleep(5000); // 只有当线程执行完才会释放锁，sleep过程中其它线程并不能拿到锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程" + Thread.currentThread().getName() + "退出了同步代码块。");
    }

    public static void main(String[] args) {
        SleepDontReleaseMonitor monitor = new SleepDontReleaseMonitor();
        new Thread(monitor).start();
        new Thread(monitor).start();
    }
}
