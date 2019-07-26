/*
 * Copyright (c) 2019. Leeladevi Balasundaram, leelabalasundaram@gmail.com
 */

package com.demoapps.restdemo;

public class CustomerDetails
{
    String customer_id;
    String ride_preferences;
    String starting_point;

    public String getcustomer_id() {
        return customer_id;
    }
    public void setcustomer_id(String cId) {
        this.customer_id = cId;
    }

    public String getride_preferences() {
        return ride_preferences;
    }
    public void setride_preferences(String ridePref) {
        this.ride_preferences = ridePref;
    }

    public String getstarting_point() {
        return starting_point;
    }
    public void setstarting_point(String startingPt) {
        this.starting_point = startingPt;
    }
}
