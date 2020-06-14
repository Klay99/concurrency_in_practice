package threadcoreknowledge.jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 不适用于volatile的情况2：boolean flag
 */
public class NoVolatile2 implements Runnable {
    private volatile boolean flag = false; // 重点不在于是否是boolean类型，而是在具体执行中的操作
    private AtomicInteger realA = new AtomicInteger();

    // 虽然用的是boolean变量，但是该变量还是会依赖之前的值，不是原子操作的，相似于i++
    private void flipFlag() {
        // 执行偶数次后结果应该还是false，但是真实的情况是true，false都有
        flag = !flag;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            flipFlag();
            realA.incrementAndGet();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NoVolatile2 useVolatile = new NoVolatile2();
        Thread thread1 = new Thread(useVolatile);
        Thread thread2 = new Thread(useVolatile);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(useVolatile.flag);
        System.out.println(useVolatile.realA.get());
    }
}
