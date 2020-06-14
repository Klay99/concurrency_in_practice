package threadcoreknowledge.jmm.singleton;

/**
 * 双重检查（线程安全）（推荐面试用）
 */
public class Singleton6 {
    private static volatile Singleton6 INSTANCE;
    private Singleton6() {}

    // 优点：线程安全，延迟加载，效率较高
    public static Singleton6 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton6.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton6();
                }
            }
        }
        return INSTANCE;
    }
}
