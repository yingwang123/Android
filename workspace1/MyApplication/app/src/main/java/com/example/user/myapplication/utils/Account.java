package com.example.user.myapplication.utils;

/**
 * Created by user on 2016/8/29.
 */
public class Account {
    private String accountNo;
    private double balance;
    public Account(){}
    public Account(String accountNo,double balance){
        this.accountNo=accountNo;
        this.balance=balance;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
   /* public int hashCode(){
        return accountNo.hashCode();
    }*/
    public boolean equals(Object obj){
        if(this==obj){
            return true;
        }
        if(obj !=null
                && obj.getClass() == Account.class){
            Account target = (Account) obj;
            return target.getAccountNo().equals(accountNo);

        }
        return false;
    }

}
