package concurrencytools.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 很多个线程打印日期，使用for循环的形式创建线程，创建/销毁线程开销大
 */
public class ThreadLocalNormalUsage01 {

    public String date(int seconds) {
        // 该构造方法参数单位为毫秒，从1970-01-01 00:00:00 GMT+8 起计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(() -> {
                String date = new ThreadLocalNormalUsage01().date(finalI);
                System.out.println(date);
            }).start();
            Thread.sleep(100);
        }
    }
}
