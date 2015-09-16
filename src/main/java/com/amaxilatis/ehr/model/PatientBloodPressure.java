package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientBloodPressure {
    private double systolicValue;
    private double diastolicValue;
    private int admissionId;
    private String patientId;

    public double getSystolicValue() {
        return systolicValue;
    }

    public void setSystolicValue(double systolicValue) {
        this.systolicValue = systolicValue;
    }

    public double getDiastolicValue() {
        return diastolicValue;
    }

    public void setDiastolicValue(double diastolicValue) {
        this.diastolicValue = diastolicValue;
    }

    public int getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(int admissionId) {
        this.admissionId = admissionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}