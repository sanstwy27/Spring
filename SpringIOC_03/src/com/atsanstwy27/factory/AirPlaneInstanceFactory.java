package com.atsanstwy27.factory;

import com.atsanstwy27.bean.AirPlane;

/**
 * 實例工廠
 */

public class AirPlaneInstanceFactory {

    //new AirPlaneInstanceFactory().getAirPlane();
    public AirPlane getAirPlane(String jzName) {
        System.out.println("AirPlaneInstanceFactory...正在造飛機");
        AirPlane airPlane = new AirPlane();
        airPlane.setFdj("太行");
        airPlane.setFjsName("lfy");
        airPlane.setJzName(jzName);
        airPlane.setPersonNumber(300);
        airPlane.setYc("198.98m");
        return airPlane;
    }

}
