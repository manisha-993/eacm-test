package com.ibm.rdh.chw.entity;

import java.util.Date;

/**
 * Created by Luis on 3/5/18.
 */
public class LifecycleAnnounceData {

    private Date validFrom;
    private Date validTo;

    public LifecycleAnnounceData() {
    }

    public LifecycleAnnounceData(Date validFrom, Date validTo) {
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public String toString() {
        return "LifecycleAnnounceData{" +
                "validFrom=" + validFrom +
                ", validTo=" + validTo +
                '}';
    }
}
