package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientVaccinations {
    private int admissionId;
    private String patientId;
    private int vaccinationId;
    private double doseLow;
    private double doseHigh;
    private String doseUnit;

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

    public int getVaccinationId() {
        return vaccinationId;
    }

    public void setVaccinationId(int vaccinationId) {
        this.vaccinationId = vaccinationId;
    }

    public double getDoseLow() {
        return doseLow;
    }

    public void setDoseLow(double doseLow) {
        this.doseLow = doseLow;
    }

    public double getDoseHigh() {
        return doseHigh;
    }

    public void setDoseHigh(double doseHigh) {
        this.doseHigh = doseHigh;
    }

    public String getDoseUnit() {
        return doseUnit;
    }

    public void setDoseUnit(String doseUnit) {
        this.doseUnit = doseUnit;
    }
}