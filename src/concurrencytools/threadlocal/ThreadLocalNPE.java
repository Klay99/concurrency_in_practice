package concurrencytools.threadlocal;

/**
 * ThreadLocal带来的空指针异常
 */
public class ThreadLocalNPE {
    ThreadLocal<Long> longThreadLocal = new ThreadLocal<>();

    public void set() {
        longThreadLocal.set(Thread.currentThread().getId());
    }

//    public Long get() { // 使用Long将返回一个“null”的字符串，而不会抛NPE
    public long get() {
        // 这里的返回类型是long基本类型，而下面的get()返回的是Long
        // 装箱拆箱中，在将null的Long转化为long时会抛出NPE
        return longThreadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalNPE threadLocalNPE = new ThreadLocalNPE();
        // 当没有set就get时，会返回null
        System.out.println(threadLocalNPE.get());
        new Thread(() -> {
            threadLocalNPE.set();
            System.out.println(threadLocalNPE.get());
        }).start();
    }
}
