package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VitalSigns {
    private Long vitalSignsId = null;
    private String codingId = "no_value";
    private String unit = "no_value";

    public Long getVitalSignsId() {
        return vitalSignsId;
    }

    public void setVitalSignsId(final Long vitalSignsId) {
        this.vitalSignsId = vitalSignsId;
    }

    public String getCodingId() {
        return codingId;
    }

    public void setCodingId(final String codingId) {
        this.codingId = codingId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "VitalSigns{" +
                "vitalSignsId=" + vitalSignsId +
                ", codingId='" + codingId + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}