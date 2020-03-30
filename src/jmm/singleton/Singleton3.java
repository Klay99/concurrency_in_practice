package jmm.singleton;

/**
 * 懒汉式（线程不安全）（不可用）
 */
public class Singleton3 {
    private static Singleton3 INSTANCE;
    private Singleton3() {}

    public static Singleton3 getInstance() {
        if (INSTANCE == null) { // 当多个线程访问时，很可能会创建多个实例
            INSTANCE = new Singleton3();
        }
        return INSTANCE;
    }
}
