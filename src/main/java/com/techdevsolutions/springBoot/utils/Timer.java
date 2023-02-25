package com.techdevsolutions.springBoot.utils;

import java.util.Date;

public class Timer {
    private Date startTime;
    private Date stopTime;
    private long diff;

    public Timer start() {
        this.startTime = new Date();
        return this;
    }

    public Timer stop() {
        this.stopTime = new Date();
        this.diff = this.stopTime.getTime() - this.startTime.getTime();
        return this;
    }

    public long getDiff() {
        return this.diff;
    }

    public long stopAndGetDiff() {
        this.stopTime = new Date();
        return this.stopTime.getTime() - this.startTime.getTime();
    }
}
