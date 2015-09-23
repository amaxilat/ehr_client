package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDataList implements Listable<Patient> {
    List<Patient> patientData;

    public List<Patient> getPatientData() {
        return patientData;
    }

    public void setPatientData(List<Patient> patientData) {
        this.patientData = patientData;
    }

    @Override
    public List<Patient> getList() {
        return patientData;
    }

    @Override
    public String toString() {
        return "PatientDataList{" +
                "patientData=" + patientData +
                '}';
    }
}
