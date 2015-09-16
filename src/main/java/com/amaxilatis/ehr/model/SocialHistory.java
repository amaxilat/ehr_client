package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialHistory {
    private String patientId;
    private int socialCoding;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private double value;
    private String unit;
    private int valueCoding;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getSocialCoding() {
        return socialCoding;
    }

    public void setSocialCoding(int socialCoding) {
        this.socialCoding = socialCoding;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getValueCoding() {
        return valueCoding;
    }

    public void setValueCoding(int valueCoding) {
        this.valueCoding = valueCoding;
    }
}