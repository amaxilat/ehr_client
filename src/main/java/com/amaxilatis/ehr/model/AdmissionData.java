package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdmissionData {

    /**
     * Class used to parse the id of a newly created {@link AdmissionData}.
     *
     * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AdmissionDataId {
        private String admissionId;

        public String getAdmissionId() {
            return admissionId;
        }

        public void setAdmissionId(final String admissionId) {
            this.admissionId = admissionId;
        }
    }

    private String admissionId;
    private String patientId;
    private String admissionTypeId;
    private String description;
    private String typeOfAdmissionDescription;
    private String createdTime;

    public String getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(String admissionId) {
        this.admissionId = admissionId;
    }

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
                "admissionId=" + admissionId +
                ", patientId='" + patientId + '\'' +
                ", admissionTypeId='" + admissionTypeId + '\'' +
                ", description='" + description + '\'' +
                ", typeOfAdmissionDescription='" + typeOfAdmissionDescription + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}