package com.practice.bean;

import java.sql.Date;

/**
 * ORM編程思想（object relational mapping） 一個數據表對應一個java類 表中的一條記錄對應java類的一個對像
 * 表中的一個字段對應java類的一個屬性
 *
 * java與SQL對應數據類型轉換表： boolean <--> BIT byte <--> TINYINT short <--> SMALLINT int
 * <--> INTEGER long <--> BIGINT String <--> CHAR,VARCHAR,LONGVARCHAR byte array
 * <--> BINARY, VAR BINARY
 *
 * java.sql.Date <--> DATE java.sql.Time <--> TIME java.sql.Timestamp <-->
 * TIMESTAMP
 *
 * */
public class Student {
    private String stuname;
    private double stuscore;
    private int stunum;
    private Date stubirth;

    public Student() {
        super();
    }

    public Student(String stuname, double stuscore, int stunum, Date stubirth) {
        super();
        this.stuname = stuname;
        this.stuscore = stuscore;
        this.stunum = stunum;
        this.stubirth = stubirth;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public double getStuscore() {
        return stuscore;
    }

    public void setStuscore(double stuscore) {
        this.stuscore = stuscore;
    }

    public int getStunum() {
        return stunum;
    }

    public void setStunum(int stunum) {
        this.stunum = stunum;
    }

    public Date getStubirth() {
        return stubirth;
    }

    public void setStubirth(Date stubirth) {
        this.stubirth = stubirth;
    }

    @Override
    public String toString() {
        return "Student [stuname=" + stuname + ", stuscore=" + stuscore
                + ", stunum=" + stunum + ", stubirth=" + stubirth + "]";
    }

}