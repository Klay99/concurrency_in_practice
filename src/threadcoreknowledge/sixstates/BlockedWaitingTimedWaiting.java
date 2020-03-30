package threadcoreknowledge.sixstates;

/**
 * 展示线程的Blocked、Waiting、TimedWaiting状态
 */
public class BlockedWaitingTimedWaiting implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        BlockedWaitingTimedWaiting runnable = new BlockedWaitingTimedWaiting();
        Thread thread1 = new Thread(runnable);
        thread1.start();
        Thread thread2 = new Thread(runnable);
        thread2.start();
        // 线程1,2启动后先让main睡一会（让线程1,2进入到同步代码后main再打印线程1,2的状态才有效）
        Thread.sleep(100);
        // 线程1先拿到锁进入计时等待状态，线程2拿不到锁进入阻塞状态
        System.out.println(thread1.getState()); // 打印timed waiting
        System.out.println(thread2.getState()); // 打印blocked

        // 待线程1睡完后进入wait时打印线程1的状态
        Thread.sleep(1500);
        System.out.println(thread1.getState()); // 打印waiting
    }

    @Override
    public void run() {
        syn();
    }

    private synchronized void syn() {
        try {
            Thread.sleep(1000); // timed waiting
            wait(); // waiting
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
