package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Medication {
    private String medicationId;
    private String codingId;
    private String coding;
    private String strengthNumerator;
    private String strengthNumeratorUnit;
    private String strengthDenominator;
    private String strengthDenominatorUnit;
    private String brandName;
    private String brand_name;

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

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
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
        this.brand_name = brand_name;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "medicationId='" + medicationId + '\'' +
                ", codingId='" + codingId + '\'' +
                ", coding='" + coding + '\'' +
                ", strengthNumerator='" + strengthNumerator + '\'' +
                ", strengthNumeratorUnit='" + strengthNumeratorUnit + '\'' +
                ", strengthDenominator='" + strengthDenominator + '\'' +
                ", strengthDenominatorUnit='" + strengthDenominatorUnit + '\'' +
                ", brandName='" + brandName + '\'' +
                ", brand_name='" + brand_name + '\'' +
                '}';
    }
}