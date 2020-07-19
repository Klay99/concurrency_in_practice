package concurrencytools.lock.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示悲观锁和乐观锁
 */
public class PessimismOptimismLock {
    // 乐观锁
    public void optimism() {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet(); // 采用CAS实现原子操作，保证并发安全
    }

    // 悲观锁，在访问资源前，必须先要获取锁
    public synchronized void pessimism() {

    }
}
