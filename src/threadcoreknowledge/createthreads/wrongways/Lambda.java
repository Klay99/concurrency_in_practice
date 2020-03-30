package threadcoreknowledge.createthreads.wrongways;

/**
 * lambda表达式实现线程
 **/
public class Lambda {

    public static void main(String[] args) {
        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
    }

}
