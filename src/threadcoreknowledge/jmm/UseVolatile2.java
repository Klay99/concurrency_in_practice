package threadcoreknowledge.jmm;

/**
 * 适用于volatile的情况2：作为刷新之前变量的触发器
 * 参考FieldVisibility类中的例子
 */
public class UseVolatile2 {
    private int a = 1;
    private volatile int b = 2;

    private void change() { // 改变a，b的值
        // do something，假设这里还有执行很多的操作，且都没有被保护起来
        a = 3; //
        // b有被volatile修饰，这里的b其实就起到了触发器的作用（近朱者赤，详见笔记）
        b = a; // 这里也可以是：b=0/boolean=true等等其它的只写入/赋值操作，只要被volatile修饰，就能起到触发器的作用
    }

    private void print() {
        /*
        if(b==?) 这里对b（上个线程执行的最后一条写操作）进行读操作，将后面的语句包起来
        由于happens-before原则，下面的语句就一定能看到之前的所有操作
         */
        System.out.println("b = " + b + ", a = " + a);
    }

    public static void main(String[] args) {
        while (true) {
            UseVolatile2 test = new UseVolatile2();
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
