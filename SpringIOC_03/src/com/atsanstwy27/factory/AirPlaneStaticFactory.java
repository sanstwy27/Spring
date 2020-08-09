package com.atsanstwy27.factory;

import com.atsanstwy27.bean.AirPlane;

/**
 * 靜態工廠
 */

public class AirPlaneStaticFactory {

    public static AirPlane getAirPlane(String jzName) {
        System.out.println("AirPlaneStaticFactory...正在為你造飛機");
        AirPlane airPlane = new AirPlane();
        airPlane.setFdj("太行");
        airPlane.setFjsName("lfy");
        airPlane.setJzName(jzName);
        airPlane.setPersonNumber(300);
        airPlane.setYc("198.98m");
        return airPlane;
    }

}
