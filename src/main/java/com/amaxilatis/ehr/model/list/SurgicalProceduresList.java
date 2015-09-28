package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.SurgicalProcedures;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * A list of {@link SurgicalProcedures}.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurgicalProceduresList implements Listable<SurgicalProcedures> {
    private List<SurgicalProcedures> surgicalProcedures;

    public List<SurgicalProcedures> getSurgicalProcedures() {
        return surgicalProcedures;
    }

    public void setSurgicalProcedures(final List<SurgicalProcedures> surgicalProcedures) {
        this.surgicalProcedures = surgicalProcedures;
    }

    @Override
    public List<SurgicalProcedures> getList() {
        return surgicalProcedures;
    }

    @Override
    public String toString() {
        return "SurgicalProceduresList{" +
                "surgicalProcedures=" + surgicalProcedures +
                '}';
    }
}
