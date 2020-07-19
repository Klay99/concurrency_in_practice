package concurrencytools.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 很多个线程打印日期，使用线程池的形式创建线程，每新建一个任务都会新建一个SimpleDateFormat对象，很浪费
 */
public class ThreadLocalNormalUsage02 {

    private static ExecutorService service = Executors.newFixedThreadPool(10);

    public String date(int seconds) {
        // 该构造方法参数单位为毫秒，从1970-01-01 00:00:00 GMT+8 起计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            service.submit(() -> {
                String date = new ThreadLocalNormalUsage02().date(finalI);
                System.out.println(date);
            });
            service.shutdown();
        }
    }
}
