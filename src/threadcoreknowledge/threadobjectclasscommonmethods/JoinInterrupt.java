package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 演示join期间被中断的效果
 */
public class JoinInterrupt {

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread thread1 = new Thread(() -> {
            try {
                mainThread.interrupt();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("子线程中断");
            }
            System.out.println("thread1执行完毕了");
        });
        thread1.start();
        System.out.println("等待子线程执行完毕");
        // 由于是主线程等待子线程加入，所以实际上，被中断的是主线程，抛出异常的也是主线程
        try {
            thread1.join();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被中断了");
            // 在子线程加入过程中被中断了，主线程被中断，子线程也应该要被中断
            // 由于中断是在main中处理的，所以main应该把中断信息恢复并传给子线程
            thread1.interrupt(); // 恢复中断
        }
        System.out.println("子线程执行完毕");
    }

}
