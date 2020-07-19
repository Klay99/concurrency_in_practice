package concurrencytools.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 两个线程打印日期
 */
public class ThreadLocalNormalUsage00 {

    public String date(int seconds) {
        // 该构造方法参数单位为毫秒，从1970-01-01 00:00:00 GMT+8 起计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            String date = new ThreadLocalNormalUsage00().date(10);
            System.out.println(date);
        }).start();
        new Thread(() -> {
            String date = new ThreadLocalNormalUsage00().date(104707);
            System.out.println(date);
        }).start();
    }
}
