package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FunctionalStatus {
    public static FunctionalStatus empty() {
        FunctionalStatus functionalStatus = new FunctionalStatus();
        functionalStatus.setFunctionalStatusId(null);
        functionalStatus.setPatientId(null);
        functionalStatus.setStatus(null);
        functionalStatus.setDescription(null);
        functionalStatus.setCodingId(null);
        functionalStatus.setCode(null);
        functionalStatus.setCodeSystem(null);
        return functionalStatus;
    }

    public static FunctionalStatus forPatientId(final String patientId) {
        FunctionalStatus functionalStatus = empty();
        functionalStatus.setPatientId(patientId);
        return functionalStatus;
    }

    private String functionalStatusId = null;
    private String patientId = "no_value";
    private String status = "no_value";
    private String description = "no_value";
    private String codingId = "no_value";
    private String code = null;
    private String codeSystem = null;

    public String getFunctionalStatusId() {
        return functionalStatusId;
    }

    public void setFunctionalStatusId(final String functionalStatusId) {
        this.functionalStatusId = functionalStatusId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(final String patientId) {
        this.patientId = patientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCodingId() {
        return codingId;
    }

    public void setCodingId(final String codingId) {
        this.codingId = codingId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getCodeSystem() {
        return codeSystem;
    }

    public void setCodeSystem(final String codeSystem) {
        this.codeSystem = codeSystem;
    }

    @Override
    public String toString() {
        return "FunctionalStatus{" +
                "functionalStatusId=" + functionalStatusId +
                ", patientId='" + patientId + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", codingId='" + codingId + '\'' +
                ", code='" + code + '\'' +
                ", codeSystem='" + codeSystem + '\'' +
                '}';
    }
}