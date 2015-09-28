package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.PatientBloodPressure;

import java.util.List;

/**
 * A list of {@link PatientBloodPressure}.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
public class PatientBloodPressureList implements Listable<PatientBloodPressure> {
    private List<PatientBloodPressure> pBloodPressure;

    public List<PatientBloodPressure> getpBloodPressure() {
        return pBloodPressure;
    }

    public void setpBloodPressure(final List<PatientBloodPressure> pBloodPressure) {
        this.pBloodPressure = pBloodPressure;
    }

    @Override
    public List<PatientBloodPressure> getList() {
        return pBloodPressure;
    }

    @Override
    public String toString() {
        return "PatientBloodPressureList{" +
                "pBloodPressure=" + pBloodPressure +
                '}';
    }
}
