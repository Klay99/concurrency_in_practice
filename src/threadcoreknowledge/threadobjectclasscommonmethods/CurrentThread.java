package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 演示打印main，Thread-0，Thread-1
 */
public class CurrentThread implements Runnable {
    @Override
    public void run() {
        // 即使是调用同一个方法，结果也不一样，说明Thread.currentThread()是当前线程的一个引用
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        new CurrentThread().run();
        new Thread(new CurrentThread()).start();
        new Thread(new CurrentThread()).start();
    }

}
