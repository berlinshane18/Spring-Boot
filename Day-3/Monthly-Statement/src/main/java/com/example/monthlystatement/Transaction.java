package com.example.monthlystatement;

public class Transaction
{
    private String date;
    private String description;
    private double amount;
    private double balance;

    public Transaction(String date,String description,double amount,double balance)
    {
        this.date=date;
        this.description=description;
        this.amount=amount;
        this.balance=balance;
    }
    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getBalance()
    {
        return balance;
    }
    public void setBalance(double balance)
    {
        this.balance = balance;
    }
}
