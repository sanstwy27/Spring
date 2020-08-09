package com.atsanstwy27.bean;

public class AirPlane {

    private String fdj; //發動機
    private String yc;  //翼長
    private Integer personNumber; //載客量
    private String jzName; //機長名字
    private String fjsName; //副駕駛名字

    public String getFdj() {
        return fdj;
    }

    public void setFdj(String fdj) {
        this.fdj = fdj;
    }

    public String getYc() {
        return yc;
    }

    public void setYc(String yc) {
        this.yc = yc;
    }

    public Integer getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(Integer personNumber) {
        this.personNumber = personNumber;
    }

    public String getJzName() {
        return jzName;
    }

    public void setJzName(String jzName) {
        this.jzName = jzName;
    }

    public String getFjsName() {
        return fjsName;
    }

    public void setFjsName(String fjsName) {
        this.fjsName = fjsName;
    }

    @Override
    public String toString() {
        return "AirPlane [fdj=" + fdj + ", yc=" + yc + ", personNumber="
                + personNumber + ", jzName=" + jzName + ", fjsName=" + fjsName
                + "]";
    }

}
