package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialHistory {
    /**
     * Gets a completely empty instance of {@link SocialHistory}.
     */
    public static SocialHistory empty() {
        SocialHistory instance = new SocialHistory();
        instance.setSocialHistoryId(null);
        instance.setPatientId(null);
        instance.setSocialCoding(null);
        instance.setValueCoding(null);
        instance.setValue(null);
        instance.setUnit(null);
        instance.setInformation(null);
        instance.setStartDate(null);
        instance.setEndDate(null);
        return instance;
    }

    /**
     * Gets a completely empty instance of {@link SocialHistory} with the given patientId set.
     */
    public static SocialHistory forPatientId(final String patientId) {
        SocialHistory instance = empty();
        instance.setPatientId(patientId);
        return instance;
    }

    private String socialHistoryId = null;
    private String patientId = "no_value";
    private String socialCoding = "no_value";
    private String valueCoding = "no_value";
    private String value = "no_value";
    private String unit = "no_value";
    private String information = "no_value";
    private String startDate = "no_value";
    private String endDate = "no_value";

    public String getSocialHistoryId() {
        return socialHistoryId;
    }

    public void setSocialHistoryId(final String socialHistoryId) {
        this.socialHistoryId = socialHistoryId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(final String patientId) {
        this.patientId = patientId;
    }

    public String getSocialCoding() {
        return socialCoding;
    }

    public void setSocialCoding(final String socialCoding) {
        this.socialCoding = socialCoding;
    }

    public String getValueCoding() {
        return valueCoding;
    }

    public void setValueCoding(final String valueCoding) {
        this.valueCoding = valueCoding;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(final String information) {
        this.information = information;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SocialHistory{" +
                "socialHistoryId=" + socialHistoryId +
                ", patientId='" + patientId + '\'' +
                ", socialCoding='" + socialCoding + '\'' +
                ", valueCoding='" + valueCoding + '\'' +
                ", value='" + value + '\'' +
                ", unit='" + unit + '\'' +
                ", information='" + information + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}