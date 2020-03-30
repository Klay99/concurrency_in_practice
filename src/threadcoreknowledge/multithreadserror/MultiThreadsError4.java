package threadcoreknowledge.multithreadserror;

/**
 * 多线程带来的问题：第三种，发布溢出，未初始化就this赋值
 */
public class MultiThreadsError4 {
    static Point point;

    public static void main(String[] args) throws InterruptedException {
        new PointMaker().start();
//        Thread.sleep(10); // 打印1,0  因为在y还未赋值时就将point赋给了当前类的point
        Thread.sleep(200); // 打印1,1
        if (point != null) {
            System.out.println(point);
        }
    }
}
class Point {
    private final int x, y;
    public Point(int x, int y) throws InterruptedException {
        this.x = x;
        MultiThreadsError4.point = this;
        Thread.sleep(100);
        this.y = y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
class PointMaker extends Thread {
    @Override
    public void run() {
        try {
            new Point(1, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}