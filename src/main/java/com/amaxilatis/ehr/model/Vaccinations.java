package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vaccinations {
    private int codingId;
    private String brandName;

    public int getCodingId() {
        return codingId;
    }

    public void setCodingId(int codingId) {
        this.codingId = codingId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}