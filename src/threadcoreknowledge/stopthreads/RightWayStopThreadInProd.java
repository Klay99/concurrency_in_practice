package threadcoreknowledge.stopthreads;

/**
 * 在生产实践中的用法，最佳实践1：传递中断
 * catch了InterruptException之后的优先选择：在方法签名中抛出异常
 * 原因：如果这样做了，那么在run()中会强制try/catch
 *
 * 运行的结果为：抛出异常后程序还是会继续执行
 * 所以在实际的场景中（如在茫茫日志中就很难找到异常信息，很容易忽略掉出现的错误）在出现异常后应该做出相应的处理
 */
public class RightWayStopThreadInProd implements Runnable {

    // run无法抛出checked Exception，只能用try/catch
    // Runnable中的run没有任何对于异常的处理，所以在override时不能改变run原来的定义
    @Override
    public void run() {
        // run作为顶层方法，在Runnable接口中对run()的定义没有实现能够抛出异常，sleep定义了所以会抛出异常
        // 所以run抛出异常只能通过try/catch，而不能在方法签名中再向上层抛出
        while (true) {
            System.out.println("go");
            try {
                // 由于是在方法前面中抛出的异常，所以异常会向上传递
                // 由run来基础抛出异常，且run只能用try/catch
                throwInMethod(); // 方法1
            } catch (InterruptedException e) {
                // 捕获异常后，run能做出相应的一些处理，例如下：
                System.out.println("保存日志");
                e.printStackTrace();
            }
            // 方法2，不建议使用，在实际开发中很容易出现问题，且我们对此束手无策
            // 该方法由try/catch处理异常，所以异常的处理在该方法中，不会传递到当前的run并交由run处理
            // 所以调用该方法出现异常后，run方法不能对其进行相应的处理
//            throwInTryCatch();
        }
    }

    // 错误的异常处理方法，用try/catch后异常的处理在本方法中，不会传递到调用该方法的其它方法中
    private void throwInTryCatch() {
        try {
            // 睡眠时间大于main中的睡眠时间，达到在睡眠过程中被中断的目的
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void throwInMethod() throws InterruptedException {
        Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }

}
