package threadcoreknowledge.createthreads;

/**
 * 同时使用Runnable和Thread两种实现线程的方式
 **/
public class BothRunnableAndThread {

    public static void main(String[] args) {
        // 创建了一个匿名内部类，Thread类
        new Thread(new Runnable() { // 传入了Runnable对象
            @Override
            public void run() {
                System.out.println("我来自Runnable");
            }
        }) { // 重写了Thread中的run方法
            @Override
            public void run() { // 此时该方法实际上已经覆盖了其父类的run方法
                System.out.println("我来自Thread");
            }
        }.start();
    }

}
