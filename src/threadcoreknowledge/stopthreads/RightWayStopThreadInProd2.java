package threadcoreknowledge.stopthreads;

/**
 * 在生产实践中的用法，最佳实践2：恢复中断
 * 在catch语句中调用Thread.currentThread().interrupt()来恢复设置中断状态
 * 以便于在后续的执行中，依然能够检查到刚才发生了中断
 * 回到刚才RightWayStopThreadInProd补上中断，让它跳出
 */
public class RightWayStopThreadInProd2 implements Runnable {

    @Override
    public void run() {
        while (true) {
            // 方法3
            if (Thread.currentThread().isInterrupted()) { // 发生中断时
                System.out.println("Interrupted，程序运行结束");
                break;
            }
            reInterrupt();
            System.out.println("保存日志");
        }
    }

    // 方法1，相比于方法3，此方法可称为：notReInterrupt
    // run中调用该方法后，并不会中断线程，而是无限的在while中不会跳出
    private void throwInTryCatch() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 方法2
    private void throwInMethod() throws InterruptedException {
        Thread.sleep(2000);
    }

    // 方法3，相比于方法1，区别在于，如果本身想处理异常
    // 但是也应该将异常信息“发送”出去，不能私自“独吞”
    private void reInterrupt() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // 虽然本方法自己处理异常，但是并没有“独吞”该信息
            // 而是将此信息重新抛出来（此处为恢复中断），以便于外界方法能够获取到
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd2());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }

}
