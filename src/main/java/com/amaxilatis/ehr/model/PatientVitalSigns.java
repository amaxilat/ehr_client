package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientVitalSigns {
    private Long patientVitalSignsId = null;
    private String admissionId = "no_value";
    private String patientId = "no_value";
    private String vitalSignsId = "no_value";
    private String retrievalDate = "no_value";
    private String vitalSignValue = "no_value";

    public Long getPatientVitalSignsId() {
        return patientVitalSignsId;
    }

    public void setPatientVitalSignsId(final Long patientVitalSignsId) {
        this.patientVitalSignsId = patientVitalSignsId;
    }

    public String getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(final String admissionId) {
        this.admissionId = admissionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(final String patientId) {
        this.patientId = patientId;
    }

    public String getVitalSignsId() {
        return vitalSignsId;
    }

    public void setVitalSignsId(final String vitalSignsId) {
        this.vitalSignsId = vitalSignsId;
    }

    public String getRetrievalDate() {
        return retrievalDate;
    }

    public void setRetrievalDate(final String retrievalDate) {
        this.retrievalDate = retrievalDate;
    }

    public String getVitalSignValue() {
        return vitalSignValue;
    }

    public void setVitalSignValue(final String vitalSignValue) {
        this.vitalSignValue = vitalSignValue;
    }

    @Override
    public String toString() {
        return "PatientVitalSigns{" +
                "patientVitalSignsId=" + patientVitalSignsId +
                ", admissionId='" + admissionId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", vitalSignsId='" + vitalSignsId + '\'' +
                ", retrievalDate='" + retrievalDate + '\'' +
                ", vitalSignValue='" + vitalSignValue + '\'' +
                '}';
    }
}