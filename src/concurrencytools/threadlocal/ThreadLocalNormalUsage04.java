package concurrencytools.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过加锁保证多个线程共用同一资源且做修改时是线程安全的
 * 此时的结果中不会出现多个线程打印同一结果的情况，可以把结果放入Set中验证
 * 同一时间只有一个线程能打印，并发度很低
 */
public class ThreadLocalNormalUsage04 {

    private static ExecutorService service = Executors.newFixedThreadPool(10);
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public String date(int seconds) {
        // 该构造方法参数单位为毫秒，从1970-01-01 00:00:00 GMT+8 起计时
        Date date = new Date(1000 * seconds);
        String s;
        // 将核心代码用锁保证线程安全
        synchronized (ThreadLocalNormalUsage04.class) {
            s = format.format(date);
        }
        return s;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            service.submit(() -> {
                String date = new ThreadLocalNormalUsage04().date(finalI);
                System.out.println(date);
            });
        }
        service.shutdown();
    }
}
