package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.PatientMedicalDevices;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * <p>A {@link List} of {@link PatientMedicalDevices}.</p>
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientMedicalDevicesList implements Listable<PatientMedicalDevices> {

    private List<PatientMedicalDevices> patientMedicalDevices;

    public List<PatientMedicalDevices> getPatientMedicalDevices() {
        return patientMedicalDevices;
    }

    public void setPatientMedicalDevices(List<PatientMedicalDevices> patientMedicalDevices) {
        this.patientMedicalDevices = patientMedicalDevices;
    }

    @Override
    public List<PatientMedicalDevices> getList() {
        return patientMedicalDevices;
    }

    @Override
    public String toString() {
        return "PatientMedicalDevicesList{" +
                "patientMedicalDevices=" + patientMedicalDevices +
                '}';
    }
}
