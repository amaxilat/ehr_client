package com.amaxilatis.ehr;

import com.amaxilatis.ehr.exception.PatientIdExistsException;
import com.amaxilatis.ehr.model.*;
import com.amaxilatis.ehr.model.list.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * A client class to interact with an EHR server.
 *
 * @author Dimitrios Amaxilatis.
 */
public class EhrClient {
    /**
     * a log4j logger to print messages.
     */
    protected static final Logger LOGGER = Logger.getLogger(EhrClient.class);
    private static final String QUERY_ALL = "{}";
    private final String connectionUrl;
    private final ObjectMapper objectMapper;

    /**
     * Prints an error message to {@link #LOGGER}.
     *
     * @param message The error message to print.
     * @param error   The {@link Throwable} that cause the error.
     */
    private static void error(final String message, final Throwable error) {
        String msg;
        if (error != null) {
            msg = message + ": " + error.getMessage();
        } else {
            msg = message;
        }

        LOGGER.error(msg, error);
    }

    /**
     * Prints an error message to {@link #LOGGER}.
     *
     * @param message The error message to print.
     */
    private static void error(final String message) {
        error(message, null);
    }

    /**
     * Creates a new EhrClient.
     *
     * @param connectionUrl the url of the EHR Service.
     */
    public EhrClient(final String connectionUrl) {
        this.connectionUrl = connectionUrl;
        objectMapper = new ObjectMapper();
    }

    /**
     * Add the {@link Patient} to the EHR.
     *
     * @param patient the {@link Patient} to add.
     * @return
     */
    public String addPatient(final Patient patient) {
        try {
            return postPath("InsertPatientData", patient);
        } catch (PatientIdExistsException e) {
            return null;
        }
    }

    /**
     * Add the {@link AdmissionData} to the EHR.
     *
     * @param admissionData the {@link AdmissionData} to add.
     * @return
     */
    public String addAdmissionData(final AdmissionData admissionData) {
        try {
            return postPath("InsertAdmissionData", admissionData);
        } catch (PatientIdExistsException e) {
            return null;
        }
    }

    /**
     * Update the {@link Patient} to the EHR.
     *
     * @param patient the {@link Patient} to update.
     * @return
     */
    public String updatePatient(final Patient patient) {
        try {
            LOGGER.warn("Patient " + patient.getPatientId() + " exists updating data...");
            return postPath("UpdatePatientData", patient);
        } catch (PatientIdExistsException e1) {
            return null;
        }
    }

    /**
     * Returns the {@link Patient} from the EHR service with the given id.
     *
     * @param patientId the id of the {@link Patient} to search for.
     * @return the {@link Patient} requested.
     */
    public Patient getPatientByPatientId(final String patientId) {
        final String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        LOGGER.debug(query);
        String resp = null;
        try {
            resp = postPath("SelectPatientData", query);
            LOGGER.info(resp);
            PatientDataList list = objectMapper.readValue(resp, PatientDataList.class);
            return list.getPatientData().get(0);
        } catch (IOException e) {
            LOGGER.error(resp, e);
            return null;
        }
    }

