package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vaccinations {
    private Long vaccinationId = null;
    private String codingId = "no_value";
    private String brandName = "no_value";

    public Long getVaccinationId() {
        return vaccinationId;
    }

    public void setVaccinationId(final Long vaccinationId) {
        this.vaccinationId = vaccinationId;
    }

    public String getCodingId() {
        return codingId;
    }

    public void setCodingId(final String codingId) {
        this.codingId = codingId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(final String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "Vaccinations{" +
                "vaccinationId=" + vaccinationId +
                ", codingId='" + codingId + '\'' +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}