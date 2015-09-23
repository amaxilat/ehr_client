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
        return save("InsertAdmissionType", admissionType);
    }

    /**
     * Returns the {@link AdmissionType} with the give type id.
     *
     * @param admissionTypeId The id of the {@link AdmissionType} to select.
     * @return An {@link AdmissionType} or null in case of an error.
     */
    public AdmissionType getAdmissionTypeByAdmissionTypeId(final int admissionTypeId) {
        String query = "{\"=\":{\"admissionTypeId\":\"" + admissionTypeId + "\"}}";
        return getSingle("SelectAdmissionType", query, AdmissionTypeList.class);
    }

    /**
     * Returns all {@link AdmissionType} entities.
     *
     * @return all {@link AdmissionType}.
     */
    public List<AdmissionType> getAdmissionsTypes() {
        return getList("SelectAdmissionType", AdmissionTypeList.class);
    }

    /**
     * Returns all {@link Patient} entities.
     *
     * @return a list of all registered {@link Patient}s.
     */
    public List<Patient> getAllPatients() {
        return getList("SelectPatientData", PatientDataList.class);
    }

    /**
     * Adds a new {@link MedicalDevices} to EHR.
     *
     * @param medicalDevices The {@link MedicalDevices} to add.
     * @return A JSON string in case of success, null otherwise.
     */
    public String addMedicalDevices(final MedicalDevices medicalDevices) {
        return save("InsertMedicalDevices", medicalDevices);
    }

    /**
     * Returns all {@link MedicalDevices} entities.
     *
     * @return A {@link List} of {@link MedicalDevices} or null in case of an error.
     */
    public List<MedicalDevices> getAllMedicalDevices() {
        return getList("SelectMedicalDevices", MedicalDevicesList.class);
    }

    /**
     * Returns a {@link MedicalDevices} with the given id.
     *
     * @param medicalDevicesId The id of the {@link MedicalDevices} to select.
     * @return A {@link MedicalDevices} or null in case of an error.
     */
    public MedicalDevices getMedicalDevicesByMedicalDevicesId(final int medicalDevicesId) {
        String query = "{\"=\":{\"medicalDevicesId\":\"" + medicalDevicesId + "\"}}";
        return getSingle("SelectMedicalDevices", query, MedicalDevicesList.class);
    }

    /**
     * Inserts a new {@link Scheduling} to EHR.
     *
     * @param scheduling The {@link Scheduling} to insert.
     * @return A JSON String in case of success, null otherwise.
     */
    public String addScheduling(final Scheduling scheduling) {
        return save("InsertScheduling", scheduling);
    }

    /**
     * Returns all {@link Scheduling} entities.
     *
     * @return A {@link List} of {@link Scheduling} or null in case of an error.
     */
    public List<Scheduling> getAllScheduling() {
        return getList("SelectScheduling", SchedulingList.class);
    }

    /**
     * Returns the {@link Scheduling} entity with the given id.
     *
     * @param schedulingId The id of the {@link Scheduling} to get.
     * @return A {@link Scheduling} or null in case of an error.
     */
    public Scheduling getSchedulingBySchedulingId(final int schedulingId) {
        String query = "{ \"=\":{\"schedulingId\":\"" + schedulingId + "\"}}";
        return getSingle("SelectScheduling", query, SchedulingList.class);
    }

    /**
     * Returns all {@link Scheduling} entities with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link Scheduling} to get.
     * @return A {@link Scheduling} or null in case of an error.
     */
    public List<Scheduling> getSchedulingByPatientId(final String patientId) {
        String query = "{ \"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectScheduling", query, SchedulingList.class);
    }

    /**
     * Saves a new {@link PregnancyHistory} to EHR.
     *
     * @param pregnancyHistory The {@link PregnancyHistory} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addPregnancyHistory(final PregnancyHistory pregnancyHistory) {
        return save("InsertPregnancyHistory", pregnancyHistory);
    }

    /**
     * Returns all {@link PregnancyHistory} entities.
     *
     * @return A {@link List} of {@link PregnancyHistory} or null in case of an error.
     */
    public List<PregnancyHistory> getAllPregnancyHistory() {
        return getList("SelectPregnancyHistory", PregnancyHistoryList.class);
    }

    /**
     * Returns all {@link PregnancyHistory} entities with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link PregnancyHistory} to get.
     * @return A {@link List} of {@link PregnancyHistory} or null in case of an error.
     */
    public List<PregnancyHistory> getPregnancyHistoryByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPregnancyHistory", query, PregnancyHistoryList.class);
    }

    /**
     * Returns the {@link PregnancyHistory} with the given id.
     *
     * @param pregnancyId The id of the {@link PregnancyHistory} to get.
     * @return A {@link PregnancyHistory} or null in case of an error.
     */
    public PregnancyHistory getPregnancyHistoryByPregnancyId(final int pregnancyId) {
        String query = "{\"=\":{\"pregrancyId\":\"" + pregnancyId + "\"}}";
        return getSingle("SelectPregnancyHistory", query, PregnancyHistoryList.class);
    }

    /**
     * Inserts a new {@link Medication} to EHR.
     *
     * @param medication The {@link Medication} to insert.
     * @return A JSON String or null in case of an error.
     */
    public String addMedication(final Medication medication) {
        return save("InsertMedication", medication);
    }

    /**
     * Returns all {@link Medication} entities.
     *
     * @return A {@link List} of {@link Medication} or null in case of an error.
     */
    public List<Medication> getAllMedication() {
        return getList("SelectMedication", MedicationList.class);
    }

    /**
     * Returns the {@link Medication} with the given id.
     *
     * @param medicationId The id of the {@link Medication} to get.
     * @return A {@link Medication} or null in case of an error.
     */
    public Medication getMedicationByMedicationId(final int medicationId) {
        String query = "{\"=\":{\"medicationId\":\"" + medicationId + "\"}}";
        return getSingle("SelectMedication", query, MedicationList.class);
    }

    /**
     * Saves a new {@link LabAnalysis} to EHR.
     *
     * @param labAnalysis The {@link LabAnalysis} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addLabAnalysis(final LabAnalysis labAnalysis) {
        return save("InsertLabAnalysis", labAnalysis);
    }

    /**
     * Returns all {@link LabAnalysis} entities.
     *
     * @return A {@link List} of {@link LabAnalysis} or null in case of an error.
     */
    public List<LabAnalysis> getAllLabAnalysis() {
        return getList("SelectLabAnalysis", LabAnalysisList.class);
    }

    /**
     * Returns the {@link LabAnalysis} with the given id.
     *
     * @param labAnalysisId The id of the {@link LabAnalysis} to fetch.
     * @return A {@link LabAnalysis} or null in case of an error.
     */
    public LabAnalysis getLabAnalysisByLabAnalysisId(final int labAnalysisId) {
        String query = "{\"=\":{\"labAnalysisId\":\"" + labAnalysisId + "\"}}";
        return getSingle("SelectLabAnalysis", query, LabAnalysisList.class);
    }

    /**
     * Insert a new {@link Coding} to EHR.
     *
     * @param coding The {@link Coding} to insert.
     * @return A JSON String or null in case of an error.
     */
    public String addCoding(final Coding coding) {
        return save("InsertCoding", coding);
    }

    /**
     * Returns all {@link Coding} entities.
     *
     * @return A {@link List} of {@link Coding} or null in case of an error.
     */
    public List<Coding> getAllCoding() {
        return getList("SelectCoding", CodingList.class);
    }

    /**
     * Returns the {@link Coding} with the given id.
     *
     * @param codingId The id of the {@link Coding} to fetch.
     * @return A {@link Coding} or null in case of an error.
     */
    public Coding getCodingByCodingId(final int codingId) {
        String query = "{\"=\":{\"codingId\":\"" + codingId + "\"}}";
        return getSingle("SelectCoding", query, CodingList.class);
    }

    /**
     * Saves a new {@link InsuranceData} to EHR.
     *
     * @param insuranceData The {@link InsuranceData} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addInsuranceData(final InsuranceData insuranceData) {
        return save("InsertInsuranceData", insuranceData);
    }

    /**
     * Returns all {@link InsuranceData} entities.
     *
     * @return A {@link List} of {@link InsuranceData} or null in case of an error.
     */
    public List<InsuranceData> getAllInsuranceData() {
        return getList("SelectInsuranceData", InsuranceDataList.class);
    }

    /**
     * Returns the {@link InsuranceData} with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link InsuranceData} to fetch.
     * @return A {@link List} of {@link InsuranceData} or null in case of an error.
     */
    public List<InsuranceData> getInsuranceDataByPatientId(final int patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectInsuranceData", query, InsuranceDataList.class);
    }

    /**
     * Returns the {@link InsuranceData} with the given id.
     *
     * @param insuranceId The id of the {@link InsuranceData} to fetch.
     * @return An {@link InsuranceData} or null in case of an error.
     */
    public InsuranceData getInsuranceDataByInsuranceId(final int insuranceId) {
        String query = "{\"=\":{\"insuranceId\":\"" + insuranceId + "\"}}";
        return getSingle("SelectInsuranceData", query, InsuranceDataList.class);
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
        return getList("SelectPatientMedicalDevices", PatientMedicalDevicesList.class);
    }

    /**
     * Returns the {@link PatientMedicalDevices} with the given serial number.
     *
     * @param patientMedicalDeviceSn The S/N of the {@link PatientMedicalDevices} to fetch.
     * @return A {@link PatientMedicalDevices} or null in case of an error.
     */
    public PatientMedicalDevices getPatientMedicalDevicesByPatientMedicalDeviceSn(final String patientMedicalDeviceSn) {
        String query = "{\"=\":{\"patientMedicalDeviceSn\":\"" + patientMedicalDeviceSn + "\"}}";
        return getSingle("SelectPatientMedicalDevices", query, PatientMedicalDevicesList.class);
    }

    /**
     * Returns all {@link PatientMedicalDevices} with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link PatientMedicalDevices} to fetch.
     * @return A {@link List} of {@link PatientMedicalDevices} or null in case of an error.
     */
    public List<PatientMedicalDevices> getPatientMedicalDevicesByPatientId(final int patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPatientMedicalDevices", query, PatientMedicalDevicesList.class);
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
