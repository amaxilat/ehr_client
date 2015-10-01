package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.FunctionalStatus;

import java.util.List;

/**
 * A list of {@link FunctionalStatus}.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
public class FunctionalStatusList implements Listable<FunctionalStatus> {
    private List<FunctionalStatus> functionalStatus;

    public List<FunctionalStatus> getFunctionalStatus() {
        return functionalStatus;
    }

    public void setFunctionalStatus(final List<FunctionalStatus> functionalStatus) {
        this.functionalStatus = functionalStatus;
    }

    @Override
    public List<FunctionalStatus> getList() {
        return functionalStatus;
    }

    @Override
    public String toString() {
        return "FunctionalStatusList{" +
                "functionalStatus=" + functionalStatus +
                '}';
    }
}
