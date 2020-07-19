package concurrencytools.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 很多个线程打印日期，使用线程池的形式创建线程，使用一个SimpleDateFormat，让线程共享使用
 * 但会出现多个线程打印同样结果的情况，显然这是错误的，发生了线程安全问题
 */
public class ThreadLocalNormalUsage03 {

    private static ExecutorService service = Executors.newFixedThreadPool(10);
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public String date(int seconds) {
        // 该构造方法参数单位为毫秒，从1970-01-01 00:00:00 GMT+8 起计时
        Date date = new Date(1000 * seconds);
        return format.format(date);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            service.submit(() -> {
                String date = new ThreadLocalNormalUsage03().date(finalI);
                System.out.println(date);
            });
        }
        service.shutdown();
    }
}
