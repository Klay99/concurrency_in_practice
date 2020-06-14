package concurrencytools.threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 关闭线程池
 */
public class ShutDown {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            service.execute(new ShutDownTask());
        }
        Thread.sleep(1500);

        /*
        1 通过shutdown停止线程池
         */
//        System.out.println(service.isShutdown()); // 判断线程池是否已经进入停止状态
//        service.shutdown();
//        System.out.println(service.isShutdown());
//        System.out.println(service.isTerminated());
//        // 再提交新任务会抛出异常
//        service.execute(new ShutDownTask());
//        Thread.sleep(10000);
//        System.out.println(service.isTerminated()); // 判断线程池是否已经完全停止
        /*
        2 通过awaitTermination停止线程池
         */
//        service.shutdown();
//        // 等待一段时间，等待过程中线程池完全停止了返回true，否则false。返回结果前会一直处于阻塞状态
//        System.out.println(service.awaitTermination(7L, TimeUnit.SECONDS));
        /*
        3 通过shutDownNow停止线程池
         */
        // 该方法会用interrupt中断正在执行任务的线程，而没有被执行的任务则会返回
        List<Runnable> runnableList = service.shutdownNow(); // 返回没有被执行的任务，用来做后续的处理工作
    }
}
class ShutDownTask implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " 被中断了");
        }
        System.out.println(Thread.currentThread().getName());
    }
}
