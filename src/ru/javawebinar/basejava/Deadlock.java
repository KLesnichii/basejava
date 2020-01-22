package ru.javawebinar.basejava;

public class Deadlock {
    static class BankAccount {
        private double balance;

        public BankAccount(double balance) {
            this.balance = balance;
        }

        private synchronized void putMoney(double amount) {
            balance += amount;
            //System.out.println("transferred to account, balance " + balance);
        }

        public synchronized void transfer(BankAccount to, double amount) {
            //System.out.println("withdrawn " + amount + " from the account");
            balance -= amount;
            Thread thread = new Thread(() -> {
                to.putMoney(amount);
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BankAccount accountA = new BankAccount(10_000);
        BankAccount accountB = new BankAccount(10_000);

        new Thread(() -> accountA.transfer(accountB, 500)).start();
        new Thread(() -> accountB.transfer(accountA, 100)).start();
    }
}
