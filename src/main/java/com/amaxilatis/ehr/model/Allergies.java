package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Model for Allergies.
 *
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Allergies {
    private String allergyId;
    private String patientId;
    private String patientData;
    private String admissionId;
    private String admissionData;
    private String agentCoding;
    private String reactionTypeCoding;
    private String typeCoding;
    private String manifestationCoding;
    private String allergyDescription;
    private String onsetDate;
    private String endDate;
    private String severity;

    public Allergies() {
        allergyId = null;
        patientId = "no_value";
        patientData = null;
        admissionId = "no_value";
        admissionData = null;
        agentCoding = "no_value";
        reactionTypeCoding = "no_value";
        typeCoding = null;
        manifestationCoding = "no_value";
        allergyDescription = "no_value";
        onsetDate = "no_value";
        endDate = "no_value";
        severity = "no_value";
    }

    public String getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(String allergyId) {
        this.allergyId = allergyId;
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

    public String getAgentCoding() {
        return agentCoding;
    }

    public void setAgentCoding(String agentCoding) {
        this.agentCoding = agentCoding;
    }

    public String getReactionTypeCoding() {
        return reactionTypeCoding;
    }

    public void setReactionTypeCoding(String reactionTypeCoding) {
        this.reactionTypeCoding = reactionTypeCoding;
    }

    public String getTypeCoding() {
        return typeCoding;
    }

    public void setTypeCoding(String typeCoding) {
        if (typeCoding != null) {
            this.reactionTypeCoding = typeCoding;
        }
        this.typeCoding = typeCoding;
    }

    public String getManifestationCoding() {
        return manifestationCoding;
    }

    public void setManifestationCoding(String manifestationCoding) {
        this.manifestationCoding = manifestationCoding;
    }

    public String getAllergyDescription() {
        return allergyDescription;
    }

    public void setAllergyDescription(String allergyDescription) {
        this.allergyDescription = allergyDescription;
    }

    public String getOnsetDate() {
        return onsetDate;
    }

    public void setOnsetDate(String onsetDate) {
        this.onsetDate = onsetDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "Allergies{" +
                "allergyId=" + allergyId +
                ", patientId='" + patientId + '\'' +
                ", patientData='" + patientData + '\'' +
                ", admissionId='" + admissionId + '\'' +
                ", admissionData='" + admissionData + '\'' +
                ", agentCoding='" + agentCoding + '\'' +
                ", reactionTypeCoding='" + reactionTypeCoding + '\'' +
                ", typeCoding='" + typeCoding + '\'' +
                ", manifestationCoding='" + manifestationCoding + '\'' +
                ", allergyDescription='" + allergyDescription + '\'' +
                ", onsetDate='" + onsetDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", severity='" + severity + '\'' +
                '}';
    }
}