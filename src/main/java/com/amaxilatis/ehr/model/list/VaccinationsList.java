package com.amaxilatis.ehr.model.list;

import com.amaxilatis.ehr.model.Vaccinations;

import java.util.List;

/**
 * A list of {@link Vaccinations}.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
public class VaccinationsList implements Listable<Vaccinations> {
    private List<Vaccinations> vaccinations;

    public List<Vaccinations> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(final List<Vaccinations> vaccinations) {
        this.vaccinations = vaccinations;
    }

    @Override
    public List<Vaccinations> getList() {
        return vaccinations;
    }

    @Override
    public String toString() {
        return "VaccinationsList{" +
                "vaccinations=" + vaccinations +
                '}';
    }
}
