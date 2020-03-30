package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * join期间获取线程状态：1.先join在mainThread.getState()；2.debugger查看线程join前后状态的对比
 */
public class JoinThreadState {

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("mainThread state:" + mainThread.getState());
                System.out.println(Thread.currentThread().getName() + "运行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        System.out.println("等待子线程运行完毕");
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("子线程运行完毕");
    }

}
