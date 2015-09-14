package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdmissionData {
    private String patientId;
    private String admissionTypeId;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String typeOfAdmissionDescription;
    private String createdTime;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(final String patientId) {
        this.patientId = patientId;
    }

    public String getAdmissionTypeId() {
        return admissionTypeId;
    }

    public void setAdmissionTypeId(final String admissionTypeId) {
        this.admissionTypeId = admissionTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getTypeOfAdmissionDescription() {
        return typeOfAdmissionDescription;
    }

    public void setTypeOfAdmissionDescription(String typeOfAdmissionDescription) {
        this.typeOfAdmissionDescription = typeOfAdmissionDescription;
    }

    @Override
    public String toString() {
        return "AdmissionData{" +
                "patientId='" + patientId + '\'' +
                ", admissionTypeId=" + admissionTypeId +
                ", description='" + description + '\'' +
                ", typeOfAdmissionDescription='" + typeOfAdmissionDescription + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}