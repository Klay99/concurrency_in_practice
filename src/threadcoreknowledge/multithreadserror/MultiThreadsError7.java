package threadcoreknowledge.multithreadserror;

/**
 * 用工厂模式来修复：对象未初始化就发布到外界
 */
public class MultiThreadsError7 {
    private int count;
    private EventListener listener;

    // 将构造函数私有化，不提供给外界访问
    private MultiThreadsError7(MySource mySource) {
        // 此匿名内部类直接对外界的类做了引用，能对原来的类进行改变
        listener = e -> System.out.println("\n我得到的数字是" + count);
        for (int i = 0; i < 10000; i++) {
            System.out.print(i);
        }
        count = 100;
    }

    public static MultiThreadsError7 getInstance(MySource source) {
        MultiThreadsError7 safeListener = new MultiThreadsError7(source);
        // 在完成上面的构造初始化工作后，才将其注册进去，让对象在初始化完毕后再发布
        source.registerListener(safeListener.listener);
        return safeListener;
    }

    public static void main(String[] args) {
        MySource mySource = new MultiThreadsError7.MySource();
        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mySource.eventCome(new MultiThreadsError7.Event() {
            });
        }).start();
        MultiThreadsError7 multiThreadsError7 = new MultiThreadsError7(mySource);
    }

    static class MySource {
        private EventListener listener;
        // 注册监听器
        void registerListener(EventListener eventListener) {
            this.listener = eventListener;
        }
        // 当事件来时，做一些处理
        void eventCome(Event e) {
            if (listener != null) {
                listener.onEvent(e);
            } else {
                System.out.println("还未初始化完毕");
            }
        }
    }
    // 事件监听器
    interface EventListener {
        void onEvent(Event e); // 监听事件
    }
    // 事件类
    interface Event {
    }
}
