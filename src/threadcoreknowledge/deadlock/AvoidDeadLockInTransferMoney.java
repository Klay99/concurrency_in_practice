package threadcoreknowledge.deadlock;

/**
 * 转账时避免死锁：通过hashcode来决定获取锁的顺序、冲突时需要“加时赛”
 * 实际开发中，可以根据主键来决定获取锁的顺序，也不会出现相同冲突的情况
 * 避免多个锁出现同时请求对方锁的情况，让锁的获取顺序不被打乱，保持一定秩序
 */
public class AvoidDeadLockInTransferMoney implements Runnable {
    private int flag = 1;
    private static TransferMoney.Account a = new TransferMoney.Account(500);
    private static TransferMoney.Account b = new TransferMoney.Account(500);
    private static final Object lock = new Object(); // hash冲突时，额外的锁，再次竞争

    @Override
    public void run() {
        if (flag == 1) {
            try {
                transferMoney(a, b, 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (flag == 0) {
            try {
                transferMoney(b, a, 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void transferMoney(TransferMoney.Account from, TransferMoney.Account to, int amount) throws InterruptedException {
        class Helper {
            public void transfer() {
                if (from.balance - amount < 0) {
                    System.out.println("余额不足，无法转账");
                } else {
                    from.balance -= amount;
                    to.balance += amount;
                    System.out.println("成功转账" + amount + "元");
                }
            }
        }
        int fromHash = System.identityHashCode(from); // 通过hashcode大小来决定获取锁的顺序
        int toHash = System.identityHashCode(to);
        // 每次会保持一定的秩序，不会出现获取锁的顺序被打乱，从而引发循环等待
        // 例：A, B两个人同时转账，且hashA < hashB
        if (fromHash < toHash) { // A转给B，A<B，先获取锁A再获取锁B
            synchronized (from) {
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) { // 如果相比上个分支，此次转账双方互换，则改变获取锁的顺序
            synchronized (to) { // 获取锁的顺序同上一个分支
                synchronized (from) { // B转给A，B>A，先获取锁A再获取锁B
                    new Helper().transfer();
                }
            }
        } else { // 当转账双方hashcode相同时，再让它们参与一次竞争，先拿到锁的先执行
            synchronized (lock) {
                synchronized (from) { // hashcode相同时，无论什么顺序都可以，因为多了一次竞争，不会发生死锁问题
                    synchronized (to) {
                        new Helper().transfer();
                    }
                }
            }
        }
        synchronized (from) {
            Thread.sleep(50);
            synchronized (to) {
                if (from.balance - amount < 0) {
                    System.out.println("余额不足，无法转账");
                } else {
                    from.balance -= amount;
                    to.balance += amount;
                    System.out.println("成功转账" + amount + "元");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AvoidDeadLockInTransferMoney r1 = new AvoidDeadLockInTransferMoney();
        AvoidDeadLockInTransferMoney r2 = new AvoidDeadLockInTransferMoney();
        r1.flag = 1;
        r2.flag = 0;
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("a的余额" + a.balance);
        System.out.println("b的余额" + b.balance);
    }

}
