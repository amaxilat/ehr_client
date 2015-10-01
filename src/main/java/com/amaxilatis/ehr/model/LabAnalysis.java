package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LabAnalysis {
    private String labAnalysisId;
    private String codingId;
    private String code;
    private String codeSystem;
    private String unit;

    public LabAnalysis() {
        labAnalysisId = null;
        codingId = "no_value";
        code = null;
        codeSystem = null;
        unit = "no_value";
    }

    public String getLabAnalysisId() {
        return labAnalysisId;
    }

    public void setLabAnalysisId(String labAnalysisId) {
        this.labAnalysisId = labAnalysisId;
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

    public String getCodeSystem() {
        return codeSystem;
    }

    public void setCodeSystem(String codeSystem) {
        this.codeSystem = codeSystem;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "LabAnalysis{" +
                "labAnalysisId=" + labAnalysisId +
                ", codingId='" + codingId + '\'' +
                ", code='" + code + '\'' +
                ", codeSystem='" + codeSystem + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}