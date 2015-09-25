package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Medication {
    private String medicationId = "no_value";
    private String codingId = "no_value";
    @JsonProperty("coding")
    private String code = null;
    private String strengthNumerator = "no_value";
    private String strengthNumeratorUnit = "no_value";
    private String strengthDenominator = "no_value";
    private String strengthDenominatorUnit = "no_value";
    private String brandName = "no_value";
    private String brand_name = null;

    public String getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    public String getCodingId() {
        return codingId;
    }

    public void setCodingId(String codingId) {
        this.codingId = codingId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStrengthNumerator() {
        return strengthNumerator;
    }

    public void setStrengthNumerator(String strengthNumerator) {
        this.strengthNumerator = strengthNumerator;
    }

    public String getStrengthNumeratorUnit() {
        return strengthNumeratorUnit;
    }

    public void setStrengthNumeratorUnit(String strengthNumeratorUnit) {
        this.strengthNumeratorUnit = strengthNumeratorUnit;
    }

    public String getStrengthDenominator() {
        return strengthDenominator;
    }

    public void setStrengthDenominator(String strengthDenominator) {
        this.strengthDenominator = strengthDenominator;
    }

    public String getStrengthDenominatorUnit() {
        return strengthDenominatorUnit;
    }

    public void setStrengthDenominatorUnit(String strengthDenominatorUnit) {
        this.strengthDenominatorUnit = strengthDenominatorUnit;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        if (brand_name != null) {
            this.brandName = brand_name;
        }
        this.brand_name = brand_name;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "medicationId='" + medicationId + '\'' +
                ", codingId='" + codingId + '\'' +
                ", code='" + code + '\'' +
                ", strengthNumerator='" + strengthNumerator + '\'' +
                ", strengthNumeratorUnit='" + strengthNumeratorUnit + '\'' +
                ", strengthDenominator='" + strengthDenominator + '\'' +
                ", strengthDenominatorUnit='" + strengthDenominatorUnit + '\'' +
                ", brandName='" + brandName + '\'' +
                ", brand_name='" + brand_name + '\'' +
                '}';
    }
}