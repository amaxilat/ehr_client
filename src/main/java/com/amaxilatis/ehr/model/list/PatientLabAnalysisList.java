package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.PatientLabAnalysis;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * A list of {@link PatientLabAnalysis}.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientLabAnalysisList implements Listable<PatientLabAnalysis> {

    private List<PatientLabAnalysis> patientLabAnalysis;

    public List<PatientLabAnalysis> getPatientLabAnalysis() {
        return patientLabAnalysis;
    }

    public void setPatientLabAnalysis(final List<PatientLabAnalysis> patientLabAnalysis) {
        this.patientLabAnalysis = patientLabAnalysis;
    }

    @Override
    public List<PatientLabAnalysis> getList() {
        return patientLabAnalysis;
    }

    @Override
    public String toString() {
        return "PatientLabAnalysisList{" +
                "patientLabAnalysis=" + patientLabAnalysis +
                '}';
    }
}
