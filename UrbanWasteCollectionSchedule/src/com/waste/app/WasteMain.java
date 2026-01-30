package com.waste.app;

import com.waste.bean.Citizen;
import com.waste.service.WasteService;
import java.sql.Date;

public class WasteMain {

    private static WasteService service = new WasteService();

    public static void main(String[] args) {

        try {
            Citizen c = new Citizen();
            c.setCitizenID("CT2001");
            c.setFullName("Meenakshi Rao");
            c.setHouseOrBuildingName("Flat 5C");
            c.setStreetName("Sunrise Residency Road");
            c.setAreaOrLocality("Lakshmi Nagar");
            c.setWardCode("WARD10");
            c.setRouteCode("RT07");
            c.setMobile("9998887771");
            c.setEmail("meenakshi.rao@example.com");

            System.out.println(service.registerNewCitizen(c)
                    ? "CITIZEN REGISTERED"
                    : "ALDREADY THERE....");

            Date today = new Date(System.currentTimeMillis());
            System.out.println(service.registerComplaint(
                    "CT2001",
                    "Missed Pickup",
                    "Garbage not collected",
                    "HIGH",
                    today,
                    today)
                    ? "COMPLAINT REGISTERED"
                    : "FAILED");

             } catch (Exception e) {
                    e.printStackTrace();
               }
        }
}
