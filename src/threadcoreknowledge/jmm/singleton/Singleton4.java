package threadcoreknowledge.jmm.singleton;

/**
 * 懒汉式（线程安全，同步方法）（不推荐用）
 */
public class Singleton4 {
    private static Singleton4 INSTANCE;
    private Singleton4() {}

    // 缺点：效率很低
    public static synchronized Singleton4 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton4();
        }
        return INSTANCE;
    }
}
