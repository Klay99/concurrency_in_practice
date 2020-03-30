package threadcoreknowledge.threadproperties;

/**
 * 线程id，id从1开始，jvm运行起来后，我们自己创建的线程id早已不是2.
 * 原因，程序在运行时，jvm需要调度一些线程，在后台创建了一些线程用来帮助我们程序的运行
 */
public class Id {
    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println(Thread.currentThread().getName() + " id: " + Thread.currentThread().getId());
        System.out.println(thread.getName() + " id : " + thread.getId());
    }
}
