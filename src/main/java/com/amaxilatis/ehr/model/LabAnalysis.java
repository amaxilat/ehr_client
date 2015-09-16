package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LabAnalysis {
    private int codingId;
    private String unit;

    public int getCodingId() {
        return codingId;
    }

    public void setCodingId(int codingId) {
        this.codingId = codingId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}