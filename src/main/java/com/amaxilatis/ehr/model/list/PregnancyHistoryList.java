package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.PregnancyHistory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * <p>A {@link java.util.List} of {@link PregnancyHistory}.</p>
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PregnancyHistoryList {

    @JsonProperty("patientData")
    private List<PregnancyHistory> pregnancyHistory;

    public List<PregnancyHistory> getPregnancyHistory() {
        return pregnancyHistory;
    }

    public void setPregnancyHistory(List<PregnancyHistory> pregnancyHistory) {
        this.pregnancyHistory = pregnancyHistory;
    }

    @Override
    public String toString() {
        return "PregnancyHistoryList{" +
                "pregnancyHistory=" + pregnancyHistory +
                '}';
    }
}
