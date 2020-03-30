package threadcoreknowledge.multithreadserror;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Test implements Runnable {
    private static int index = 0;
    private static final Test instance = new Test();
    private static AtomicInteger realIndex = new AtomicInteger();
    private static AtomicInteger wrongCount = new AtomicInteger();
    private static CyclicBarrier cyclicBarrier1 = new CyclicBarrier(3);
    private static CyclicBarrier cyclicBarrier2 = new CyclicBarrier(3);
    private static boolean[] marked = new boolean[10000000];

    @Override
    public void run() {
        for (int i = 0; i < 50000; i++) {
            int pre = index;
            cyclicBarrier2.reset();
            try {
                cyclicBarrier1.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            index++;
            cyclicBarrier1.reset();
            try {
                cyclicBarrier2.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            realIndex.incrementAndGet();
            synchronized (instance) {
                boolean[] isWrong = new boolean[index-pre];
                for (int j = 0; j < index - pre; j++) {
                    if (marked[index-j]) {
                        isWrong[j] = true;
                    } else {
                        isWrong[j] = false;
                    }
                    marked[index-j] = true;
                }
                boolean res = isWrong[0];
                for (int i1 = 1; i1 < isWrong.length; i1++) {
                    res = res && isWrong[i1];
                }
                if (res) {
                    System.out.println("wrong location,pre:" + pre + "\t\tindex:" + index);
                }
                if (marked[index]) {
                    if (marked[index-1]) {

                    }
                    System.out.println("wrong location,pre:" + pre + "\t\tindex:" + index);
                    wrongCount.incrementAndGet();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        Thread thread3 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("index: " + index);
        System.out.println("real index: " + realIndex.get());
        System.out.println("wrong count: " + wrongCount.get());
    }
}
