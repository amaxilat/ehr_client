package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.Scheduling;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * A {@link List} of {@link Scheduling}s.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchedulingList implements Listable<Scheduling> {

    private List<Scheduling> scheduling;

    public List<Scheduling> getScheduling() {
        return scheduling;
    }

    public void setScheduling(List<Scheduling> scheduling) {
        this.scheduling = scheduling;
    }

    @Override
    public List<Scheduling> getList() {
        return scheduling;
    }

    @Override
    public String toString() {
        return "SchedulingList{" +
                "scheduling=" + scheduling +
                '}';
    }
}
