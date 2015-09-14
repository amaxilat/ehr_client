package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.AdmissionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdmissionTypeList {
    List<AdmissionType> admissionType;

    public List<AdmissionType> getAdmissionType() {
        return admissionType;
    }

    public void setAdmissionType(List<AdmissionType> admissionType) {
        this.admissionType = admissionType;
    }

    @Override
    public String toString() {
        return "AdmissionTypeList{" +
                "admissionType=" + admissionType +
                '}';
    }
}
