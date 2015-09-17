package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.InsuranceData;

import java.util.List;

/**
 * <p>A {@link List} of {@link InsuranceData}</p>
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
public class InsuranceDataList {

    private List<InsuranceData> insuranceData;

    public List<InsuranceData> getInsuranceData() {
        return insuranceData;
    }

    public void setInsuranceData(List<InsuranceData> insuranceData) {
        this.insuranceData = insuranceData;
    }

    @Override
    public String toString() {
        return "InsuranceDataList{" +
                "insuranceData=" + insuranceData +
                '}';
    }
}
