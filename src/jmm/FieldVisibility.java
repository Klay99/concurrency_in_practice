package jmm;

/**
 * 正常情况：
 * 1.线程1先执行完毕线程2再执行：a = 3, b = 3
 * 2.线程2先执行完毕线程1再执行：a = 1, b = 2
 * 3.线程1，2交替执行：a = 3, b = 2
 * 可见性带来的非正常情况：
 * 4.线程1执行完后线程2只看到了修改后的b，看不到修改后的a：a = 1, b = 3
 */
public class FieldVisibility {
    private int a = 1, b = 2;
//    private volatile int a = 1, b = 2; // 加上volatile后就不会出现第4种情况了

    private void change() { // 改变a，b的值
        a = 3;
        b = a;
    }

    private void print() {
        System.out.println("b = " + b + ", a = " + a);
    }

    public static void main(String[] args) {
        while (true) {
            FieldVisibility test = new FieldVisibility();
            new Thread(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.change();
            }).start();
            new Thread(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.print();
            }).start();
        }
    }
}
