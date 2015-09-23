package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Model for {@link Medication} of a {@link Patient} during an {@link AdmissionData}.
 *
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientMedication {

    private Long patientMedicationId = null;
    private String admissionId = "no_value";
    private String patientId = "no_value";
    private String prescribedMedicationId = "no_value";
    private String dispensedMedicationId = "no_value";
    private String doseFormCoding = "no_value";
    private String doseQuantityLow = "no_value";
    private String doseQuantityHigh = "no_value";
    private String doseQuantityUnit = "no_value";
    private String frequencyOfIntakes = "no_value";
    private String frequencyOfIntakesUnit = "no_value";
    private String durationOfTreatment = "no_value";
    private String dateOfOnsetTreatment = "no_value";

    public Long getPatientMedicationId() {
        return patientMedicationId;
    }

    public void setPatientMedicationId(Long patientMedicationId) {
        this.patientMedicationId = patientMedicationId;
    }

    public String getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(String admissionId) {
        this.admissionId = admissionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPrescribedMedicationId() {
        return prescribedMedicationId;
    }

    public void setPrescribedMedicationId(String prescribedMedicationId) {
        this.prescribedMedicationId = prescribedMedicationId;
    }

    public String getDispensedMedicationId() {
        return dispensedMedicationId;
    }

    public void setDispensedMedicationId(String dispensedMedicationId) {
        this.dispensedMedicationId = dispensedMedicationId;
    }

    public String getDoseFormCoding() {
        return doseFormCoding;
    }

    public void setDoseFormCoding(String doseFormCoding) {
        this.doseFormCoding = doseFormCoding;
    }

    public String getDoseQuantityLow() {
        return doseQuantityLow;
    }

    public void setDoseQuantityLow(String doseQuantityLow) {
        this.doseQuantityLow = doseQuantityLow;
    }

    public String getDoseQuantityHigh() {
        return doseQuantityHigh;
    }

    public void setDoseQuantityHigh(String doseQuantityHigh) {
        this.doseQuantityHigh = doseQuantityHigh;
    }

    public String getDoseQuantityUnit() {
        return doseQuantityUnit;
    }

    public void setDoseQuantityUnit(String doseQuantityUnit) {
        this.doseQuantityUnit = doseQuantityUnit;
    }

    public String getFrequencyOfIntakes() {
        return frequencyOfIntakes;
    }

    public void setFrequencyOfIntakes(String frequencyOfIntakes) {
        this.frequencyOfIntakes = frequencyOfIntakes;
    }

    public String getFrequencyOfIntakesUnit() {
        return frequencyOfIntakesUnit;
    }

    public void setFrequencyOfIntakesUnit(String frequencyOfIntakesUnit) {
        this.frequencyOfIntakesUnit = frequencyOfIntakesUnit;
    }

    public String getDurationOfTreatment() {
        return durationOfTreatment;
    }

    public void setDurationOfTreatment(String durationOfTreatment) {
        this.durationOfTreatment = durationOfTreatment;
    }

    public String getDateOfOnsetTreatment() {
        return dateOfOnsetTreatment;
    }

    public void setDateOfOnsetTreatment(String dateOfOnsetTreatment) {
        this.dateOfOnsetTreatment = dateOfOnsetTreatment;
    }

    @Override
    public String toString() {
        return "PatientMedication{" +
                "patientMedicationId=" + patientMedicationId +
                ", admissionId='" + admissionId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", prescribedMedicationId='" + prescribedMedicationId + '\'' +
                ", dispensedMedicationId='" + dispensedMedicationId + '\'' +
                ", doseFormCoding='" + doseFormCoding + '\'' +
                ", doseQuantityLow='" + doseQuantityLow + '\'' +
                ", doseQuantityHigh='" + doseQuantityHigh + '\'' +
                ", doseQuantityUnit='" + doseQuantityUnit + '\'' +
                ", frequencyOfIntakes='" + frequencyOfIntakes + '\'' +
                ", frequencyOfIntakesUnit='" + frequencyOfIntakesUnit + '\'' +
                ", durationOfTreatment='" + durationOfTreatment + '\'' +
                ", dateOfOnsetTreatment='" + dateOfOnsetTreatment + '\'' +
                '}';
    }
}