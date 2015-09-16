package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PregnancyHistory {
    private String patientId;
    private java.util.Date expectedDateOfDelivery;
    private int codingId;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Date getExpectedDateOfDelivery() {
        return expectedDateOfDelivery;
    }

    public void setExpectedDateOfDelivery(Date expectedDateOfDelivery) {
        this.expectedDateOfDelivery = expectedDateOfDelivery;
    }

    public int getCodingId() {
        return codingId;
    }

    public void setCodingId(int codingId) {
        this.codingId = codingId;
    }
}