package deadlock;

/**
 * 转账时遇到死锁，放开下面注释后，便会发生死锁
 */
public class TransferMoney implements Runnable {
    private int flag = 1;
    private static Account a = new Account(500);
    private static Account b = new Account(500);

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

    public static void transferMoney(Account from, Account to, int amount) throws InterruptedException {
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
        TransferMoney r1 = new TransferMoney();
        TransferMoney r2 = new TransferMoney();
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

    public static class Account {
        int balance; // 余额
        public Account(int balance) {
            this.balance = balance;
        }
    }
}
