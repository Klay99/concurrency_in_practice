package jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 适用于volatile的情况1：只对变量进行赋值
 */
public class UseVolatile implements Runnable {
    private volatile boolean flag = false; // 重点不在于是否是boolean类型，而是在setFlag中的操作
    private AtomicInteger realA = new AtomicInteger();


    private void setFlag() { // 该方法只对变量进行了赋值是原子性操作是线程安全的，不同于i++
        flag = true;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            setFlag();
            realA.incrementAndGet();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseVolatile useVolatile = new UseVolatile();
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