    /**
     * Adds a new {@link Allergies} to EHR.
     *
     * @param allergies The {@link Allergies} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addAllergy(final Allergies allergies) {
        return save("InsertAllergies", allergies);
    }

    /**
     * Returns all {@link Allergies} with the given {@link Patient} id.
     *
     * @param patientId the id of the {@link Patient} to search for.
     * @return a List of {@link Allergies} of the {@link Patient}.
     */
    public List<Allergies> getAllergiesByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getAllAllertiesByQuery(query);
    }

    /**
     * Gets all the {@link Allergies} for the given {@link Patient} and {@link AdmissionData} ids.
     *
     * @param patientId The id of the {@link Patient}.
     * @param admissionId The id of the {@link AdmissionData}.
     * @return The {@link Allergies} that match the query or null in case of an error.
     */
    public List<Allergies> getAllertiesByPatientAndAdmissionId(final String patientId,
                                                               final long admissionId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\", \"admissionId\":\"" + admissionId + "\"}}";
        return getAllAllertiesByQuery(query);
    }

    /**
     * Gets all the {@link Allergies} that match the given query.
     *
     * @param query The query to match.
     * @return The allerties that match the query or null in case of an error.
     */
    private List<Allergies> getAllAllertiesByQuery(final String query) {
        LOGGER.debug("Allergies by query: " + query);
        AllergiesDataList allergiesDataList = getAll("SelectAllergies", query, AllergiesDataList.class);
        if (allergiesDataList != null) {
            return allergiesDataList.getAllergy();
        }

        return null;
    }

    /**
     * Returns all {@link AdmissionData} with the given {@link Patient} id.
     *
     * @param patientId the id of the {@link Patient} to search for.
     * @return a List of {@link AdmissionData} of the {@link Patient}.
     */
    public List<AdmissionData> getAdmissionsByPatientId(final String patientId) {
        final String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        LOGGER.debug(query);
        String resp = null;
        try {
            resp = postPath("SelectAdmissionData", query);
            LOGGER.info(resp);
            AdmissionDataList list = objectMapper.readValue(resp, AdmissionDataList.class);
            return list.getAdmissionData();
        } catch (IOException e) {
            LOGGER.error(resp, e);
            return null;
        }
    }

    /**
     * Returns the {@link AdmissionData} with this id.
     *
     * @param admissionId The id of the {@link AdmissionData} to fetch.
     * @return An {@link AdmissionData} or null in case of an error.
     */
    public AdmissionData getAdmissionByAdmissionId(final long admissionId) {
        final String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        LOGGER.debug(query);
        AdmissionDataList admissionDataList = getAll("SelectAdmissionData", query, AdmissionDataList.class);
        if (admissionDataList != null) {
            List<AdmissionData> admissionList = admissionDataList.getAdmissionData();
            if (admissionList != null && admissionList.size() > 0) {
                return admissionList.get(0);
            }
        }
        return null;
    }

    /**
     * Adds a new {@link AdmissionType} to EHR.</p>
     *
     * @param admissionType The {@link AdmissionType} to insert.
     * @return A JSON String or null in case of an error.
     */
    public String insertAdmissionType(final AdmissionType admissionType) {
        try {
            return postPath("InsertAdmissionType", admissionType);
        } catch (Exception error) {
            LOGGER.error("Error while adding new AdmissionType: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns the {@link AdmissionType} with the give type id.
     *
     * @param admissionTypeId The id of the {@link AdmissionType} to select.
     * @return An {@link AdmissionType} or null in case of an error.
     */
    public AdmissionType getAdmissionTypeByAdmissionTypeId(final int admissionTypeId) {
        final String query = "{\"=\":{\"admissionTypeId\":\"" + admissionTypeId + "\"}}";
        LOGGER.debug(query);
        try {
            String response = postPath("SelectAdmissionType", query);
            return objectMapper.readValue(response, AdmissionTypeList.class).getAdmissionType().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while selecting AdmissionType: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns all {@link AdmissionType} entities.
     *
     * @return all {@link AdmissionType}.
     */
    public List<AdmissionType> getAdmissionsTypes() {
        String resp = null;
        try {
            resp = postPath("SelectAdmissionType", QUERY_ALL);
            LOGGER.info(resp);
            AdmissionTypeList list = objectMapper.readValue(resp, AdmissionTypeList.class);
            return list.getAdmissionType();
        } catch (IOException e) {
            LOGGER.error(resp, e);
            return null;
        }
    }

    /**
     * Returns all {@link Patient} entities.
     *
     * @return a list of all registered {@link Patient}s.
     */
    public List<Patient> getAllPatients() {
        String resp = null;
        try {
            resp = postPath("SelectPatientData", QUERY_ALL);
            LOGGER.info(resp);
            PatientDataList list = objectMapper.readValue(resp, PatientDataList.class);
            return list.getPatientData();
        } catch (IOException e) {
            LOGGER.error(resp, e);
            return null;
        }
    }

    /**
     * Adds a new {@link MedicalDevices} to EHR.
     *
     * @param medicalDevices The {@link MedicalDevices} to add.
     * @return A JSON string in case of success, null otherwise.
     */
    public String addMedicalDevices(final MedicalDevices medicalDevices) {
        try {
            return postPath("InsertMedicalDevices", medicalDevices);
        } catch (Exception error) {
            LOGGER.error("Error while inserting new MedicalDevices: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns all {@link MedicalDevices} entities.
     *
     * @return A {@link List} of {@link MedicalDevices} or null in case of an error.
     */
    public List<MedicalDevices> getAllMedicalDevices() {
        try {
            String response = postPath("SelectMedicalDevices", QUERY_ALL);
            return objectMapper.readValue(response, MedicalDevicesList.class).getMedicalDevices();

        } catch (Exception error) {
            LOGGER.error("Error while querying MedicalDevices: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns a {@link MedicalDevices} with the given id.
     *
     * @param medicalDevicesId The id of the {@link MedicalDevices} to select.
     * @return A {@link MedicalDevices} or null in case of an error.
     */
    public MedicalDevices getMedicalDevicesByMedicalDevicesId(final int medicalDevicesId) {
        final String query = "{\"=\":{\"medicalDevicesId\":\"" + medicalDevicesId + "\"}}";
        LOGGER.debug(query);
        try {
            String response = postPath("SelectMedicalDevices", query);
            return objectMapper.readValue(response, MedicalDevicesList.class).getMedicalDevices().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while selecting MedicalDevices by id: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Inserts a new {@link Scheduling} to EHR.
     *
     * @param scheduling The {@link Scheduling} to insert.
     * @return A JSON String in case of success, null otherwise.
     */
    public String addScheduling(final Scheduling scheduling) {
        try {
            return postPath("InsertScheduling", scheduling);
        } catch (Exception error) {
            LOGGER.error("Error while inserting new Scheduling: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns all {@link Scheduling} entities.
     *
     * @return A {@link List} of {@link Scheduling} or null in case of an error.
     */
    public List<Scheduling> getAllScheduling() {
        try {
            String response = postPath("SelectScheduling", QUERY_ALL);
            return objectMapper.readValue(response, SchedulingList.class).getScheduling();
        } catch (Exception error) {
            LOGGER.error("Error getting all Scheduling: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns the {@link Scheduling} entity with the given id.
     *
     * @param schedulingId The id of the {@link Scheduling} to get.
     * @return A {@link Scheduling} or null in case of an error.
     */
    public Scheduling getSchedulingBySchedulingId(final int schedulingId) {
        final String query = "{ \"=\":{\"schedulingId\":\"" + schedulingId + "\"}}";
        LOGGER.debug(query);
        try {
            String response = postPath("SelectScheduling", query);
            return objectMapper.readValue(response, SchedulingList.class).getScheduling().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting Scheduling by id: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns all {@link Scheduling} entities with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link Scheduling} to get.
     * @return A {@link Scheduling} or null in case of an error.
     */
    public List<Scheduling> getSchedulingByPatientId(final String patientId) {
        final String query = "{ \"=\":{\"patientId\":\"" + patientId + "\"}}";
        LOGGER.debug(query);
        SchedulingList schedulingList = getAll("SelectScheduling", query, SchedulingList.class);
        if (schedulingList != null) {
            return schedulingList.getScheduling();
        }
        return null;
    }

    /**
     * Saves a new {@link PregnancyHistory} to EHR.
     *
     * @param pregnancyHistory The {@link PregnancyHistory} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addPregnancyHistory(final PregnancyHistory pregnancyHistory) {
        try {
            return postPath("InsertPregnancyHistory", pregnancyHistory);
        } catch (Exception error) {
            LOGGER.error("Error while inserting new PregnancyHistory: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns all {@link PregnancyHistory} entities.
     *
     * @return A {@link List} of {@link PregnancyHistory} or null in case of an error.
     */
    public List<PregnancyHistory> getAllPregnancyHistory() {
        return getPregnancyHistoryByQuery(QUERY_ALL);
    }

    /**
     * Returns all {@link PregnancyHistory} entities with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link PregnancyHistory} to get.
     * @return A {@link List} of {@link PregnancyHistory} or null in case of an error.
     */
    public List<PregnancyHistory> getPregnancyHistoryByPatientId(final String patientId) {
        final String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        LOGGER.debug(query);
        return getPregnancyHistoryByQuery(query);
    }

    private List<PregnancyHistory> getPregnancyHistoryByQuery(final String query) {
        try {
            String response = postPath("SelectPregnancyHistory", query);
            return objectMapper.readValue(response, PregnancyHistoryList.class).getPregnancyHistory();
        } catch (Exception error) {
            LOGGER.error("Error while getting PregnancyHistory for patient: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns the {@link PregnancyHistory} with the given id.
     *
     * @param pregnancyId The id of the {@link PregnancyHistory} to get.
     * @return A {@link PregnancyHistory} or null in case of an error.
     */
    public PregnancyHistory getPregnancyHistoryByPregnancyId(final int pregnancyId) {
        final String query = "{\"=\":{\"pregrancyId\":\"" + pregnancyId + "\"}}";
        LOGGER.debug(query);
        try {
            String response = postPath("SelectPregnancyHistory", query);
            return objectMapper.readValue(response, PregnancyHistoryList.class).getPregnancyHistory().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting PregnancyHistory by id: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Inserts a new {@link Medication} to EHR.
     *
     * @param medication The {@link Medication} to insert.
     * @return A JSON String or null in case of an error.
     */
    public String addMedication(final Medication medication) {
        try {
            return postPath("InsertMedication", medication);
        } catch (Exception error) {
            LOGGER.error("Error while inserting Medication: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns all {@link Medication} entities.
     *
     * @return A {@link List} of {@link Medication} or null in case of an error.
     */
    public List<Medication> getAllMedication() {
        try {
            String response = postPath("SelectMedication", QUERY_ALL);
            return objectMapper.readValue(response, MedicationList.class).getMedication();
        } catch (Exception error) {
            LOGGER.error("Error while getting all Medication: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns the {@link Medication} with the given id.
     *
     * @param medicationId The id of the {@link Medication} to get.
     * @return A {@link Medication} or null in case of an error.
     */
    public Medication getMedicationByMedicationId(final int medicationId) {
        final String query = "{\"=\":{\"medicationId\":\"" + medicationId + "\"}}";
        LOGGER.debug(query);
        try {
            String response = postPath("SelectMedication", query);
            return objectMapper.readValue(response, MedicationList.class).getMedication().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting Medication by id: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Saves a new {@link LabAnalysis} to EHR.
     *
     * @param labAnalysis The {@link LabAnalysis} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addLabAnalysis(final LabAnalysis labAnalysis) {
        try {
            return postPath("InsertLabAnalysis", labAnalysis);
        } catch (Exception error) {
            LOGGER.error("Error while inserting LabAnalysis: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns all {@link LabAnalysis} entities.
     *
     * @return A {@link List} of {@link LabAnalysis} or null in case of an error.
     */
    public List<LabAnalysis> getAllLabAnalysis() {
        try {
            String response = postPath("SelectLabAnalysis", QUERY_ALL);
            return objectMapper.readValue(response, LabAnalysisList.class).getLabAnalysis();
        } catch (Exception error) {
            LOGGER.error("Error while getting all LabAnalysis: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns the {@link LabAnalysis} with the given id.
     *
     * @param labAnalysisId The id of the {@link LabAnalysis} to fetch.
     * @return A {@link LabAnalysis} or null in case of an error.
     */
    public LabAnalysis getLabAnalysisByLabAnalysisId(final int labAnalysisId) {
        final String query = "{\"=\":{\"labAnalysisId\":\"" + labAnalysisId + "\"}}";
        LOGGER.debug(query);
        try {
            String response = postPath("SelectLabAnalysis", query);
            return objectMapper.readValue(response, LabAnalysisList.class).getLabAnalysis().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting LabAnalysis with id " + labAnalysisId + ": " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Insert a new {@link Coding} to EHR.
     *
     * @param coding The {@link Coding} to insert.
     * @return A JSON String or null in case of an error.
     */
    public String addCoding(final Coding coding) {
        try {
            return postPath("InsertCoding", coding);
        } catch (Exception error) {
            LOGGER.error("Error while adding new coding: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns all {@link Coding} entities.
     *
     * @return A {@link List} of {@link Coding} or null in case of an error.
     */
    public List<Coding> getAllCoding() {
        try {
            String response = postPath("SelectCoding", QUERY_ALL);
            return objectMapper.readValue(response, CodingList.class).getCoding();
        } catch (Exception error) {
            LOGGER.error("Error while getting all Coding: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns the {@link Coding} with the given id.
     *
     * @param codingId The id of the {@link Coding} to fetch.
     * @return A {@link Coding} or null in case of an error.
     */
    public Coding getCodingByCodingId(final int codingId) {
        final String query = "{\"=\":{\"codingId\":\"" + codingId + "\"}}";
        LOGGER.debug(query);
        try {
            String response = postPath("SelectCoding", query);
            return objectMapper.readValue(response, CodingList.class).getCoding().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting Coding with id " + codingId + ": " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Saves a new {@link InsuranceData} to EHR.
     *
     * @param insuranceData The {@link InsuranceData} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addInsuranceData(final InsuranceData insuranceData) {
        try {
            return postPath("InsertInsuranceData", insuranceData);
        } catch (Exception error) {
            LOGGER.error("Error while inserting InsuranceData: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns all {@link InsuranceData} entities.
     *
     * @return A {@link List} of {@link InsuranceData} or null in case of an error.
     */
    public List<InsuranceData> getAllInsuranceData() {
        try {
            return getInsuranceDataByQuery(QUERY_ALL);
        } catch (Exception error) {
            LOGGER.error("Error while getting all InsuranceData: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * Returns the {@link InsuranceData} with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link InsuranceData} to fetch.
     * @return A {@link List} of {@link InsuranceData} or null in case of an error.
     */
    public List<InsuranceData> getInsuranceDataByPatientId(final int patientId) {
        final String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        LOGGER.debug(query);
        try {
            return getInsuranceDataByQuery(query);
        } catch (Exception error) {
            LOGGER.error("Error while getting InsuranceData with patientId " + patientId + ": " + error.getMessage(), error);
            return null;
        }
    }

    private List<InsuranceData> getInsuranceDataByQuery(final String query) throws IOException {
        final String response = postPath("SelectInsuranceData", query);
        return objectMapper.readValue(response, InsuranceDataList.class).getInsuranceData();
    }


    /**
     * Returns the {@link InsuranceData} with the given id.
     *
     * @param insuranceId The id of the {@link InsuranceData} to fetch.
     * @return An {@link InsuranceData} or null in case of an error.
     */
    public InsuranceData getInsuranceDataByInsuranceId(final int insuranceId) {
        final String query = "{\"=\":{\"insuranceId\":\"" + insuranceId + "\"}}";
        LOGGER.debug(query);
        try {
            String response = postPath("SelectInsuranceData", query);
            return objectMapper.readValue(response, InsuranceDataList.class).getInsuranceData().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting InsuranceData with id " + insuranceId + ": " + error.getMessage(), error);
            return null;
        }
    }


    /**
     * Add a new {@link PatientMedicalDevices} to EHR.
     *
     * @param patientMedicalDevices The {@link PatientMedicalDevices} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addPatientMedicalDevices(final PatientMedicalDevices patientMedicalDevices) {
        return save("InsertPatientMedicalDevices", patientMedicalDevices);
    }

    /**
     * Returns all {@link PatientMedicalDevices} entities.
     *
     * @return A {@link List} of {@link PatientMedicalDevices} or null in case of an error.
     */
    public List<PatientMedicalDevices> getAllPatientMedicalDevices() {
        PatientMedicalDevicesList patientMedicalDevicesList = getAll("SelectPatientMedicalDevices", PatientMedicalDevicesList.class);
        if (patientMedicalDevicesList != null) {
            return patientMedicalDevicesList.getPatientMedicalDevices();
        }

        return null;
    }

    /**
     * Returns the {@link PatientMedicalDevices} with the given serial number.
     *
     * @param patientMedicalDeviceSn The S/N of the {@link PatientMedicalDevices} to fetch.
     * @return A {@link PatientMedicalDevices} or null in case of an error.
     */
    public PatientMedicalDevices getPatientMedicalDevicesByPatientMedicalDeviceSn(final String patientMedicalDeviceSn) {
        final String query = "{\"=\":{\"patientMedicalDeviceSn\":\"" + patientMedicalDeviceSn + "\"}}";
        LOGGER.debug(query);
        PatientMedicalDevicesList patientMedicalDevicesList = getAll("SelectPatientMedicalDevices", query, PatientMedicalDevicesList.class);
        if (patientMedicalDevicesList != null) {
            List<PatientMedicalDevices> patientMedicalDevices = patientMedicalDevicesList.getPatientMedicalDevices();
            if (patientMedicalDevices != null && patientMedicalDevices.size() > 0) {
                return patientMedicalDevices.get(0);
            }
        }

        return null;
    }

    /**
     * Returns all {@link PatientMedicalDevices} with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link PatientMedicalDevices} to fetch.
     * @return A {@link List} of {@link PatientMedicalDevices} or null in case of an error.
     */
    public List<PatientMedicalDevices> getPatientMedicalDevicesByPatientId(final int patientId) {
        final String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        LOGGER.debug(query);
        PatientMedicalDevicesList patientMedicalDevicesList = getAll("SelectPatientMedicalDevices", query, PatientMedicalDevicesList.class);
        if (patientMedicalDevicesList != null) {
            return patientMedicalDevicesList.getPatientMedicalDevices();
        }

        return null;
    }

    /**
     * Saves a new {@link Diagnosis} to EHR.
     *
     * @param diagnosis The {@link Diagnosis} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addDiagnosis(final Diagnosis diagnosis) {
        return save("InsertDiagnosis", diagnosis);
    }

    /**
     * Returns all {@link Diagnosis} entities.
     *
     * @return A {@link List} of {@link Diagnosis} or null in case of an error.
     */
    public List<Diagnosis> getAllDiagnosis() {
        return getList("SelectDiagnosis", DiagnosisList.class);
    }

    /**
     * Returns the {@link Diagnosis} with the given id.
     *
     * @param diagnosisId The id of the {@link Diagnosis} to fetch.
     * @return A {@link Diagnosis} or null in case of an error.
     */
    public Diagnosis getDiagnosisByDiagnosisId(final long diagnosisId) {
        String query = "{\"=\":{\"diagnosisId\":\"" + diagnosisId + "\"}}";
        return getSingle("SelectDiagnosis", query, DiagnosisList.class);
    }

    /**
     * Returns all {@link Diagnosis} with the given {@link AdmissionData} id.
     *
     * @param admissionId The id of the {@link AdmissionData} for which to fetch the {@link Diagnosis}.
     * @return A {@link List} of {@link Diagnosis} or null.
     */
    public List<Diagnosis> getDiagnosisByAdmissionId(final long admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectDiagnosis", query, DiagnosisList.class);
    }

    /**
     * Returns all {@link Diagnosis} withe the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link Diagnosis} to fetch.
     * @return A {@link List} of {@link Diagnosis} or null in case of an error.
     */
    public List<Diagnosis> getDiagnosisByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectDiagnosis", query, DiagnosisList.class);
    }

    /**
     * Saves a new {@link PatientMedication} to EHR.
     *
     * @param patientMedication The {@link PatientMedication} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addPatientMedication(final PatientMedication patientMedication) {
        return save("InsertPatientMedication", patientMedication);
    }

    /**
     * Gets a {@link PatientMedication} by its id.
     *
     * @param patientMedicationId The id of the {@link PatientMedication} to fetch.
     * @return A {@link PatientMedication} or null in case of an error.
     */
    public PatientMedication getPatientMedicationByPatientMedicationId(final long patientMedicationId) {
        String query = "{\"=\":{\"patientMedicationId\":\"" + patientMedicationId + "\"}}";
        return getSingle("SelectPatientMedications", query, PatientMedicationList.class);
    }

    /**
     * Gets all the {@link PatientMedication} saved in EHR.
     *
     * @return A {@link List} of {@link PatientMedication} or null in case of an error.
     */
    public List<PatientMedication> getPatientMedication() {
        return getList("SelectPatientMedications", PatientMedicationList.class);
    }

    /**
     * Gets all the {@link PatientMedication} saved in EHR for the given {@link AdmissionData} id.
     *
     * @param admissionId The id of the {@link AdmissionData}.
     * @return A {@link List} of {@link PatientMedication} associated with the given {@link AdmissionData} id,
     *         or null in case of an error.
     */
    public List<PatientMedication> getPatientMedicationByAdmissionId(final long admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectPatientMedications", query, PatientMedicationList.class);
    }

    /**
     * Gets all the {@link PatientMedication} saved in EHR for the given {@link AdmissionData} id and
     * {@link Patient} id.
     *
     * @param admissionId The id of the {@link AdmissionData}.
     * @param patientId The id of the {@link Patient}.
     * @return A {@link List} of {@link PatientMedication} that match the query, or null in case of an error.
     */
    public List<PatientMedication> getPatientMedicationByAdmissionIdAndPatientId(final long admissionId,
                                                                                 final String patientId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\", \"patientId\":\"" + patientId +"\"}}";
        return getList("SelectPatientMedications", query, PatientMedicationList.class);
    }

    /**
     * Generic method for saving entities to EHR.
     *
     * @param path   The path where to save the entity.
     * @param entity The entity to save.
     * @param <A>    The type of the entity to save.
     * @return A JSON String or null in case of an error.
     */
    private <A> String save(final String path, final A entity) {
        try {
            return postPath(path, entity);
        } catch (Exception error) {
            error("Error while saving to " + path, error);
            return null;
        }
    }

    /**
     * Generic methods to get all the entities in the given path that match the given query.
     *
     * @param path     The path to query.
     * @param query    The query to run.
     * @param theClass The class to convert the reponse to.
     * @param <A>      The type of the response.
     * @return An instance of A or null in case of an error.
     */
    private <A> A getAll(final String path, final String query, final Class<A> theClass) {
        try {
            String response = postPath(path, query);
            return objectMapper.readValue(response, theClass);
        } catch (Exception error) {
            error("Error while getting all entities from " + path, error);
            return null;
        }
    }

    /**
     * Generic methods to get all the entities in the given path.
     *
     * @param path     The path to query.
     * @param theClass The class to convert the reponse to.
     * @param <A>      The type of the response.
     * @return An instance of A or null in case of an error.
     */
    private <A> A getAll(final String path, final Class<A> theClass) {
        return getAll(path, QUERY_ALL, theClass);
    }

    /**
     * Generic methods to get all the entities in the given path.
     *
     * @param path The path to query.
     * @param query The query to match.
     * @param theClass The type of the response that is {@link Listable}.
     * @param <A> The type of entities that will be returned.
     * @return A {@link List} of {@link A}s that match the query or null in case of an error.
     */
    private <A, B extends Listable<A>> List<A> getList(final String path,
                                                       final String query,
                                                       final Class<B> theClass) {
        Listable<A> list = getAll(path, query, theClass);
        if (list != null) {
            return list.getList();
        }

        return null;
    }

    /**
     * Generic methods to get all the entities in the given path.
     *
     * @param path The path to query.
     * @param theClass The type of the response that is {@link Listable}.
     * @param <A> The type of entities that will be returned.
     * @return A {@link List} of {@link A}s or null in case of an error.
     */
    private <A, B extends Listable<A>> List<A> getList(final String path,
                                                       final Class<B> theClass) {
        return getList(path, QUERY_ALL, theClass);
    }

    /**
     * Gets the single entity of type {@link A} that matches the given query.
     *
     * @param path The path from which to select the entity.
     * @param query The query to match.
     * @param theClass The class of type {@link B} that the response will be converted to.
     * @param <A> The type of the entity to return.
     * @param <B> The type of the response EHR will return.
     * @return An instance of {@link A} or null in case of an error.
     */
    private <A, B extends Listable<A>> A getSingle(final String path,
                                                   final String query,
                                                   final Class<B> theClass) {
        List<A> listOfEntities = getList(path, query, theClass);
        if (listOfEntities != null && listOfEntities.size() > 0) {
            return listOfEntities.get(0);
        }

        return null;
    }

    /**
     * Execute a put request to the specified path.
     *
     * @param path   the path to request.
     * @param entity the text containing the entity to store.
     * @return the response string from the server.
     */
    private String postPath(final String path, final Object entity) throws PatientIdExistsException {

        try {
            final Entity payload = Entity.json(objectMapper.writeValueAsString(entity));
            LOGGER.debug(payload);
            final Response response = getClientForPath(path).post(payload);
            LOGGER.debug("getStatus: " + response.getStatus());
            if (response.getStatus() == 400) {
                throw new PatientIdExistsException();
            }
            LOGGER.debug("getEntity: " + response.getEntity());
            LOGGER.debug("getStatusInfo: " + response.getStatusInfo());
            return response.readEntity(String.class);
        } catch (JsonProcessingException e) {
            LOGGER.error(e, e);
        }
        return null;
    }

    /**
     * Execute a put request to the specified path.
     *
     * @param path   the path to request.
     * @param entity the text containing the entity to store.
     * @return the response string from the server.
     */
    private String postPath(final String path, final String entity) {
        final Entity payload = Entity.json(entity);
        LOGGER.debug(payload);
        final Response response = getClientForPath(path).post(payload);
        LOGGER.debug("status: " + response.getStatus());
        LOGGER.debug("status: " + response.getEntity());
        LOGGER.debug("status: " + response.getStatusInfo());
        return response.readEntity(String.class);
    }

    /**
     * Get a client to the orion context broker for the selected path.
     *
     * @param path the path to request.
     * @return a client to execute an http request.
     */
    private Invocation.Builder getClientForPath(final String path) {
        Client client = ClientBuilder.newClient();
        LOGGER.debug("path: " + path);
        return client.target(connectionUrl)
                .path(path)
                .request()
                .header("Content-Type", "application/json");
    }


}
