package jmm.singleton;

/**
 * 静态内部类（线程安全）（推荐用）
 */
public class Singleton7 {
    private Singleton7() {}
    // 静态内部类，只有当被调用时才对instance进行初始化（懒汉式）
    private static class SingletonInstance {
        private static final Singleton7 INSTANCE = new Singleton7();
    }

    // 优点：线程安全，延迟加载，效率较高
    public static Singleton7 getInstance() {
        return SingletonInstance.INSTANCE;
    }
}
