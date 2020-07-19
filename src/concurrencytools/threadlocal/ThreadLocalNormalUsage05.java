package concurrencytools.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过ThreadLocal实现，给线程池中每个线程分配一个SimpleDateFormat对象
 * 资源占用少，同时也保证了线程安全，也不会带来低并发度的问题
 */
public class ThreadLocalNormalUsage05 {

    private static ExecutorService service = Executors.newFixedThreadPool(10);

    public String date(int seconds) {
        // 该构造方法参数单位为毫秒，从1970-01-01 00:00:00 GMT+8 起计时
        Date date = new Date(1000 * seconds);
        // 通过ThreadLocal拿到每个线程自己的dateFormat对象
        SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
        return dateFormat.format(date);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            service.submit(() -> {
                String date = new ThreadLocalNormalUsage05().date(finalI);
                System.out.println(date);
            });
        }
        service.shutdown();
    }
}
class ThreadSafeFormatter {
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        }
    };
    // 用lambda
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal2 = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
}