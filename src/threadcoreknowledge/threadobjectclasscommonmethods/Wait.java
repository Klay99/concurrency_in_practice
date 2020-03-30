package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 展示wait()和notify()的基本用法
 * 1.研究代码的执行顺序
 * 2.证明wait()释放锁
 */
public class Wait {
    public static Object object = new Object(); // 普通对象，没什么要求，只用来执行wait()和notify()

    static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("线程" + Thread.currentThread().getName() + "开始执行了");
                try {
                    System.out.println("线程" + Thread.currentThread().getName() + "调用了wait()");
                    // 执行等待期间被中断，会抛出异常
                    object.wait(); // 会释放锁，否则thread2也无法进入同一对象锁的方法中，并唤醒thread1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 上面释放锁后，被其它线程唤醒后会继续等待获取锁，当其它线程执行完释放锁后，
                // 本线程才能获取到锁并完成剩余工作（执行以下语句）
                System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁");
            }
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("线程" + Thread.currentThread().getName() + "调用了notify()");
                // 唤醒了同样需要该锁的线程，但是被唤醒的线程并不会立刻得到锁，而是要等本线程执行完并释放锁后才行
                object.notify();
                System.out.println("线程" + Thread.currentThread().getName() + "执行完毕");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        Thread.sleep(200); // 让thread1先进入wait，然后再将它唤醒
        thread2.start();
    }

}
