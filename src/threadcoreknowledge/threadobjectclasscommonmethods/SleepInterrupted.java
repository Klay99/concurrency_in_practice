package threadcoreknowledge.threadobjectclasscommonmethods;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 每隔1秒钟输出当前时间，被中断，观察。
 * 写法一：Thread.sleep()
 * 写法二（更优雅）：TimeUnit.SECONDS.sleep()
 */
public class SleepInterrupted implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(new Date());
            try {
                System.out.println("进入sleep");
                TimeUnit.SECONDS.sleep(1); // 单位秒
            } catch (InterruptedException e) {
                System.out.println("我被中断了！");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new SleepInterrupted());
        thread.start();
        Thread.sleep(5000); // 写法一，对比写法二
        thread.interrupt();
    }
}
