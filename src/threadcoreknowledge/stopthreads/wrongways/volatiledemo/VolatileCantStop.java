package threadcoreknowledge.stopthreads.wrongways.volatiledemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 演示用volatile的局限：2.陷入阻塞时，volatile是无法停止线程的。
 * 此例描述：生产者的生产速度很快，消费者的消费速度很慢，
 * 所以会出现阻塞队列满了的情况，满了以后生产者会阻塞并等待消费者继续消费
 */
public class VolatileCantStop {

    public static void main(String[] args) throws InterruptedException {
        // 相当于队列，满时放或空时取，都会阻塞
        ArrayBlockingQueue storage = new ArrayBlockingQueue(10);

        Producer producer = new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread.sleep(1000);

        Consumer consumer = new Consumer(storage);
        while (consumer.needMoreNums()) {
            System.out.println(consumer.storage.take() + "被消费了");
            Thread.sleep(300); // 让消费者多睡一会，及消费者消费速度很慢
        }
        System.out.println("消费者不需要更多数据了");
        // 一旦消费者不需要更多数据了，我们应该让生产者也停下来
        producer.canceled = true;
    }

}

class Producer implements Runnable {
    public volatile boolean canceled = false;
    BlockingQueue storage; // 阻塞队列，仓库
    public Producer(BlockingQueue storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (num <= 100000 && !canceled) { // 每次循环都会进行检查
                if (num % 100 == 0) {
                    // 线程不能响应中断的原因：
                    // 因为当发生阻塞时，下面的put就不会执行（满了），而后面的代码也都不会执行
                    // 及不会进入到下一轮循环当中，所以循环中的判断无法检查到已被中断（canceled）
                    storage.put(num); // 满了后，线程只会一直等在这里
                    System.out.println(num + "是100的倍数，放入了仓库中");
                }
                num++;
                Thread.sleep(1); // 生产者生产速度很快
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("生产者停止运行");
        }
    }
}

class Consumer {

    BlockingQueue storage;

    public Consumer(BlockingQueue storage) {
        this.storage = storage;
    }

    public boolean needMoreNums() {
        return !(Math.random() > 0.95); // 消费者需要消费的概率为95%，消费速度很慢
    }
}