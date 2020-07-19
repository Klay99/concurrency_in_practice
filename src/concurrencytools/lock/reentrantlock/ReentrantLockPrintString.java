package concurrencytools.lock.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示ReentrantLock打印字符串
 */
public class ReentrantLockPrintString {
    private static Lock lock = new ReentrantLock();

    private void init() {
        final Print print = new Print();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                print.print("abcdefghijklmnopqrstuvwxyz");
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                print.print("0123465789");
            }
        }).start();
    }

    public static void main(String[] args) {
        new ReentrantLockPrintString().init();
    }

static class Print{
    public void print(String name) {
        int len = name.length();
        lock.lock();
        try {
            for (int i = 0; i < len; i++) {
                System.out.print(name.charAt(i));
            }
            System.out.println();
        } finally {
            lock.unlock();
        }
    }
}

}
