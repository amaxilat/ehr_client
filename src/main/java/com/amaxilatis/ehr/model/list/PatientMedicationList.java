package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.PatientMedication;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * A {@link java.util.List} of {@link com.amaxilatis.ehr.model.PatientMedication}.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientMedicationList implements Listable<PatientMedication> {

    private List<PatientMedication> patientMedication;

    public List<PatientMedication> getPatientMedication() {
        return patientMedication;
    }

    public void setPatientMedication(List<PatientMedication> patientMedication) {
        this.patientMedication = patientMedication;
    }

    @Override
    public List<PatientMedication> getList() {
        return patientMedication;
    }

    @Override
    public String toString() {
        return "PatientMedicationList{" +
                "patientMedication=" + patientMedication +
                '}';
    }
}
