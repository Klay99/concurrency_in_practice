package threadcoreknowledge.uncaughtexception;

/**
 * 子线程异常无法用传统方法捕获（try/catch）
 * step 1: 不加try / catch抛出4个异常，都带线程名字
 * step 2: 加了try / catch，期望捕获到第一个线程的异常，线程234不应该运行，希望看到Caught Exception
 * step 3: 执行时发现，根本没有Caught Exception，线程234依然运行并且抛出了异常
 */
public class CantCatchDirectly implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            // step 1
            new Thread(new CantCatchDirectly(), "MyThread-1").start();
            Thread.sleep(500);
            new Thread(new CantCatchDirectly(), "MyThread-2").start();
            Thread.sleep(500);
            new Thread(new CantCatchDirectly(), "MyThread-3").start();
            Thread.sleep(500);
            new Thread(new CantCatchDirectly(), "MyThread-4").start();
        } catch (RuntimeException e) { // step 2
            System.out.println("Caught Exception");
        }
        // 实际运行结果如step 3所述
        /*
          原因：try/catch只能捕获对应线程内的异常，及本例中的try/catch只能捕获main中的异常，
          而实际上发生的异常是在子线程当中的（子线程run时抛出的异常），这时try/catch是无法对其进行捕获的
         */
    }
}
