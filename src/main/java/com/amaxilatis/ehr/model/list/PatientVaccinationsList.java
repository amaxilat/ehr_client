package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.PatientVaccinations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * A list of {@link PatientVaccinations}.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientVaccinationsList implements Listable<PatientVaccinations> {
    private List<PatientVaccinations> patientVaccinations;

    public List<PatientVaccinations> getPatientVaccinations() {
        return patientVaccinations;
    }

    public void setPatientVaccinations(final List<PatientVaccinations> patientVaccinations) {
        this.patientVaccinations = patientVaccinations;
    }

    @Override
    public List<PatientVaccinations> getList() {
        return patientVaccinations;
    }

    @Override
    public String toString() {
        return "PatientVaccinationsList{" +
                "patientVaccinations=" + patientVaccinations +
                '}';
    }
}
