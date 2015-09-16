package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientLabAnalysis {
    private String patientId;
    private int admissionId;
    private int labAnalysisId;
    private double labAnalysisValue;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(int admissionId) {
        this.admissionId = admissionId;
    }

    public int getLabAnalysisId() {
        return labAnalysisId;
    }

    public void setLabAnalysisId(int labAnalysisId) {
        this.labAnalysisId = labAnalysisId;
    }

    public double getLabAnalysisValue() {
        return labAnalysisValue;
    }

    public void setLabAnalysisValue(double labAnalysisValue) {
        this.labAnalysisValue = labAnalysisValue;
    }
}