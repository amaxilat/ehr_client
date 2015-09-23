package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.Diagnosis;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * <p>A {@link List} of {@link Diagnosis}.</p>
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiagnosisList implements Listable<Diagnosis> {

    private List<Diagnosis> diagnosis;

    public List<Diagnosis> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(List<Diagnosis> diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public List<Diagnosis> getList() {
        return diagnosis;
    }

    @Override
    public String toString() {
        return "DiagnosisList{" +
                "diagnosis=" + diagnosis +
                '}';
    }
}
