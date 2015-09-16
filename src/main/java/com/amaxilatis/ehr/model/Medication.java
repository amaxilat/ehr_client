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
    private Integer medicationId;
    private int codingId;
    private double strengthNumerator;
    private String strengthNumeratorUnit;
    private double strengthDenominator;
    private String strengthDenominatorUnit;
    private String brandName;

    public Integer getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Integer medicationId) {
        this.medicationId = medicationId;
    }

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

    @Override
    public String toString() {
        return "Medication{" +
                "medicationId=" + medicationId +
                ", codingId=" + codingId +
                ", strengthNumerator=" + strengthNumerator +
                ", strengthNumeratorUnit='" + strengthNumeratorUnit + '\'' +
                ", strengthDenominator=" + strengthDenominator +
                ", strengthDenominatorUnit='" + strengthDenominatorUnit + '\'' +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}