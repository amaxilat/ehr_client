package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.Allergies;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllergiesDataList implements Listable<Allergies> {
    List<Allergies> allergy;

    public List<Allergies> getAllergy() {
        return allergy;
    }

    public void setAllergy(List<Allergies> allergy) {
        this.allergy = allergy;
    }

    @Override
    public List<Allergies> getList() {
        return allergy;
    }

    @Override
    public String toString() {
        return "AllergiesDataList{" +
                "allergy=" + allergy +
                '}';
    }
}
