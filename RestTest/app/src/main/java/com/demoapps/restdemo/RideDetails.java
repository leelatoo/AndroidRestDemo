/*
 * Copyright (c) 2019. Leeladevi Balasundaram, leelabalasundaram@gmail.com
 */

package com.demoapps.restdemo;

import java.io.Serializable;
import java.util.List;

public class RideDetails implements Serializable
{
    List<String> Ride;
    int EstimatedTime;

    public List<String> getRide() {
        return Ride;
    }

    public void setRide(List<String> ride) {
        this.Ride = ride;
    }

    public int getEstimatedTime() {
        return EstimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.EstimatedTime = estimatedTime;
    }
}
