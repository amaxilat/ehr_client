package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Scheduling {
    private String schedulingId;
    private String patientId;
    private String physicianId;
    private String appointmentDate;
    private String description;
    private String durationMinutes;

    /**
     * <p>Default no-argument constructor that sets the required fields to insert a new {@link Scheduling}
     * to EHR to non-null values.</p>
     */
    public Scheduling() {
        schedulingId = null;
        patientId = "no_value";
        physicianId = "no_value";
        appointmentDate = "no_value";
        description = "no_value";
        durationMinutes = "no_value";
    }

    public String getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(String schedulingId) {
        this.schedulingId = schedulingId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(String physicianId) {
        this.physicianId = physicianId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(String durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String toString() {
        return "Scheduling{" +
                "schedulingId=" + schedulingId +
                ", patientId='" + patientId + '\'' +
                ", physicianId='" + physicianId + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", description='" + description + '\'' +
                ", durationMinutes='" + durationMinutes + '\'' +
                '}';
    }
}