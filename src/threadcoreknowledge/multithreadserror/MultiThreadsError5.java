package threadcoreknowledge.multithreadserror;

/**
 * 多线程带来的问题：第三种，发布溢出，观察者模式
 */
public class MultiThreadsError5 {
    int count;

    public MultiThreadsError5(MySource mySource) {
        // 此匿名内部类直接对外界的类做了引用，能对原来的类进行改变
        mySource.registerListener(e -> System.out.println("\n我得到的数字是" + count));
        for (int i = 0; i < 10000; i++) {
            System.out.print(i);
        }
        count = 100;
    }

    public static void main(String[] args) {
        MySource mySource = new MySource();
        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mySource.eventCome(new Event() {
            });
        }).start();
        MultiThreadsError5 multiThreadsError5 = new MultiThreadsError5(mySource);
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
