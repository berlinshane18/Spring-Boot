package com.example.monthlystatement;

public class User
{
    private String id;
    private String name;
    private String email;
    private String address;
    private String accountNumber;
    public User(String id,String name,String email,String address,String accountNumber)
    {
        this.id=id;
        this.name=name;
        this.email=email;
        this.address=address;
        this.accountNumber=accountNumber;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getAccountNumber()
    {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }
}
