package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 通过join原理，分析出join的代替写法
 */
public class JoinPrinciple {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        });
        thread.start();
        System.out.println("开始等待子线程运行完毕");
//        thread.join();
        // join等价代码
        synchronized (thread) { // 原因分析
            // join底层是wait，而wait本身是需要在synchronized中运行的
            thread.wait();
        }

        System.out.println("所有子线程执行完毕");
    }
}
