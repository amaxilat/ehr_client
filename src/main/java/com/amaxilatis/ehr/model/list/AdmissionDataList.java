package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.AdmissionData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdmissionDataList {
    List<AdmissionData> admissionData;

    public List<AdmissionData> getAdmissionData() {
        return admissionData;
    }

    public void setAdmissionData(List<AdmissionData> admissionData) {
        this.admissionData = admissionData;
    }

    @Override
    public String toString() {
        return "AdmissionDataList{" +
                "admissionData=" + admissionData +
                '}';
    }
}
