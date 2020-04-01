package deadlock;

import java.util.Random;

/**
 * 多人同时转账，依然很危险
 */
public class MultiTransferMoney {
    private static final int NUM_ACCOUNTS = 500; // 账户个数
    private static final int NUM_MONEY = 1000;
    private static final int NUM_ITERATION = 1000000; // 转账次数
    private static final int NUM_THREADS = 20;
    private static Random random = new Random();
    private static TransferMoney.Account[] accounts = new TransferMoney.Account[NUM_ACCOUNTS];

    public static void main(String[] args) {
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new TransferMoney.Account(NUM_MONEY);
        }
        for (int i = 0; i < NUM_THREADS; i++) {
            new TransferThread().start();
        }
        System.out.println("运行结束");
    }

    static class TransferThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < NUM_ITERATION; i++) {
                int fromAcct = random.nextInt(NUM_ACCOUNTS);
                int toAcct = random.nextInt(NUM_ACCOUNTS);
                int amount = random.nextInt(NUM_MONEY);
                try {
                    TransferMoney.transferMoney(accounts[fromAcct], accounts[toAcct], amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
