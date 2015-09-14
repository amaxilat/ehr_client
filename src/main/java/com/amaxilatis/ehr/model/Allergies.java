package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Allergies {
    private String patientId;
    private int admissionId;
    private int reactionTypeCoding;
    private int clinicalManifestationCoding;
    private int agentCoding;
    private java.util.Date onsetDate;
    private java.util.Date endDate;
    private String severity;

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

    public int getReactionTypeCoding() {
        return reactionTypeCoding;
    }

    public void setReactionTypeCoding(int reactionTypeCoding) {
        this.reactionTypeCoding = reactionTypeCoding;
    }

    public int getClinicalManifestationCoding() {
        return clinicalManifestationCoding;
    }

    public void setClinicalManifestationCoding(int clinicalManifestationCoding) {
        this.clinicalManifestationCoding = clinicalManifestationCoding;
    }

    public int getAgentCoding() {
        return agentCoding;
    }

    public void setAgentCoding(int agentCoding) {
        this.agentCoding = agentCoding;
    }

    public Date getOnsetDate() {
        return onsetDate;
    }

    public void setOnsetDate(Date onsetDate) {
        this.onsetDate = onsetDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}