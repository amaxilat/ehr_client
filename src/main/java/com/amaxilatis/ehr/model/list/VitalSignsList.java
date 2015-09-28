package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.VitalSigns;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * A list of {@link com.amaxilatis.ehr.model.VitalSigns}.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VitalSignsList implements Listable<VitalSigns> {

    private List<VitalSigns> vitalSigns;

    public List<VitalSigns> getVitalSigns() {
        return vitalSigns;
    }

    public void setVitalSigns(final List<VitalSigns> vitalSigns) {
        this.vitalSigns = vitalSigns;
    }

    @Override
    public List<VitalSigns> getList() {
        return vitalSigns;
    }

    @Override
    public String toString() {
        return "VitalSignsList{" +
                "vitalSigns=" + vitalSigns +
                '}';
    }
}
