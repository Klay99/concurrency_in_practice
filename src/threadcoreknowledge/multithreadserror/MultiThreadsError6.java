package threadcoreknowledge.multithreadserror;

import java.util.HashMap;
import java.util.Map;

/**
 * 多线程带来的问题：第三种，发布溢出，构造函数中新建线程
 */
public class MultiThreadsError6 {
    private Map<String, String> states;

    /*
    不能在构造函数中用新建线程的方式做初始化工作，
    因为初始化是在另一个线程中，它还没执行完毕，所以会抛空指针异常。
    实际开发中，常表现为：构造函数中拿线程池的引用，拿数据库的连接池
     */
    public MultiThreadsError6() {
        new Thread(() -> { // 通过新建子线程来做初始化工作
            states = new HashMap<>();
            states.put("1", "周一");
            states.put("2", "周二");
            states.put("3", "周三");
            states.put("4", "周四");
        }).start();
    }

    public Map<String, String> getStates() {
        return states;
    }

    public static void main(String[] args) throws InterruptedException {
        MultiThreadsError6 multiThreadsError6 = new MultiThreadsError6();
        // 但是当主线程先休眠一段时间，等待构造函数中的子线程先执行完，这样一来就不会出空指针异常
        // 这样带来的问题是，时间不同程序的结果也不同，这样就导致程序的稳定性是极差的。（可通过工厂模式来修复）
        Thread.sleep(1000);
        System.out.println(multiThreadsError6.getStates().get("1"));
    }
}
