package com.example.tuananh.module1.Model;

public class DetailInfo {
    String phone,work,email,description;
    long dBirth,dDeath;
    int age;

    public DetailInfo(String phone, String work, String email, String description, long dBirth, long dDeath, int age) {
        this.phone = phone;
        this.work = work;
        this.email = email;
        this.description = description;
        this.dBirth = dBirth;
        this.dDeath = dDeath;
        this.age = age;
    }

    public DetailInfo() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getdBirth() {
        return dBirth;
    }

    public void setdBirth(long dBirth) {
        this.dBirth = dBirth;
    }

    public long getdDeath() {
        return dDeath;
    }

    public void setdDeath(long dDeath) {
        this.dDeath = dDeath;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean isEmpty(){
        if (phone!=null || phone.trim().length()>0) return false;
        if (work!=null || work.trim().length()>0) return false;
        if (email!=null || email.trim().length()>0) return false;
        if (description!=null || description.trim().length()>0) return false;
        if (dBirth!=0) return false;
        if (dDeath!=0) return false;
        return true;
    }
}