package concurrencytools.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        // 执行定时任务
//        service.schedule(new Task(), 5, TimeUnit.SECONDS);
        // 以一定频率重复运行
        service.scheduleAtFixedRate(new Task(), 1, 3, TimeUnit.SECONDS);
    }
}
