package com.example.user.myapplication;

/**
 * Created by user on 2016/8/29.
 */
public class DrawThread extends Thread{
    private Account account;
    private double drawAccount;

    public DrawThread(String name,Account account, double drawAccount) {
        super(name);
        this.account = account;
        this.drawAccount = drawAccount;
    }
    public void run() {
        //使用account作为同步监视器，任何线程进下面同步代码块之前
        //必须先获得对account账户的锁定--其他线程无法获得锁，也就无法修改它
        //这种做法符合：加锁--修改--释放锁的逻辑
        synchronized (account){
            if(account.getBalance()>=drawAccount){
                System.out.println(getName() + "取钱成功！吐出钞票：" + drawAccount);
                try {
                    Thread.sleep(10);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                account.setBalance(account.getBalance()-drawAccount);
                System.out.println("\t余额为："+account.getBalance());
            }else{
                System.out.println(getName() + "取钱失败！余额不足！");
            }
        }

    }
}
