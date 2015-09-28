package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientVaccinations {
    private Long patientVaccinationId = null;
    private String admissionId = "no_value";
    private String patientId = "no_value";
    private String vaccinationId = "no_value";
    private String vaccinationDate = "no_value";
    private String doseLow = "no_value";
    private String doseHigh = "no_value";
    private String doseUnit = "no_value";

    public Long getPatientVaccinationId() {
        return patientVaccinationId;
    }

    public void setPatientVaccinationId(final Long patientVaccinationId) {
        this.patientVaccinationId = patientVaccinationId;
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

    public String getVaccinationId() {
        return vaccinationId;
    }

    public void setVaccinationId(final String vaccinationId) {
        this.vaccinationId = vaccinationId;
    }

    public String getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(final String vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public String getDoseLow() {
        return doseLow;
    }

    public void setDoseLow(final String doseLow) {
        this.doseLow = doseLow;
    }

    public String getDoseHigh() {
        return doseHigh;
    }

    public void setDoseHigh(final String doseHigh) {
        this.doseHigh = doseHigh;
    }

    public String getDoseUnit() {
        return doseUnit;
    }

    public void setDoseUnit(final String doseUnit) {
        this.doseUnit = doseUnit;
    }

    @Override
    public String toString() {
        return "PatientVaccinations{" +
                "patientVaccinationId=" + patientVaccinationId +
                ", admissionId='" + admissionId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", vaccinationId='" + vaccinationId + '\'' +
                ", vaccinationDate='" + vaccinationDate + '\'' +
                ", doseLow='" + doseLow + '\'' +
                ", doseHigh='" + doseHigh + '\'' +
                ", doseUnit='" + doseUnit + '\'' +
                '}';
    }
}