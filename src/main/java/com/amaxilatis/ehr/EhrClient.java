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
import java.util.ArrayList;
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
    private final ArrayList<Integer> successCodes;
    private final String connectionUrl;
    private final ObjectMapper objectMapper;

    /**
     * <p>Prints an error message to {@link #LOGGER}.</p>
     *
     * @param message The error message to print.
     * @param error The {@link Throwable} that cause the error.
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
     * <p>Prints an error message to {@link #LOGGER}.</p>
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
        successCodes = new ArrayList<Integer>();
        successCodes.add(200);
        successCodes.add(201);
        successCodes.add(202);
        successCodes.add(204);
        this.connectionUrl = connectionUrl;
        objectMapper = new ObjectMapper();
    }

    /**
     * Add the {@see Patient} to the EHR.
     *
     * @param patient the {@see Patient} to add.
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
     * Add the {@see AdmissionData} to the EHR.
     *
     * @param admissionData the {@see AdmissionData} to add.
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
     * Update the {@see Patient} to the EHR.
     *
     * @param patient the {@see Patient} to update.
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
     * Retrieve a {@see Patient} from the EHR service.
     *
     * @param patientId the id of the {@see Patient} to search for.
     * @return the {@see Patient} requested.
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
            e.printStackTrace();
            LOGGER.error(resp);
            return null;
        }
    }

    /**
     * Retrieve all {@see Allergies} of the {@see Patient}.
     *
     * @param patientId the id of the {@see Patient} to search for.
     * @return a List of {@see Allergies} of the {@see Patient}.
     */
    public List<Allergies> getAllergiesByPatientId(final String patientId) {
        final String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        LOGGER.info(query);
        String resp = null;
        try {
            resp = postPath("SelectAllergies", query);
            LOGGER.info(resp);
            AllergiesDataList list = objectMapper.readValue(resp, AllergiesDataList.class);
            return list.getAllergy();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(resp);
            return null;
        }
    }

    /**
     * Retrieve all {@see Admissions} of the {@see Patient}.
     *
     * @param patientId the id of the {@see Patient} to search for.
     * @return a List of {@see Admissions} of the {@see Patient}.
     */
    public List<AdmissionData> getAdmissionsByPatientId(final String patientId) {
        final String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        LOGGER.info(query);
        String resp = null;
        try {
            resp = postPath("SelectAdmissionData", query);
            LOGGER.info(resp);
            AdmissionDataList list = objectMapper.readValue(resp, AdmissionDataList.class);
            return list.getAdmissionData();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(resp);
            return null;
        }
    }

    /**
     * <p>Gets an {@link AdmissionData} by its admissionId.</p>
     *
     * @param admissionId The id of the {@link AdmissionData} to fetch.
     * @return An {@link AdmissionData} or null in case of an error.
     */
    public AdmissionData getAdmissionByAdmissionId(final long admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
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
     * <p>Adds a new {@link AdmissionType} to EHR.</p>
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
     * <p>Gets an {@link AdmissionType} by its id.</p>
     *
     * @param admissionTypeId The id of the {@link AdmissionType} to select.
     * @return An {@link AdmissionType} or null in case of an error.
     */
    public AdmissionType getAdmissionTypeByAdmissionTypeId(final int admissionTypeId) {
        String query = "{\"=\":{\"admissionTypeId\":\"" + admissionTypeId + "\"}}";
        try {
            String response = postPath("SelectAdmissionType", query);
            return objectMapper.readValue(response, AdmissionTypeList.class).getAdmissionType().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while selecting AdmissionType: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * List all registered {@see AdmissionType}s.
     *
     * @return a list of all registered {@see AdmissionType}s.
     */
    public List<AdmissionType> getAdmissionsTypes() {
        final String query = "{}";
        LOGGER.info(query);
        String resp = null;
        try {
            resp = postPath("SelectAdmissionType", query);
            LOGGER.info(resp);
            AdmissionTypeList list = objectMapper.readValue(resp, AdmissionTypeList.class);
            return list.getAdmissionType();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(resp);
            return null;
        }
    }

    /**
     * List all registered {@see Patient}s.
     *
     * @return a list of all registered {@see Patient}s.
     */
    public List<Patient> getAllPatients() {
        final String query = "{}";
        LOGGER.info(query);
        String resp = null;
        try {
            resp = postPath("SelectPatientData", query);
            LOGGER.info(resp);
            PatientDataList list = objectMapper.readValue(resp, PatientDataList.class);
            return list.getPatientData();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(resp);
            return null;
        }
    }

    /**
     * <p>Adds a new {@link MedicalDevices} to EHR.</p>
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
     * <p>Gets all the {@link MedicalDevices} saved in EHR.</p>
     *
     * @return A {@link List} of {@link MedicalDevices} or null in case of an error.
     */
    public List<MedicalDevices> getAllMedicalDevices() {
        String query = "{}";
        try {
            String response = postPath("SelectMedicalDevices", query);
            return objectMapper.readValue(response, MedicalDevicesList.class).getMedicalDevices();

        } catch (Exception error) {
            LOGGER.error("Error while querying MedicalDevices: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Gets a {@link MedicalDevices} by its id.</p>
     *
     * @param medicalDevicesId The id of the {@link MedicalDevices} to select.
     * @return A {@link MedicalDevices} or null in case of an error.
     */
    public MedicalDevices getMedicalDevicesByMedicalDevicesId(final int medicalDevicesId) {
        String query = "{\"=\":{\"medicalDevicesId\":\"" + medicalDevicesId + "\"}}";
        try {
            String response = postPath("SelectMedicalDevices", query);
            return objectMapper.readValue(response, MedicalDevicesList.class).getMedicalDevices().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while selecting MedicalDevices by id: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Inserts a new {@link Scheduling} to EHR.</p>
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
     * <p>Gets all the {@link Scheduling} saved in EHR.</p>
     *
     * @return A {@link List} of {@link Scheduling} or null in case of an error.
     */
    public List<Scheduling> getAllScheduling() {
        String query = "{}";
        try {
            String response = postPath("SelectScheduling", query);
            return objectMapper.readValue(response, SchedulingList.class).getScheduling();
        } catch (Exception error) {
            LOGGER.error("Error getting all Scheduling: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Gets a {@link Scheduling} by its schedulingId.</p>
     *
     * @param schedulingId The id of the {@link Scheduling} to get.
     * @return A {@link Scheduling} or null in case of an error.
     */
    public Scheduling getSchedulingBySchedulingId(final int schedulingId) {
        String query = "{ \"=\":{\"schedulingId\":\"" + schedulingId +"\"}}";
        try {
            String response = postPath("SelectScheduling", query);
            return objectMapper.readValue(response, SchedulingList.class).getScheduling().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting Scheduling by id: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Gets a {@link Scheduling} by its patientId.</p>
     *
     * @param patientId The id of the {@link Patient} whose {@link Scheduling} to get.
     * @return A {@link Scheduling} or null in case of an error.
     */
    public List<Scheduling> getSchedulingByPatientId(final String patientId) {
        String query = "{ \"=\":{\"patientId\":\"" + patientId +"\"}}";
        SchedulingList schedulingList = getAll("SelectScheduling", query, SchedulingList.class);
        if (schedulingList != null) {
            return schedulingList.getScheduling();
        }
        return null;
    }

    /**
     * <p>Saves a new {@link PregnancyHistory} to EHR.</p>
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
     * <p>Gets all the {@link PregnancyHistory} saved in EHR.</p>
     *
     * @return A {@link List} of {@link PregnancyHistory} or null in case of an error.
     */
    public List<PregnancyHistory> getAllPregnancyHistory() {
        String query = "{}";
        try {
            String response = postPath("SelectPregnancyHistory", query);
            return objectMapper.readValue(response, PregnancyHistoryList.class).getPregnancyHistory();
        } catch (Exception error) {
            LOGGER.error("Error while getting all PregnancyHistory: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Gets all the {@link PregnancyHistory} for a given patient Id.</p>
     *
     * @param patientId The id of the {@link Patient} whose {@link PregnancyHistory} to get.
     * @return A {@link List} of {@link PregnancyHistory} or null in case of an error.
     */
    public List<PregnancyHistory> getPregnancyHistoryByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        try {
            String response = postPath("SelectPregnancyHistory", query);
            return objectMapper.readValue(response, PregnancyHistoryList.class).getPregnancyHistory();
        } catch (Exception error) {
            LOGGER.error("Error while gettint PregnancyHistory for patient: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Gets a {@link PregnancyHistory} by its id.</p>
     *
     * @param pregnancyId The id of the {@link PregnancyHistory} to get.
     * @return A {@link PregnancyHistory} or null in case of an error.
     */
    public PregnancyHistory getPregnancyHistoryByPregnancyId(final int pregnancyId) {
        String query = "{\"=\":{\"pregrancyId\":\"" + pregnancyId + "\"}}";
        try {
            String response = postPath("SelectPregnancyHistory", query);
            return objectMapper.readValue(response, PregnancyHistoryList.class).getPregnancyHistory().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting PregnancyHistory by id: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Inserts a new {@link Medication} to EHR.</p>
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
     * <p>Gets all the {@link Medication} saved in EHR.</p>
     *
     * @return A {@link List} of {@link Medication} or null in case of an error.
     */
    public List<Medication> getAllMedication() {
        String query = "{}";
        try {
            String response = postPath("SelectMedication", query);
            return objectMapper.readValue(response, MedicationList.class).getMedication();
        } catch (Exception error) {
            LOGGER.error("Error while getting all Medication: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Gets a {@link Medication} by its id.</p>
     *
     * @param medicationId The id of the {@link Medication} to get.
     * @return A {@link Medication} or null in case of an error.
     */
    public Medication getMedicationByMedicationId(final int medicationId) {
        String query = "{\"=\":{\"medicationId\":\"" + medicationId + "\"}}";
        try {
            String response = postPath("SelectMedication", query);
            return objectMapper.readValue(response, MedicationList.class).getMedication().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting Medication by id: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Saves a new {@link LabAnalysis} to EHR.</p>
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
     * <p>Gets all the {@link LabAnalysis} saved in EHR.</p>
     * @return A {@link List} of {@link LabAnalysis} or null in case of an error.
     */
    public List<LabAnalysis> getAllLabAnalysis() {
        try {
            String response = postPath("SelectLabAnalysis", "{}");
            return objectMapper.readValue(response, LabAnalysisList.class).getLabAnalysis();
        } catch (Exception error) {
            LOGGER.error("Error while getting all LabAnalysis: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Gets a {@link LabAnalysis} by its id.</p>
     *
     * @param labAnalysisId The id of the {@link LabAnalysis} to fetch.
     * @return A {@link LabAnalysis} or null in case of an error.
     */
    public LabAnalysis getLabAnalysisByLabAnalysisId(final int labAnalysisId) {
        String query = "{\"=\":{\"labAnalysisId\":\"" + labAnalysisId + "\"}}";
        try {
            String response = postPath("SelectLabAnalysis", query);
            return objectMapper.readValue(response, LabAnalysisList.class).getLabAnalysis().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting LabAnalysis with id " + labAnalysisId + ": " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Insert a new {@link Coding} to EHR.</p>
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
     * <p>Gets all the {@link Coding} saved in EHR.</p>
     *
     * @return A {@link List} of {@link Coding} or null in case of an error.
     */
    public List<Coding> getAllCoding() {
        try {
            String response = postPath("SelectCoding", "{}");
            return objectMapper.readValue(response, CodingList.class).getCoding();
        } catch (Exception error) {
            LOGGER.error("Error while getting all Coding: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Gets a {@link Coding} with the given id.</p>
     *
     * @param codingId The id of the {@link Coding} to fetch.
     * @return A {@link Coding} or null in case of an error.
     */
    public Coding getCodingByCodingId(final int codingId) {
        String query = "{\"=\":{\"codingId\":\"" + codingId + "\"}}";
        try {
            String response = postPath("SelectCoding", query);
            return objectMapper.readValue(response, CodingList.class).getCoding().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting Coding with id " + codingId + ": " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Saves a new {@link InsuranceData} to EHR.</p>
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
     * <p>Gets all the {@link InsuranceData} saved in EHR.</p>
     *
     * @return A {@link List} of {@link InsuranceData} or null in case of an error.
     */
    public List<InsuranceData> getAllInsuranceData() {
        try {
            String response = postPath("SelectInsuranceData", "{}");
            return objectMapper.readValue(response, InsuranceDataList.class).getInsuranceData();
        } catch (Exception error) {
            LOGGER.error("Error while getting all InsuranceData: " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Gets the {@link InsuranceData} with the given id.</p>
     *
     * @param insuranceId The id of the {@link InsuranceData} to fetch.
     * @return An {@link InsuranceData} or null in case of an error.
     */
    public InsuranceData getInsuranceDataByInsuranceId(final int insuranceId) {
        String query = "{\"=\":{\"insuranceId\":\"" + insuranceId + "\"}}";
        try {
            String response = postPath("SelectInsuranceData", query);
            return objectMapper.readValue(response, InsuranceDataList.class).getInsuranceData().get(0);
        } catch (Exception error) {
            LOGGER.error("Error while getting InsuranceData with id " + insuranceId + ": " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Gets the {@link InsuranceData} for a patient using his id.</p>
     *
     * @param patientId The id of the {@link Patient} whose {@link InsuranceData} to fetch.
     * @return A {@link List} of {@link InsuranceData} or null in case of an error.
     */
    public List<InsuranceData> getInsuranceDataByPatientId(final int patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        try {
            String response = postPath("SelectInsuranceData", query);
            return objectMapper.readValue(response, InsuranceDataList.class).getInsuranceData();
        } catch (Exception error) {
            LOGGER.error("Error while getting InsuranceData with patientId " + patientId + ": " + error.getMessage(), error);
            return null;
        }
    }

    /**
     * <p>Add a new {@link PatientMedicalDevices} to EHR.</p>
     *
     * @param patientMedicalDevices The {@link PatientMedicalDevices} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addPatientMedicalDevices(final PatientMedicalDevices patientMedicalDevices) {
        return save("InsertPatientMedicalDevices", patientMedicalDevices);
    }

    /**
     * <p>Gets all the {@link PatientMedicalDevices} saved in EHR.</p>
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
     * <p>Gets a {@link PatientMedicalDevices} by its S/N</p>
     *
     * @param patientMedicalDeviceSn The S/N of the {@link PatientMedicalDevices} to fetch.
     * @return A {@link PatientMedicalDevices} or null in case of an error.
     */
    public PatientMedicalDevices getPatientMedicalDevicesByPatientMedicalDeviceSn(final String patientMedicalDeviceSn) {
        String query = "{\"=\":{\"patientMedicalDeviceSn\":\"" + patientMedicalDeviceSn + "\"}}";
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
     * <p>Gets all the {@link PatientMedicalDevices} associated with a {@link Patient}.</p>
     *
     * @param patientId The id of the {@link Patient} whose {@link PatientMedicalDevices} to fetch.
     * @return A {@link List} of {@link PatientMedicalDevices} or null in case of an error.
     */
    public List<PatientMedicalDevices> getPatientMedicalDevicesByPatientId(final int patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        PatientMedicalDevicesList patientMedicalDevicesList = getAll("SelectPatientMedicalDevices", query, PatientMedicalDevicesList.class);
        if (patientMedicalDevicesList != null) {
            return patientMedicalDevicesList.getPatientMedicalDevices();
        }

        return null;
    }

    /**
     * <p>Saves a new {@link Diagnosis} to EHR.</p>
     *
     * @param diagnosis The {@link Diagnosis} to save.
     * @return A JSON String or null in case of an error.
     */
    public String addDiagnosis(final Diagnosis diagnosis) {
        return save("InsertDiagnosis", diagnosis);
    }

    /**
     * <p>Gets all the {@link Diagnosis} saved in EHR.</p>
     *
     * @return A {@link List} of {@link Diagnosis} or null in case of an error.
     */
    public List<Diagnosis> getAllDiagnosis() {
        DiagnosisList diagnosisList = getAll("SelectDiagnosis", DiagnosisList.class);
        if (diagnosisList != null) {
            return diagnosisList.getDiagnosis();
        }
        return null;
    }

    /**
     * <p>Gets a {@link Diagnosis} by its id.</p>
     *
     * @param diagnosisId The id of the {@link Diagnosis} to fetch.
     * @return A {@link Diagnosis} or null in case of an error.
     */
    public Diagnosis getDiagnosisByDiagnosisId(final long diagnosisId) {
        String query = "{\"=\":{\"diagnosisId\":\"" + diagnosisId + "\"}}";
        DiagnosisList diagnosisList = getAll("SelectDiagnosis", query, DiagnosisList.class);
        if (diagnosisList != null) {
            List<Diagnosis> diagnoses = diagnosisList.getDiagnosis();
            if (diagnoses != null && diagnoses.size() > 0) {
                return diagnoses.get(0);
            }
        }
        return null;
    }

    /**
     * <p>Gets the {@link Diagnosis} associated with an {@link AdmissionData}.</p>
     *
     * @param admissionId The id of the {@link AdmissionData} for which to fetch the {@link Diagnosis}.
     * @return A {@link List} of {@link Diagnosis} or null.
     */
    public List<Diagnosis> getDiagnosisByAdmissionId(final long admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        DiagnosisList diagnosisList = getAll("SelectDiagnosis", query, DiagnosisList.class);
        if (diagnosisList != null) {
            return diagnosisList.getDiagnosis();
        }
        return null;
    }

    /**
     * <p>Gets all the {@link Diagnosis} associated with a {@link Patient}.</p>
     *
     * @param patientId The id of the {@link Patient} whose {@link Diagnosis} to fetch.
     * @return A {@link List} of {@link Diagnosis} or null in case of an error.
     */
    public List<Diagnosis> getDiagnosisByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        DiagnosisList diagnosisList = getAll("SelectDiagnosis", query, DiagnosisList.class);
        if (diagnosisList != null) {
            return diagnosisList.getDiagnosis();
        }
        return null;
    }

    /**
     * <p>Generic metehod for saving entities to EHR.</p>
     *
     * @param path The path where to save the entity.
     * @param entity The entity to save.
     * @param <A> The type of the entity to save.
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
     * <p>Generic methods to get all the entities in the given path that match the given query.</p>
     * @param path The path to query.
     * @param query The query to run.
     * @param theClass The class to convert the reponse to.
     * @param <A> The type of the response.
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
     * <p>Generic methods to get all the entities in the given path.</p>
     * @param path The path to query.
     * @param theClass The class to convert the reponse to.
     * @param <A> The type of the response.
     * @return An instance of A or null in case of an error.
     */
    private <A> A getAll(final String path, final Class<A> theClass) {
        return getAll(path, "{}", theClass);
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
