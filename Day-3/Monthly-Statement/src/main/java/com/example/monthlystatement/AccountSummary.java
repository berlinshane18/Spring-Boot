package com.example.monthlystatement;

public class AccountSummary
{
    private double totalDeposits;
    private double totalWithdrawals;
    private double endingBalance;

    public AccountSummary(double totalDeposits,double totalWithdrawals,double endingBalance)
    {
        this.totalDeposits=totalDeposits;
        this.totalWithdrawals=totalWithdrawals;
        this.endingBalance=endingBalance;
    }
    public double getTotalDeposits()
    {
        return totalDeposits;
    }
    public void setTotalDeposits(double totalDeposits)
    {
        this.totalDeposits = totalDeposits;
    }
    public double getTotalWithdrawals()
    {
        return totalWithdrawals;
    }
    public void setTotalWithdrawals(double totalWithdrawals)
    {
        this.totalWithdrawals = totalWithdrawals;
    }
    public double getEndingBalance()
    {
        return endingBalance;
    }
    public void setEndingBalance(double endingBalance)
    {
        this.endingBalance = endingBalance;
    }
}
