package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.PregnancyHistory;

import java.util.List;

/**
 * <p>A {@link java.util.List} of {@link PregnancyHistory}.</p>
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
public class PregnancyHistoryList {

    private List<PregnancyHistory> patientData;

    public List<PregnancyHistory> getPatientData() {
        return patientData;
    }

    public void setPatientData(List<PregnancyHistory> patientData) {
        this.patientData = patientData;
    }

    @Override
    public String toString() {
        return "PregnancyHistoryList{" +
                "patientData=" + patientData +
                '}';
    }
}
