package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalDevices {
    private Integer medicalDevicesId;
    private String codingId;
    private String brandName;
    private String code;
    private String codeSystem;
    private String description;

    /**
     * <p>Default no argument constructor. Initializes the fields required
     * to add a new {@link MedicalDevices} to EHR to non null values.</p>
     */
    public MedicalDevices() {
        medicalDevicesId = null;
        codingId = "no_value";
        brandName = "no_value";
        code = null;
        codeSystem = null;
        description = "no_value";
    }

    public Integer getMedicalDevicesId() {
        return medicalDevicesId;
    }

    public void setMedicalDevicesId(Integer medicalDevicesId) {
        this.medicalDevicesId = medicalDevicesId;
    }

    public String getCodingId() {
        return codingId;
    }

    public void setCodingId(String codingId) {
        this.codingId = codingId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MedicalDevices{" +
                "medicalDevicesId=" + medicalDevicesId +
                ", codingId='" + codingId + '\'' +
                ", brandName='" + brandName + '\'' +
                ", code='" + code + '\'' +
                ", codeSystem='" + codeSystem + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}