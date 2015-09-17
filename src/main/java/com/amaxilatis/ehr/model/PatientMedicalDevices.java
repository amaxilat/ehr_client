package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientMedicalDevices {
    private String patientMedicalDeviceSn;
    private String admissionId;
    private String patientId;
    private String deviceDescription;
    private String deviceCodeSystem;
    private String medicalDevicesId;
    private String date;

    public PatientMedicalDevices() {
        patientMedicalDeviceSn = "no_value";
        admissionId = "no_value";
        patientId = "no_value";
        deviceDescription = "no_value";
        deviceCodeSystem = null;
        medicalDevicesId = "no_value";
        date = "no_value";
    }

    public String getPatientMedicalDeviceSn() {
        return patientMedicalDeviceSn;
    }

    public void setPatientMedicalDeviceSn(String patientMedicalDeviceSn) {
        this.patientMedicalDeviceSn = patientMedicalDeviceSn;
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

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public String getDeviceCodeSystem() {
        return deviceCodeSystem;
    }

    public void setDeviceCodeSystem(String deviceCodeSystem) {
        this.deviceCodeSystem = deviceCodeSystem;
    }

    public String getMedicalDevicesId() {
        return medicalDevicesId;
    }

    public void setMedicalDevicesId(String medicalDevicesId) {
        this.medicalDevicesId = medicalDevicesId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PatientMedicalDevices{" +
                "patientMedicalDeviceSn='" + patientMedicalDeviceSn + '\'' +
                ", admissionId='" + admissionId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", deviceDescription='" + deviceDescription + '\'' +
                ", deviceCodeSystem='" + deviceCodeSystem + '\'' +
                ", medicalDevicesId='" + medicalDevicesId + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}