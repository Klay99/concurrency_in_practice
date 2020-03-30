package jmm.singleton;

/**
 * 懒汉式（线程不安全，同步代码块）（不可用）
 */
public class Singleton5 {
    private static Singleton5 INSTANCE;
    private Singleton5() {}

    // 线程不安全
    public static Singleton5 getInstance() {
        if (INSTANCE == null) { // 当多个线程同时进入时，还是同样会创建多个实例，只不过他们是排队创建的
            synchronized (Singleton5.class) {
                INSTANCE = new Singleton5();
            }
        }
        return INSTANCE;
    }
}
