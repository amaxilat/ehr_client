package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientBloodPressure {
    private String patientBloodPressureId = null;
    private String admissionId = "no_value";
    private String patientId = "no_value";
    private String systolicValue = "no_value";
    private String diastolicValue = "no_value";
    private String retrievalDate = "no_value";

    public String getPatientBloodPressureId() {
        return patientBloodPressureId;
    }

    public void setPatientBloodPressureId(final String patientBloodPressureId) {
        this.patientBloodPressureId = patientBloodPressureId;
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

    public String getSystolicValue() {
        return systolicValue;
    }

    public void setSystolicValue(final String systolicValue) {
        this.systolicValue = systolicValue;
    }

    public String getDiastolicValue() {
        return diastolicValue;
    }

    public void setDiastolicValue(final String diastolicValue) {
        this.diastolicValue = diastolicValue;
    }

    public String getRetrievalDate() {
        return retrievalDate;
    }

    public void setRetrievalDate(final String retrievalDate) {
        this.retrievalDate = retrievalDate;
    }

    @Override
    public String toString() {
        return "PatientBloodPressure{" +
                "patientBloodPressureId=" + patientBloodPressureId +
                ", admissionId='" + admissionId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", systolicValue='" + systolicValue + '\'' +
                ", diastolicValue='" + diastolicValue + '\'' +
                ", retrievalDate='" + retrievalDate + '\'' +
                '}';
    }
}