package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.MedicalDevices;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicalDevicesList implements Listable<MedicalDevices> {

    private List<MedicalDevices> medicalDevices;

    public List<MedicalDevices> getMedicalDevices() {
        return medicalDevices;
    }

    public void setMedicalDevices(List<MedicalDevices> medicalDevices) {
        this.medicalDevices = medicalDevices;
    }

    @Override
    public List<MedicalDevices> getList() {
        return medicalDevices;
    }

    @Override
    public String toString() {
        return "MedicalDevicesList{" +
                "medicalDevices=" + medicalDevices +
                '}';
    }
}
