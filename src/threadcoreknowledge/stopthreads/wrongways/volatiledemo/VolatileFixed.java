package threadcoreknowledge.stopthreads.wrongways.volatiledemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用中断来修复刚才的无尽等待的问题
 */
public class VolatileFixed {
    public static void main(String[] args) throws InterruptedException {
        VolatileFixed body = new VolatileFixed();
        // 相当于队列，满时放或空时取，都会阻塞
        ArrayBlockingQueue storage = new ArrayBlockingQueue(10);

        Producer producer = body.new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread.sleep(1000);

        Consumer consumer = body.new Consumer(storage);
        while (consumer.needMoreNums()) {
            System.out.println(consumer.storage.take() + "被消费了");
            Thread.sleep(300); // 让消费者多睡一会，及消费者消费速度很慢
        }
        System.out.println("消费者不需要更多数据了");
        // 一旦消费者不需要更多数据了，我们应该让生产者也停下来
        producerThread.interrupt();
    }

    class Producer implements Runnable {
        BlockingQueue storage; // 阻塞队列，仓库
        public Producer(BlockingQueue storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            int num = 0;
            try {
                while (num <= 100000 && !Thread.currentThread().isInterrupted()) { // 每次循环都会进行检查
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
}
