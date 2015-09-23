package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.LabAnalysis;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * <p>A {@link List} of {@link LabAnalysis}.</p>
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LabAnalysisList implements Listable<LabAnalysis> {

    private List<LabAnalysis> labAnalysis;

    public List<LabAnalysis> getLabAnalysis() {
        return labAnalysis;
    }

    public void setLabAnalysis(List<LabAnalysis> labAnalysis) {
        this.labAnalysis = labAnalysis;
    }

    @Override
    public List<LabAnalysis> getList() {
        return labAnalysis;
    }

    @Override
    public String toString() {
        return "LabAnalysisList{" +
                "labAnalysis=" + labAnalysis +
                '}';
    }
}
