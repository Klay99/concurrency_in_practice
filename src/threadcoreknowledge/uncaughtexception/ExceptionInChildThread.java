package threadcoreknowledge.uncaughtexception;

/**
 * 单线程：抛出异常，处理异常，有异常堆栈
 * 多线程：子线程发生异常，会有什么不同？
 */
public class ExceptionInChildThread implements  Runnable {
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        new Thread(new ExceptionInChildThread()).start();
        // 即使子线程抛出异常，但是主线程却丝毫不受影响
        // 在实际开发中，出错后很难在日志中发现错误，所以需要全局处理异常
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }
}
