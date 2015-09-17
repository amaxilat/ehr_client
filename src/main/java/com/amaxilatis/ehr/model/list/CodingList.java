package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.Coding;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * <p>A {@link java.util.List} of {@link Coding}s</p>
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodingList {

    private List<Coding> coding;

    public List<Coding> getCoding() {
        return coding;
    }

    public void setCoding(List<Coding> coding) {
        this.coding = coding;
    }

    @Override
    public String toString() {
        return "CodingList{" +
                "coding=" + coding +
                '}';
    }
}
