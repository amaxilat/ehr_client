package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurgicalProcedures {
    private int admissionId;
    private String patientId;
    private int surgeryCoding;
    private int priorityCoding;

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

    public int getSurgeryCoding() {
        return surgeryCoding;
    }

    public void setSurgeryCoding(int surgeryCoding) {
        this.surgeryCoding = surgeryCoding;
    }

    public int getPriorityCoding() {
        return priorityCoding;
    }

    public void setPriorityCoding(int priorityCoding) {
        this.priorityCoding = priorityCoding;
    }
}