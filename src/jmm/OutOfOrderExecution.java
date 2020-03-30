package jmm;

import java.util.concurrent.CountDownLatch;

/**
 * 演示重排序的现象
 * 重排序不是一直出现的，用重复，直到达到某个条件才停止，来测试小概率事件
 * 下面4行代码（①②③④）的执行顺序决定了最终x和y的结果，一共有3种情况：
 * one先执行完two再执行：
 * 1.a = 1; x = b(0); b = 1; y = a(1)，最终的结果是：x = 0, y = 1
 * two先执行完one再执行：
 * 2.b = 1; y = a(0); a = 1; x = b(1)，最终的结果是：x = 1, y = 0
 * 两个线程同时执行且运行速度几乎相同：需要辅助类CountDownLatch，和循环
 * 3.a = 1; b = 1; x = b(1); y = a(1)，最终的结果是：x = 1, y = 1
 * 正常情况下永远也不可能发生的第四种情况：重排序导致这样的情况发生了
 * 4.x=b(0);b=1;y=a(0);a=1;（任何，②先于③且④先于①，情况中的一种）
 */
public class OutOfOrderExecution {
    private static int x = 0, y = 0, a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        // 达到条件跳出循环
        do { // 通过循环实现第3种情况下的小概率事件
            i++;
            x = 0; // 每次循环重置开始的值
            y = 0;
            a = 0;
            b = 0;
            CountDownLatch latch = new CountDownLatch(1); // 参数1表示倒计时次数
            Thread one = new Thread(() -> {
                try {
                    latch.await(); // 等待，当收到信号时，才能执行下面的代码
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = 1; // ①
                x = b; // ②
            });
            Thread two = new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b = 1; // ③
                y = a; // ④
            });
            one.start();
            two.start();
            latch.countDown(); // 等两个都启动了，开放await
            // 要想达到第3种情况，概率还是很小，因为不能保证await之后两个线程执行的顺序
            one.join();
            two.join();
            System.out.println("第" + i + "次： " + "x = " + x + ", y = " + y);
        } while (x != 1 || y != 1); // x=1,y=1时跳出循环

            /*
            虽然两个线程执行的速度先后可能不一样，但是每个线程内执行的顺序是不变的，
            及：①一定先于②（条件A）；③一定先于④（条件B）
            但是程序出现了在正常情况下，永远也可能出现的额外的第4种情况：x = 0, y = 0
            原因是发生了重排序，导致了：②在③前执行（条件C），④在①前执行（条件D）
            正常情况：在满足条件AB的情况下，只能满足CD中的一个，不能同时满足CD。
            重排序发生：同理满足CD就不能同时满足AB，除非A或B中的某一顺序被颠倒了（及发生了重排序）
             */
//        } while (x != 0 || y != 0); // x=0,y=0时跳出循环
    }
}