package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientMedication {
    private int admissionId;
    private String patientId;
    private int prescribedMedicationId;
    private int dispensedMedicationId;
    private int doseFormCoding;
    private double doseQuantityLow;
    private double doseQuantityHigh;
    private String doseQuantityUnit;
    private int frequencyOfIntakes;
    private String frequencyOfIntakesUnit;
    private int durationOfTreatment;
    private java.util.Date dateOfOnsetTreatment;

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

    public int getPrescribedMedicationId() {
        return prescribedMedicationId;
    }

    public void setPrescribedMedicationId(int prescribedMedicationId) {
        this.prescribedMedicationId = prescribedMedicationId;
    }

    public int getDispensedMedicationId() {
        return dispensedMedicationId;
    }

    public void setDispensedMedicationId(int dispensedMedicationId) {
        this.dispensedMedicationId = dispensedMedicationId;
    }

    public int getDoseFormCoding() {
        return doseFormCoding;
    }

    public void setDoseFormCoding(int doseFormCoding) {
        this.doseFormCoding = doseFormCoding;
    }

    public double getDoseQuantityLow() {
        return doseQuantityLow;
    }

    public void setDoseQuantityLow(double doseQuantityLow) {
        this.doseQuantityLow = doseQuantityLow;
    }

    public double getDoseQuantityHigh() {
        return doseQuantityHigh;
    }

    public void setDoseQuantityHigh(double doseQuantityHigh) {
        this.doseQuantityHigh = doseQuantityHigh;
    }

    public String getDoseQuantityUnit() {
        return doseQuantityUnit;
    }

    public void setDoseQuantityUnit(String doseQuantityUnit) {
        this.doseQuantityUnit = doseQuantityUnit;
    }

    public int getFrequencyOfIntakes() {
        return frequencyOfIntakes;
    }

    public void setFrequencyOfIntakes(int frequencyOfIntakes) {
        this.frequencyOfIntakes = frequencyOfIntakes;
    }

    public String getFrequencyOfIntakesUnit() {
        return frequencyOfIntakesUnit;
    }

    public void setFrequencyOfIntakesUnit(String frequencyOfIntakesUnit) {
        this.frequencyOfIntakesUnit = frequencyOfIntakesUnit;
    }

    public int getDurationOfTreatment() {
        return durationOfTreatment;
    }

    public void setDurationOfTreatment(int durationOfTreatment) {
        this.durationOfTreatment = durationOfTreatment;
    }

    public Date getDateOfOnsetTreatment() {
        return dateOfOnsetTreatment;
    }

    public void setDateOfOnsetTreatment(Date dateOfOnsetTreatment) {
        this.dateOfOnsetTreatment = dateOfOnsetTreatment;
    }
}