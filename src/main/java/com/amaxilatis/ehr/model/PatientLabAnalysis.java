package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientLabAnalysis {
    private String patientLabAnalysisId = null;
    private String patientId = "no_value";
    private String admissionId = "no_value";
    private String labAnalysisDate = "no_value";
    private String labAnalysisId = "no_value";
    private String labAnalysisValue = "0";
    private String performed = "false";

    public String getPatientLabAnalysisId() {
        return patientLabAnalysisId;
    }

    public void setPatientLabAnalysisId(final String patientLabAnalysisId) {
        this.patientLabAnalysisId = patientLabAnalysisId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(final String patientId) {
        this.patientId = patientId;
    }

    public String getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(final String admissionId) {
        this.admissionId = admissionId;
    }

    public String getLabAnalysisDate() {
        return labAnalysisDate;
    }

    public void setLabAnalysisDate(final String labAnalysisDate) {
        this.labAnalysisDate = labAnalysisDate;
    }

    public String getLabAnalysisId() {
        return labAnalysisId;
    }

    public void setLabAnalysisId(final String labAnalysisId) {
        this.labAnalysisId = labAnalysisId;
    }

    public String getLabAnalysisValue() {
        return labAnalysisValue;
    }

    public void setLabAnalysisValue(final String labAnalysisValue) {
        this.labAnalysisValue = labAnalysisValue;
    }

    public String getPerformed() {
        return performed;
    }

    public void setPerformed(final String performed) {
        this.performed = performed;
    }

    @Override
    public String toString() {
        return "PatientLabAnalysis{" +
                "patientLabAnalysisId=" + patientLabAnalysisId +
                ", patientId='" + patientId + '\'' +
                ", admissionId='" + admissionId + '\'' +
                ", labAnalysisDate='" + labAnalysisDate + '\'' +
                ", labAnalysisId='" + labAnalysisId + '\'' +
                ", labAnalysisValue='" + labAnalysisValue + '\'' +
                ", performed='" + performed + '\'' +
                '}';
    }
}