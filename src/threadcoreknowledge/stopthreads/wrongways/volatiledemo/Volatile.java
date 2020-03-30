package threadcoreknowledge.stopthreads.wrongways.volatiledemo;

/**
 * 演示用volatile的局限：1.看似可行
 */
public class Volatile implements Runnable {

    // 模拟interrupt，为false时表示没有收到中断信息，反之收到中断信息
    private volatile boolean canceled = false;

    @Override
    public void run() {
        int num = 0;
        try {
            while (num <= 100000 && !canceled) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的倍数");
                }
                num++;
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Volatile v = new Volatile();
        Thread thread = new Thread(v);
        thread.start();
        Thread.sleep(5000);
        v.canceled = true;
    }

}
