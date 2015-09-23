package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.Medication;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * <p>A {@link List} of {@link Medication}.</p>
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicationList implements Listable<Medication> {

    private List<Medication> medication;

    public List<Medication> getMedication() {
        return medication;
    }

    public void setMedication(List<Medication> medication) {
        this.medication = medication;
    }

    @Override
    public List<Medication> getList() {
        return medication;
    }

    @Override
    public String toString() {
        return "MedicationList{" +
                "medication=" + medication +
                '}';
    }
}
