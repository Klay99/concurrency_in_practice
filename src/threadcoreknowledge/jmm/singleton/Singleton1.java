package threadcoreknowledge.jmm.singleton;

/**
 * 饿汉式（静态常量）（可用，但是没有懒加载）
 */
public class Singleton1 {
    private static final Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {

    }

    public static Singleton1 getInstance() {
        return INSTANCE;
    }
}
