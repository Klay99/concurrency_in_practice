package threadcoreknowledge.stopthreads;

/**
 * 如果在执行过程中，每次循环都会调用sleep或wait等方法，那么不需要在每次迭代时都进行是否被中断的判断
 */
public class RightWayStopThreadWithSleepEveryLoop {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int num = 0;
            try {
                // 线程响应中断的方式有两种，1判断当前线程是否被中断；2抛出异常时自动处理中断
                while (num <= 10000) { // 此时循环中无须判断当前线程是否被中断
                    if (num % 100 == 0) {
                        System.out.println(num + "是100的倍数");
                    }
                    num++;
                    // 上面的代码程序运算速度很快，程序基本上会在下面的休眠语句中接收到中断信号
                    // 线程睡眠中被中断时，会抛出异常（处理中断），所以无须在每次循环时进行判断
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }

}
