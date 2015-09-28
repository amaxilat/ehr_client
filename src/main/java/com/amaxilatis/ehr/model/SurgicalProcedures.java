package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurgicalProcedures {
    private Long surgicalProcedureId = null;
    private String admissionId = "no_value";
    private String patientId = "no_value";
    private String procedureDescription = "no_value";
    private String priorityCoding = "no_value";
    private String surgeryCoding = "no_value";
    private String procedureDate = "no_value";

    public Long getSurgicalProcedureId() {
        return surgicalProcedureId;
    }

    public void setSurgicalProcedureId(final Long surgicalProcedureId) {
        this.surgicalProcedureId = surgicalProcedureId;
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

    public String getProcedureDescription() {
        return procedureDescription;
    }

    public void setProcedureDescription(final String procedureDescription) {
        this.procedureDescription = procedureDescription;
    }

    public String getPriorityCoding() {
        return priorityCoding;
    }

    public void setPriorityCoding(final String priorityCoding) {
        this.priorityCoding = priorityCoding;
    }

    public String getSurgeryCoding() {
        return surgeryCoding;
    }

    public void setSurgeryCoding(final String surgeryCoding) {
        this.surgeryCoding = surgeryCoding;
    }

    public String getProcedureDate() {
        return procedureDate;
    }

    public void setProcedureDate(final String procedureDate) {
        this.procedureDate = procedureDate;
    }

    @Override
    public String toString() {
        return "SurgicalProcedures{" +
                "surgicalProcedureId=" + surgicalProcedureId +
                ", admissionId='" + admissionId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", procedureDescription='" + procedureDescription + '\'' +
                ", priorityCoding='" + priorityCoding + '\'' +
                ", surgeryCoding='" + surgeryCoding + '\'' +
                ", procedureDate='" + procedureDate + '\'' +
                '}';
    }
}