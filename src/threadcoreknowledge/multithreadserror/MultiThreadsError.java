package threadcoreknowledge.multithreadserror;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程带来的问题：第一种，运行结果出错。       演示计数不准确（减少），找出具体出错的位置。
 *
 * 思路：
 * 1.在++操作前后检查count值的变化，用原子类来同步++操作，输出理应正确输出的值，以及统计出错的次数
 *
 * 2.标识每次出错的位置，通过boolean[]中的小标来标识线程操作过的数，为true表示操作过，
 *   ++正常运行应该相隔1个下标都为true，反之是相邻下标都为true。
 *
 * 3.在对boolean进行标识时，同一时间内应只有一个线程执行
 *
 * 4.一个线程在修改boolean中的值时，另一个此时不应该执行++操作，否则将影响到操作boolean的线程
 *   用cyclicBarrier来实现，一个线程在执行重要操作时，将另一个线程拦截下来
 */
public class MultiThreadsError implements Runnable {
    private static int count = 0;
    private static final MultiThreadsError instance = new MultiThreadsError();

    // 原子类，能够把count++中的三个步骤合为一个步骤，原理为：CAS
    private static AtomicInteger realCount = new AtomicInteger();
    private static AtomicInteger wrongCount = new AtomicInteger();

    // 参数中的2表示，需要等待2个线程，只有当2个线程都到达指定位置后
    // （运行完cyclicBarrier.wait()），才能放行，进行下一步操作
    private static volatile CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
    private static volatile CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

    // 用于存储count++过程中是否出错，为true时，代表已经被修改过了
    private final boolean[] marked = new boolean[10000000];

    @Override
    public void run() {
//        countNotCorrect();
        try {
            checkErrorInCount();
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 观察count++的结果
    private void countNotCorrect() {
//        while (count < 10000) {
//            count++; // 只要 < 10000就会加，结果一定是10000，加的次数实际上小于10000
//        }
        for (int j = 0; j < 10000; j++) { // 只加10000次，多线程下结果可能小于20000
            count++;
        }
    }

    // 观察count++中到底哪里少加了，思路如下：
    // 1：在每次++后观察变化，看是不是少加了
    private void checkErrorInCount() throws BrokenBarrierException, InterruptedException {
        marked[0] = true; // 0处默认标记true，因为两个线程执行++不可能为0，具体原因见下面的注释
        for (int i = 0; i < 100000; i++) {
            cyclicBarrier2.reset(); // 每次拦截前先对另一个拦截点进行重置，让其在下次循环时也起作用
            // 设下第一个拦截点，只有当两个线程都达到这里时才能进行下一步操作
            // 防止线程1在执行完后，线程2还未执行完时，线程1又再次进入这里并将count++
            cyclicBarrier1.await();
            int pre = count;
            count++; // ① 可能加了2次，也可能只加了一次，非原子操作
            cyclicBarrier1.reset();
            // 第二个拦截点，防止线程1在执行下面同步代码且还没执行完，将要修改②中的值时
            // 线程2此时却执行了①，改变了线程1本想使用的count值
            cyclicBarrier2.await();
            // 等价于count++，但是可以保证原子性，不会出错的++
            realCount.incrementAndGet(); // 统计真正计算的次数，应该是两倍循环次数
            synchronized (instance) { // 保证一个线程修改marked时另一个必须等待
                // ①正常执行时（+2次），marked[count]和marked[count-2]都为true（先进来的线程修改为了true）
                // ①出错少加时（+1次），marked[count]和marked[count-1]都为true
                // 特殊情况：当count==1时（少加了），理应是满足条件，所以marked[0]应该设为true才能判断出错了
                if (marked[count] && marked[count-1]) {
                    System.out.println("发生了错误（少加），加前：" + pre + "\t\t加后：" + count);
                    wrongCount.incrementAndGet(); // 统计count++出错的次数
                }
                marked[count] = true; // ②
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("最后的结果为：" + count);
        System.out.println("真正运行的次数：" + realCount.get());
        System.out.println("错误的计算次数：" + wrongCount.get());
    }
}
