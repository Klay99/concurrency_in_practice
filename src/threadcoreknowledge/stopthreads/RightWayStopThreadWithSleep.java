package threadcoreknowledge.stopthreads;

/**
 * 带有sleep的中断线程的写法
 */
public class RightWayStopThreadWithSleep {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int num = 0;
            try {
                // 这里设置的循环次数很小，为了方便线程快速进入下面的睡眠状态
                // 线程响应中断的方式有两种，1判断当前线程是否被中断；2抛出异常时自动处理中断
                while (num <= 300 && !Thread.currentThread().isInterrupted()) {
                    if (num % 100 == 0) {
                        System.out.println(num + "是100的倍数");
                    }
                    num++;
                }
                // （模拟阻塞）睡眠过程中收到中断信号时，会抛出异常
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }

}
