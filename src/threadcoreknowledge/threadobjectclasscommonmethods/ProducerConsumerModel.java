package threadcoreknowledge.threadobjectclasscommonmethods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用wait/notify来实现生产者消费者模式
 */
public class ProducerConsumerModel {
    public static void main(String[] args) {
        EventStorage storage = new EventStorage();
        Producer producer = new Producer(storage);
        Consumer consumer = new Consumer(storage);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

class Producer implements Runnable {
    private EventStorage storage;

    public Producer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            storage.put();
        }
    }
}

class Consumer implements Runnable {
    private EventStorage storage;

    public Consumer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            storage.take();
        }
    }
}

class EventStorage { // 阻塞队列
    private int maxSize;
    private List<Date> storage;

    public EventStorage() {
        maxSize = 10;
        storage = new ArrayList<>();
    }

    public synchronized void put() {
        while (storage.size() == maxSize) {
            try {
                wait(); // 队列满时，停止生产，并释放锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.add(new Date());
        System.out.println("仓库里有了" + storage.size() + "个产品");
        notify(); // 换线消费者继续消费
    }

    public synchronized void take() {
        while (storage.size() == 0) {
            try {
                wait(); // 队列为空时，停止消费，并释放锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("拿到了" + storage.remove(0) + "，现在仓库还剩下" + storage.size() + "个产品");
        System.out.println("仓库里有了" + storage.size() + "个产品");
        notify(); // 唤醒生产者继续生产
    }
}