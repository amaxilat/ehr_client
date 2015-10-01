package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.PatientVitalSigns;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * A list of {@link PatientVitalSigns}.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientVitalSignsList implements Listable<PatientVitalSigns> {
    private List<PatientVitalSigns> patientVitalSigns;

    public List<PatientVitalSigns> getPatientVitalSigns() {
        return patientVitalSigns;
    }

    public void setPatientVitalSigns(final List<PatientVitalSigns> patientVitalSigns) {
        this.patientVitalSigns = patientVitalSigns;
    }

    @Override
    public List<PatientVitalSigns> getList() {
        return patientVitalSigns;
    }

    @Override
    public String toString() {
        return "PatientVitalSignsList{" +
                "patientVitalSigns=" + patientVitalSigns +
                '}';
    }
}
