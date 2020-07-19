package concurrencytools.lock.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用tryLock避免死锁
 */
public class TryLockDeadLock implements Runnable {
    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();
    private int flag = 1;

    public static void main(String[] args) {
        TryLockDeadLock r1 = new TryLockDeadLock();
        TryLockDeadLock r2 = new TryLockDeadLock();
        r1.flag = 1;
        r2.flag = 2;
        new Thread(r1, "线程1").start();
        new Thread(r2, "线程2").start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (flag == 1) {
                try {
                    if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println(Thread.currentThread().getName() + "获取到了lock1");
                            Thread.sleep(new Random().nextInt(1000));

                            if (lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + "获取到了lock2");
                                    System.out.println(Thread.currentThread().getName() + "成功获取到了两把锁");
                                    break;
                                } finally {
                                    lock2.unlock();
                                    Thread.sleep(new Random().nextInt(1000));
                                }
                            } else {
                                System.out.println(Thread.currentThread().getName() + "获取lock2失败，已重试");
                            }
                        } finally {
                            lock1.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "没有获取到lock1，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (flag == 2) {
                try {
                    if (lock2.tryLock(3000, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println(Thread.currentThread().getName() + "获取到了lock2");
                            Thread.sleep(new Random().nextInt(1000));

                            if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + "获取到了lock1");
                                    System.out.println(Thread.currentThread().getName() + "成功获取到了两把锁");
                                    break;
                                } finally {
                                    lock1.unlock();
                                    Thread.sleep(new Random().nextInt(1000));
                                }
                            } else {
                                System.out.println(Thread.currentThread().getName() + "获取lock1失败，已重试");
                            }
                        } finally {
                            lock2.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "没有获取到lock2，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
