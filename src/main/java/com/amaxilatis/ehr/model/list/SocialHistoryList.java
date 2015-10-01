package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.SocialHistory;

import java.util.List;

/**
 * A list of {@link SocialHistory}.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
public class SocialHistoryList implements Listable<SocialHistory> {
    private List<SocialHistory> socialHistory;

    public List<SocialHistory> getSocialHistory() {
        return socialHistory;
    }

    public void setSocialHistory(final List<SocialHistory> socialHistory) {
        this.socialHistory = socialHistory;
    }

    @Override
    public List<SocialHistory> getList() {
        return socialHistory;
    }

    @Override
    public String toString() {
        return "SocialHistoryList{" +
                "socialHistory=" + socialHistory +
                '}';
    }
}
