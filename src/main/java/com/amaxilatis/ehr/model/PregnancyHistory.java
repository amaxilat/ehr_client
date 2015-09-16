package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PregnancyHistory {
    private Integer pregnancyId;
    private String patientId;
    private String expectedDateOfDelivery;
    private String pregnancyInformation;

    public PregnancyHistory() {
        pregnancyId = null;
        patientId = "no_value";
        expectedDateOfDelivery = "no_value";
        pregnancyInformation = "no_value";
    }

    public Integer getPregnancyId() {
        return pregnancyId;
    }

    public void setPregnancyId(Integer pregnancyId) {
        this.pregnancyId = pregnancyId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getExpectedDateOfDelivery() {
        return expectedDateOfDelivery;
    }

    public void setExpectedDateOfDelivery(String expectedDateOfDelivery) {
        this.expectedDateOfDelivery = expectedDateOfDelivery;
    }

    public String getPregnancyInformation() {
        return pregnancyInformation;
    }

    public void setPregnancyInformation(String pregnancyInformation) {
        this.pregnancyInformation = pregnancyInformation;
    }

    @Override
    public String toString() {
        return "PregnancyHistory{" +
                "pregnancyId=" + pregnancyId +
                ", patientId='" + patientId + '\'' +
                ", expectedDateOfDelivery='" + expectedDateOfDelivery + '\'' +
                ", pregnancyInformation='" + pregnancyInformation + '\'' +
                '}';
    }
}