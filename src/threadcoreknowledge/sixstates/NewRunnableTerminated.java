package threadcoreknowledge.sixstates;

/**
 * 展示线程的NEW，RUNNABLE，TERMINATED状态，即使是正在运行也是runnable状态，而不是running
 */
public class NewRunnableTerminated implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new NewRunnableTerminated());
        // 打印new状态
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 打印runnable状态，即使是正在运行
        System.out.println(thread.getState());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 打印terminated状态
        System.out.println(thread.getState());
    }

}
