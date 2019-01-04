package com.example.tuananh.module2;

public class SummaryInfo {
    String phone,work;
    long dBirth,dDeath;

    public SummaryInfo(String phone, String work, long dBirth, long dDeath) {
        this.phone = phone;
        this.work = work;
        this.dBirth = dBirth;
        this.dDeath = dDeath;
    }

    public SummaryInfo() {
    }

    public String getPhone() {
        return phone;
    }

    public String getWork() {
        return work;
    }

    public long getdBirth() {
        return dBirth;
    }

    public long getdDeath() {
        return dDeath;
    }
}
