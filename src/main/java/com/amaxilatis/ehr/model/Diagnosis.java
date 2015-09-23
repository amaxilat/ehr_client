package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Diagnosis {
    private Long diagnosisId;
    private String admissionId;
    private String admissionData;
    private String patientId;
    private String patientData;
    private String description;
    private String codingId;
    private String onsetDate;
    private String illnessHistory;
    private String resolved;
    @JsonProperty("treatmentRecomendation")
    private String treatmentRecommendation;

    public Diagnosis() {
        diagnosisId = null;
        admissionId = "no_value";
        admissionData = null;
        patientId = "no_value";
        patientData = null;
        description = "no_value";
        codingId = "no_value";
        onsetDate = "no_value";
        illnessHistory = "no_value";
        resolved = "no_value";
        treatmentRecommendation = "no_value";
    }

    public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(String admissionId) {
        this.admissionId = admissionId;
    }

    public String getAdmissionData() {
        return admissionData;
    }

    public void setAdmissionData(String admissionData) {
        if (admissionData != null) {
            this.admissionId = admissionData;
        }
        this.admissionData = admissionData;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientData() {
        return patientData;
    }

    public void setPatientData(String patientData) {
        if (patientData != null) {
            this.patientId = patientData;
        }
        this.patientData = patientData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodingId() {
        return codingId;
    }

    public void setCodingId(String codingId) {
        this.codingId = codingId;
    }

    public String getOnsetDate() {
        return onsetDate;
    }

    public void setOnsetDate(String onsetDate) {
        this.onsetDate = onsetDate;
    }

    public String getIllnessHistory() {
        return illnessHistory;
    }

    public void setIllnessHistory(String illnessHistory) {
        this.illnessHistory = illnessHistory;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getTreatmentRecommendation() {
        return treatmentRecommendation;
    }

    public void setTreatmentRecommendation(String treatmentRecommendation) {
        this.treatmentRecommendation = treatmentRecommendation;
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "diagnosisId=" + diagnosisId +
                ", admissionId='" + admissionId + '\'' +
                ", admissionData='" + admissionData + '\'' +
                ", patientId='" + patientId + '\'' +
                ", patientData='" + patientData + '\'' +
                ", description='" + description + '\'' +
                ", codingId='" + codingId + '\'' +
                ", onsetDate='" + onsetDate + '\'' +
                ", illnessHistory='" + illnessHistory + '\'' +
                ", resolved='" + resolved + '\'' +
                ", treatmentRecommendation='" + treatmentRecommendation + '\'' +
                '}';
    }
}