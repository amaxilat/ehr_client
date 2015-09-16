package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientMedicalDevices {
    private int admissionId;
    private String patientId;
    private int medicalDeviceId;
    private java.util.Date date;

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

    public int getMedicalDeviceId() {
        return medicalDeviceId;
    }

    public void setMedicalDeviceId(int medicalDeviceId) {
        this.medicalDeviceId = medicalDeviceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}