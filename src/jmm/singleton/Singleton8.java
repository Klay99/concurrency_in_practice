package jmm.singleton;

/**
 * 枚举类（线程安全）（推荐用，最好）
 * 1.写法简单
 * 2.线程安全：
 * 反编译后的enum会变成final class且extends Enum，而Enum中的实例是通过static定义的（懒加载）
 * 3.避免反序列化破坏单例
 */
public enum Singleton8 {
    INSTANCE;

    public void doSomething() {}
}
