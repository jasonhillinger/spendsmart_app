package com.example.spendsmart_soen357_app.Model;

import java.util.Date;

public class Transaction {
    private String account;
    private Double amount;
    private String category;
    private String subject;
    private String type;
    private Date date;
    private Date time;
    private String username;

    public Transaction(String account, Double amount, String category, String subject, String type, Date date, Date time, String username) {
        this.account = account;
        this.amount = amount;
        this.category = category;
        this.subject = subject;
        this.type = type;
        this.date = date;
        this.time = time;
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
