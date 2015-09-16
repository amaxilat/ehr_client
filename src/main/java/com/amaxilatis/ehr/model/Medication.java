package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Medication {
    private int codingId;
    private double strengthNumerator;
    private String strengthNumeratorUnit;
    private double strengthDenominator;
    private String strengthDenominatorUnit;
    private String brandName;

    public int getCodingId() {
        return codingId;
    }

    public void setCodingId(int codingId) {
        this.codingId = codingId;
    }

    public double getStrengthNumerator() {
        return strengthNumerator;
    }

    public void setStrengthNumerator(double strengthNumerator) {
        this.strengthNumerator = strengthNumerator;
    }

    public String getStrengthNumeratorUnit() {
        return strengthNumeratorUnit;
    }

    public void setStrengthNumeratorUnit(String strengthNumeratorUnit) {
        this.strengthNumeratorUnit = strengthNumeratorUnit;
    }

    public double getStrengthDenominator() {
        return strengthDenominator;
    }

    public void setStrengthDenominator(double strengthDenominator) {
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
}