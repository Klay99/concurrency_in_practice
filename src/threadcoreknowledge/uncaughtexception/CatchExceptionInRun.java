package threadcoreknowledge.uncaughtexception;

/**
 * 解决方案一（不推荐）：手动在每个run方法里进行try/catch
 */
public class CatchExceptionInRun implements Runnable {
    @Override
    public void run() {
        // 能够捕获并处理异常，但是也带来了弊端：
        // 需要在每个run方法中做处理，且无法预料出现的异常到底是什么类型
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            System.out.println("Caught Exception");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            new Thread(new CatchExceptionInRun(), "MyThread-1").start();
            Thread.sleep(500);
            new Thread(new CatchExceptionInRun(), "MyThread-2").start();
            Thread.sleep(500);
            new Thread(new CatchExceptionInRun(), "MyThread-3").start();
            Thread.sleep(500);
            new Thread(new CatchExceptionInRun(), "MyThread-4").start();
        } catch (RuntimeException e) {
            System.out.println("Caught Exception");
        }
    }
}
