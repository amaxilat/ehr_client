package com.amaxilatis.ehr;

import com.amaxilatis.ehr.exception.EhrClientException;
import com.amaxilatis.ehr.model.*;
import com.amaxilatis.ehr.model.list.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * A client to interact with an EHR server.
 *
 * @author Dimitrios Amaxilatis.
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
public class EhrClient {
    private static final Logger LOGGER = Logger.getLogger(EhrClient.class);
    private static final String QUERY_ALL = "{}";
    private static final String EMPTY_JSON_ARRAY = "{}";
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
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addPatient(final Patient patient) {
        return save("InsertPatientData", patient);
    }

    /**
     * Add the {@link AdmissionData} to the EHR.
     *
     * @param admissionData the {@link AdmissionData} to add.
     * @return The id of the newly created {@link AdmissionData}.
     * @throws EhrClientException in case of an error.
     */
    public String addAdmissionData(final AdmissionData admissionData) {
        try {
            AdmissionData.AdmissionDataId admissionDataId =
                    objectMapper.readValue(save("InsertAdmissionData", admissionData), AdmissionData.AdmissionDataId.class);
            return admissionDataId.getAdmissionId();
        } catch (Exception error) {
            throw new EhrClientException("InsertAdmissionData", admissionData, error);
        }
    }

    /**
     * Updates an existing {@link AdmissionData} in EHR.
     *
     * @param admissionData The {@link AdmissionData} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateAdmissionData(final AdmissionData admissionData) {
        return save("UpdateAdmissionData", admissionData);
    }

    /**
     * Update the {@link Patient} to the EHR.
     *
     * @param patient the {@link Patient} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updatePatient(final Patient patient) {
        return postPath("UpdatePatientData", patient);
    }

    /**
     * Returns the {@link Patient} from the EHR service with the given id.
     *
     * @param patientId the id of the {@link Patient} to search for.
     * @return the {@link Patient} requested or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public Patient getPatientByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getSingle("SelectPatientData", query, PatientDataList.class);
    }

    /**
     * Adds a new {@link Allergies} to EHR.
     *
     * @param allergies The {@link Allergies} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addAllergy(final Allergies allergies) {
        return save("InsertAllergies", allergies);
    }

    /**
     * Updates an {@link Allergies} in EHR.
     *
     * @param allergy The {@link Allergies} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateAllergy(final Allergies allergy) {
        return save("UpdateAllergies", allergy);
    }

    /**
     * Returns all {@link Allergies} with the given {@link Patient} id.
     *
     * @param patientId the id of the {@link Patient} to search for.
     * @return a List of {@link Allergies} of the {@link Patient} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<Allergies> getAllergiesByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectAllergies", query, AllergiesDataList.class);
    }

    /**
     * Gets all the {@link Allergies} for the given {@link Patient} and {@link AdmissionData} ids.
     *
     * @param patientId   The id of the {@link Patient}.
     * @param admissionId The id of the {@link AdmissionData}.
     * @return The {@link Allergies} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<Allergies> getAllertiesByPatientAndAdmissionId(final String patientId,
                                                               final String admissionId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\", \"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectAllergies", query, AllergiesDataList.class);
    }

    /**
     * Returns all {@link AdmissionData} with the given {@link Patient} id.
     *
     * @param patientId the id of the {@link Patient} to search for.
     * @return a List of {@link AdmissionData} of the {@link Patient} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<AdmissionData> getAdmissionsByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectAdmissionData", query, AdmissionDataList.class);
    }

    /**
     * Returns the {@link AdmissionData} with this id.
     *
     * @param admissionId The id of the {@link AdmissionData} to fetch.
     * @return An {@link AdmissionData} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public AdmissionData getAdmissionByAdmissionId(final String admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        return getSingle("SelectAdmissionData", query, AdmissionDataList.class);
    }

    /**
     * Adds a new {@link AdmissionType} to EHR.</p>
     *
     * @param admissionType The {@link AdmissionType} to insert.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String insertAdmissionType(final AdmissionType admissionType) {
        return save("InsertAdmissionType", admissionType);
    }

    /**
     * Updates an {@link AdmissionType} saved in EHR.
     *
     * @param admissionType The {@link AdmissionType} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateAdmissionType(final AdmissionType admissionType) {
        return save("UpdateAdmissionType", admissionType);
    }

    /**
     * Returns the {@link AdmissionType} with the give type id.
     *
     * @param admissionTypeId The id of the {@link AdmissionType} to select.
     * @return An {@link AdmissionType} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public AdmissionType getAdmissionTypeByAdmissionTypeId(final int admissionTypeId) {
        String query = "{\"=\":{\"admissionTypeId\":\"" + admissionTypeId + "\"}}";
        return getSingle("SelectAdmissionType", query, AdmissionTypeList.class);
    }

    /**
     * Returns all {@link AdmissionType} entities.
     *
     * @return all {@link AdmissionType} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<AdmissionType> getAdmissionsTypes() {
        return getList("SelectAdmissionType", AdmissionTypeList.class);
    }

    /**
     * Returns all {@link Patient} entities.
     *
     * @return a list of all registered {@link Patient}s or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<Patient> getAllPatients() {
        return getList("SelectPatientData", PatientDataList.class);
    }

    /**
     * Adds a new {@link MedicalDevices} to EHR.
     *
     * @param medicalDevices The {@link MedicalDevices} to add.
     * @return A JSON string.
     * @throws EhrClientException in case of an error.
     */
    public String addMedicalDevices(final MedicalDevices medicalDevices) {
        return save("InsertMedicalDevices", medicalDevices);
    }

    /**
     * Updates a {@link MedicalDevices} saved in EHR.
     *
     * @param medicalDevice The {@link MedicalDevices} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateMedicalDevices(final MedicalDevices medicalDevice) {
        return save("UpdateMedicalDevices", medicalDevice);
    }

    /**
     * Returns all {@link MedicalDevices} entities.
     *
     * @return A {@link List} of {@link MedicalDevices} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<MedicalDevices> getAllMedicalDevices() {
        return getList("SelectMedicalDevices", MedicalDevicesList.class);
    }

    /**
     * Returns a {@link MedicalDevices} with the given id.
     *
     * @param medicalDevicesId The id of the {@link MedicalDevices} to select.
     * @return A {@link MedicalDevices} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public MedicalDevices getMedicalDevicesByMedicalDevicesId(final int medicalDevicesId) {
        String query = "{\"=\":{\"medicalDevicesId\":\"" + medicalDevicesId + "\"}}";
        return getSingle("SelectMedicalDevices", query, MedicalDevicesList.class);
    }

    /**
     * Inserts a new {@link Scheduling} to EHR.
     *
     * @param scheduling The {@link Scheduling} to insert.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addScheduling(final Scheduling scheduling) {
        return save("InsertScheduling", scheduling);
    }

    /**
     * Updates a {@link Scheduling} saved in EHR.
     *
     * @param scheduling The {@link Scheduling} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateScheduling(final Scheduling scheduling) {
        return save("UpdateScheduling", scheduling);
    }

    /**
     * Returns all {@link Scheduling} entities.
     *
     * @return A {@link List} of {@link Scheduling} or null in case no match.
     * @throws EhrClientException in case of an error.
     */
    public List<Scheduling> getAllScheduling() {
        return getList("SelectScheduling", SchedulingList.class);
    }

    /**
     * Returns the {@link Scheduling} entity with the given id.
     *
     * @param schedulingId The id of the {@link Scheduling} to get.
     * @return A {@link Scheduling} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public Scheduling getSchedulingBySchedulingId(final int schedulingId) {
        String query = "{ \"=\":{\"schedulingId\":\"" + schedulingId + "\"}}";
        return getSingle("SelectScheduling", query, SchedulingList.class);
    }

    /**
     * Returns all {@link Scheduling} entities with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link Scheduling} to get.
     * @return A {@link Scheduling} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<Scheduling> getSchedulingByPatientId(final String patientId) {
        String query = "{ \"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectScheduling", query, SchedulingList.class);
    }

    /**
     * Saves a new {@link PregnancyHistory} to EHR.
     *
     * @param pregnancyHistory The {@link PregnancyHistory} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addPregnancyHistory(final PregnancyHistory pregnancyHistory) {
        return save("InsertPregnancyHistory", pregnancyHistory);
    }

    /**
     * Updates a {@link PregnancyHistory} saved in EHR.
     *
     * @param pregnancyHistory The {@link PregnancyHistory} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updatePregnancyHistory(final PregnancyHistory pregnancyHistory) {
        return save("UpdatePregnancyHistory", pregnancyHistory);
    }

    /**
     * Returns all {@link PregnancyHistory} entities.
     *
     * @return A {@link List} of {@link PregnancyHistory} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PregnancyHistory> getAllPregnancyHistory() {
        return getList("SelectPregnancyHistory", PregnancyHistoryList.class);
    }

    /**
     * Returns all {@link PregnancyHistory} entities with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link PregnancyHistory} to get.
     * @return A {@link List} of {@link PregnancyHistory} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PregnancyHistory> getPregnancyHistoryByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPregnancyHistory", query, PregnancyHistoryList.class);
    }

    /**
     * Returns the {@link PregnancyHistory} with the given id.
     *
     * @param pregnancyId The id of the {@link PregnancyHistory} to get.
     * @return A {@link PregnancyHistory} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public PregnancyHistory getPregnancyHistoryByPregnancyId(final int pregnancyId) {
        String query = "{\"=\":{\"pregrancyId\":\"" + pregnancyId + "\"}}";
        return getSingle("SelectPregnancyHistory", query, PregnancyHistoryList.class);
    }

    /**
     * Inserts a new {@link Medication} to EHR.
     *
     * @param medication The {@link Medication} to insert.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addMedication(final Medication medication) {
        return save("InsertMedication", medication);
    }

    /**
     * Updates a {@link Medication} saved in EHR.
     *
     * @param medication The {@link Medication} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateMedication(final Medication medication) {
        return save("UpdateMedication", medication);
    }

    /**
     * Returns all {@link Medication} entities.
     *
     * @return A {@link List} of {@link Medication} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<Medication> getAllMedication() {
        return getList("SelectMedication", MedicationList.class);
    }

    /**
     * Returns the {@link Medication} with the given id.
     *
     * @param medicationId The id of the {@link Medication} to get.
     * @return A {@link Medication} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public Medication getMedicationByMedicationId(final int medicationId) {
        String query = "{\"=\":{\"medicationId\":\"" + medicationId + "\"}}";
        return getSingle("SelectMedication", query, MedicationList.class);
    }

    /**
     * Saves a new {@link LabAnalysis} to EHR.
     *
     * @param labAnalysis The {@link LabAnalysis} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addLabAnalysis(final LabAnalysis labAnalysis) {
        return save("InsertLabAnalysis", labAnalysis);
    }

    /**
     * Updates a {@link LabAnalysis} saved in EHR.
     *
     * @param labAnalysis The {@link LabAnalysis} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateLabAnalysis(final LabAnalysis labAnalysis) {
        return save("UpdateLabAnalysis", labAnalysis);
    }

    /**
     * Returns all {@link LabAnalysis} entities.
     *
     * @return A {@link List} of {@link LabAnalysis} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<LabAnalysis> getAllLabAnalysis() {
        return getList("SelectLabAnalysis", LabAnalysisList.class);
    }

    /**
     * Returns the {@link LabAnalysis} with the given id.
     *
     * @param labAnalysisId The id of the {@link LabAnalysis} to fetch.
     * @return A {@link LabAnalysis} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public LabAnalysis getLabAnalysisByLabAnalysisId(final int labAnalysisId) {
        String query = "{\"=\":{\"labAnalysisId\":\"" + labAnalysisId + "\"}}";
        return getSingle("SelectLabAnalysis", query, LabAnalysisList.class);
    }

    /**
     * Insert a new {@link Coding} to EHR.
     *
     * @param coding The {@link Coding} to insert.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addCoding(final Coding coding) {
        return save("InsertCoding", coding);
    }

    /**
     * Updates an already saved {@link Coding} in EHR.
     *
     * @param coding The {@link Coding} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateCoding(final Coding coding) {
        return save("UpdateCoding", coding);
    }

    /**
     * Returns all {@link Coding} entities.
     *
     * @return A {@link List} of {@link Coding} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<Coding> getAllCoding() {
        return getList("SelectCoding", CodingList.class);
    }

    /**
     * Returns the {@link Coding} with the given id.
     *
     * @param codingId The id of the {@link Coding} to fetch.
     * @return A {@link Coding} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public Coding getCodingByCodingId(final String codingId) {
        String query = "{\"=\":{\"codingId\":\"" + codingId + "\"}}";
        return getSingle("SelectCoding", query, CodingList.class);
    }

    /**
     * Saves a new {@link InsuranceData} to EHR.
     *
     * @param insuranceData The {@link InsuranceData} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addInsuranceData(final InsuranceData insuranceData) {
        return save("InsertInsuranceData", insuranceData);
    }

    /**
     * Returns all {@link InsuranceData} entities.
     *
     * @return A {@link List} of {@link InsuranceData} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<InsuranceData> getAllInsuranceData() {
        return getList("SelectInsuranceData", InsuranceDataList.class);
    }

    /**
     * Returns the {@link InsuranceData} with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link InsuranceData} to fetch.
     * @return A {@link List} of {@link InsuranceData} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<InsuranceData> getInsuranceDataByPatientId(final int patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectInsuranceData", query, InsuranceDataList.class);
    }

    /**
     * Returns the {@link InsuranceData} with the given id.
     *
     * @param insuranceId The id of the {@link InsuranceData} to fetch.
     * @return An {@link InsuranceData} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public InsuranceData getInsuranceDataByInsuranceId(final int insuranceId) {
        String query = "{\"=\":{\"insuranceId\":\"" + insuranceId + "\"}}";
        return getSingle("SelectInsuranceData", query, InsuranceDataList.class);
    }


    /**
     * Add a new {@link PatientMedicalDevices} to EHR.
     *
     * @param patientMedicalDevices The {@link PatientMedicalDevices} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addPatientMedicalDevices(final PatientMedicalDevices patientMedicalDevices) {
        return save("InsertPatientMedicalDevices", patientMedicalDevices);
    }

    /**
     * Updates an existing {@link PatientMedicalDevices} in EHR.
     *
     * @param patientMedicalDevices The {@link PatientMedicalDevices} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updatePatientMedicalDevices(final PatientMedicalDevices patientMedicalDevices) {
        return save("UpdatePatientMedicalDevices", patientMedicalDevices);
    }

    /**
     * Returns all {@link PatientMedicalDevices} entities.
     *
     * @return A {@link List} of {@link PatientMedicalDevices} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientMedicalDevices> getAllPatientMedicalDevices() {
        return getList("SelectPatientMedicalDevices", PatientMedicalDevicesList.class);
    }

    /**
     * Returns the {@link PatientMedicalDevices} with the given serial number.
     *
     * @param patientMedicalDeviceSn The S/N of the {@link PatientMedicalDevices} to fetch.
     * @return A {@link PatientMedicalDevices} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public PatientMedicalDevices getPatientMedicalDevicesByPatientMedicalDeviceSn(final String patientMedicalDeviceSn) {
        String query = "{\"=\":{\"patientMedicalDeviceSn\":\"" + patientMedicalDeviceSn + "\"}}";
        return getSingle("SelectPatientMedicalDevices", query, PatientMedicalDevicesList.class);
    }

    /**
     * Returns all {@link PatientMedicalDevices} with the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link PatientMedicalDevices} to fetch.
     * @return A {@link List} of {@link PatientMedicalDevices} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientMedicalDevices> getPatientMedicalDevicesByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPatientMedicalDevices", query, PatientMedicalDevicesList.class);
    }

    /**
     * Returns all {@link PatientMedicalDevices} with the given {@link AdmissionData} id.
     *
     * @param admissionId The id of the {@link AdmissionData} whose {@link PatientMedicalDevices} to fetch.
     * @return A {@link List} of {@link PatientMedicalDevices} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientMedicalDevices> getPatientMedicalDeviceByAdmissionId(final String admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectPatientMedicalDevices", query, PatientMedicalDevicesList.class);
    }

    /**
     * Returns all {@link PatientMedicalDevices} with the given {@link AdmissionData} and {@link Patient} ids.
     *
     * @param admissionId The id of the {@link AdmissionData} whose {@link PatientMedicalDevices} to fetch.
     * @param patientId   The id of the {@link Patient} whose {@link PatientMedicalDevices} to fetch.
     * @return A {@link List} of {@link PatientMedicalDevices} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientMedicalDevices> getPatientMedicalDevicesByAdmissionIdAndPatientId(final String admissionId,
                                                                                         final String patientId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\",\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPatientMedicalDevices", query, PatientMedicalDevicesList.class);
    }

    /**
     * Saves a new {@link Diagnosis} to EHR.
     *
     * @param diagnosis The {@link Diagnosis} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addDiagnosis(final Diagnosis diagnosis) {
        return save("InsertDiagnosis", diagnosis);
    }

    /**
     * Updates an existing {@link Diagnosis} saved in EHR.
     *
     * @param diagnosis The {@link Diagnosis} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateDiagnosis(final Diagnosis diagnosis) {
        return save("UpdateDiagnosis", diagnosis);
    }

    /**
     * Returns all {@link Diagnosis} entities.
     *
     * @return A {@link List} of {@link Diagnosis} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<Diagnosis> getAllDiagnosis() {
        return getList("SelectDiagnosis", DiagnosisList.class);
    }

    /**
     * Returns the {@link Diagnosis} with the given id.
     *
     * @param diagnosisId The id of the {@link Diagnosis} to fetch.
     * @return A {@link Diagnosis} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public Diagnosis getDiagnosisByDiagnosisId(final String diagnosisId) {
        String query = "{\"=\":{\"diagnosisId\":\"" + diagnosisId + "\"}}";
        return getSingle("SelectDiagnosis", query, DiagnosisList.class);
    }

    /**
     * Returns all {@link Diagnosis} with the given {@link AdmissionData} id.
     *
     * @param admissionId The id of the {@link AdmissionData} for which to fetch the {@link Diagnosis}.
     * @return A {@link List} of {@link Diagnosis} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<Diagnosis> getDiagnosisByAdmissionId(final String admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectDiagnosis", query, DiagnosisList.class);
    }

    /**
     * Returns all {@link Diagnosis} withe the given {@link Patient} id.
     *
     * @param patientId The id of the {@link Patient} whose {@link Diagnosis} to fetch.
     * @return A {@link List} of {@link Diagnosis} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<Diagnosis> getDiagnosisByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectDiagnosis", query, DiagnosisList.class);
    }

    /**
     * Saves a new {@link PatientMedication} to EHR.
     *
     * @param patientMedication The {@link PatientMedication} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addPatientMedication(final PatientMedication patientMedication) {
        return save("InsertPatientMedication", patientMedication);
    }

    /**
     * Updates an existing {@link PatientMedication} in EHR.
     *
     * @param patientMedication The {@link PatientMedication} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updatePatientMedication(final PatientMedication patientMedication) {
        return save("UpdatePatientMedication", patientMedication);
    }

    /**
     * Gets a {@link PatientMedication} by its id.
     *
     * @param patientMedicationId The id of the {@link PatientMedication} to fetch.
     * @return A {@link PatientMedication} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public PatientMedication getPatientMedicationByPatientMedicationId(final String patientMedicationId) {
        String query = "{\"=\":{\"patientMedicationId\":\"" + patientMedicationId + "\"}}";
        return getSingle("SelectPatientMedications", query, PatientMedicationList.class);
    }

    /**
     * Gets all the {@link PatientMedication} saved in EHR.
     *
     * @return A {@link List} of {@link PatientMedication} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientMedication> getPatientMedication() {
        return getList("SelectPatientMedications", PatientMedicationList.class);
    }

    /**
     * Gets all the {@link PatientMedication} saved in EHR for the given {@link AdmissionData} id.
     *
     * @param admissionId The id of the {@link AdmissionData}.
     * @return A {@link List} of {@link PatientMedication} associated with the given {@link AdmissionData} id,
     * or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientMedication> getPatientMedicationByAdmissionId(final String admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectPatientMedications", query, PatientMedicationList.class);
    }

    /**
     * Gets all the {@link PatientMedication} saved in EHR for the given {@link AdmissionData} id and
     * {@link Patient} id.
     *
     * @param admissionId The id of the {@link AdmissionData}.
     * @param patientId   The id of the {@link Patient}.
     * @return A list of {@link PatientMedication} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientMedication> getPatientMedicationByAdmissionIdAndPatientId(final String admissionId,
                                                                                 final String patientId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\", \"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPatientMedications", query, PatientMedicationList.class);
    }

    /**
     * Saves a new {@link PatientLabAnalysis} to EHR.
     *
     * @param patientLabAnalysis The {@link PatientLabAnalysis} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addPatientLabAnalysis(final PatientLabAnalysis patientLabAnalysis) {
        return save("InsertPatientLabAnalysis", patientLabAnalysis);
    }

    /**
     * Updates an existing {@link PatientLabAnalysis} in EHR.
     *
     * @param patientLabAnalysis The {@link PatientLabAnalysis} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updatePatientLabAnalysis(final PatientLabAnalysis patientLabAnalysis) {
        return save("UpdatePatientLabAnalysis", patientLabAnalysis);
    }

    /**
     * Gets a {@link PatientLabAnalysis} by its id.
     *
     * @param patientLabAnalysisId The id of the {@link PatientLabAnalysis} to fetch.
     * @return A {@link PatientLabAnalysis} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public PatientLabAnalysis getPatientLabAnalysisByPatientLabAnalysisId(final String patientLabAnalysisId) {
        String query = "{\"=\":{\"patientLabAnalysisId\":\"" + patientLabAnalysisId + "\"}}";
        return getSingle("SelectPatientLabAnalysis", query, PatientLabAnalysisList.class);
    }

    /**
     * Gets all the {@link PatientLabAnalysis} saved in EHR.
     *
     * @return A list of {@link PatientLabAnalysis} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientLabAnalysis> getAllPatientLabAnalysis() {
        return getList("SelectPatientLabAnalysis", PatientLabAnalysisList.class);
    }

    /**
     * Gets all the {@link PatientLabAnalysis} associated with an {@link AdmissionData}.
     *
     * @param admissionId The id of the {@link AdmissionData} whose {@link PatientLabAnalysis} to fetch.
     * @return A list of {@link PatientLabAnalysis} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientLabAnalysis> getPatientLabAnalysisByAdmissionId(final String admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectPatientLabAnalysis", query, PatientLabAnalysisList.class);
    }

    /**
     * Gets all the {@link PatientLabAnalysis} associated with a {@link Patient} and {@link AdmissionData}.
     *
     * @param patientId   The id of the {@link Patient}.
     * @param admissionId The id of the {@link AdmissionData}.
     * @return A list of {@link PatientLabAnalysis} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientLabAnalysis> getPatientLabAnalysisByPatientIdAndAdmissionId(final String patientId,
                                                                                   final String admissionId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\", \"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectPatientLabAnalysis", query, PatientLabAnalysisList.class);
    }

    /**
     * Saves a new {@link SurgicalProcedures} to EHR.
     *
     * @param surgicalProcedures The {@link SurgicalProcedures} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addSurgicalProcedure(final SurgicalProcedures surgicalProcedures) {
        return save("InsertSurgicalProcedures", surgicalProcedures);
    }

    /**
     * Updates a {@link SurgicalProcedures} saved in EHR.
     *
     * @param surgicalProcedure The {@link SurgicalProcedures} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateSurgicalProcedure(final SurgicalProcedures surgicalProcedure) {
        return save("UpdateSurgicalProcedures", surgicalProcedure);
    }

    /**
     * Gets all the {@link SurgicalProcedures} saved in EHR.
     *
     * @return A list of {@link SurgicalProcedures} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<SurgicalProcedures> getAllSurgicalProcedures() {
        return getList("SelectSurgicalProcedures", SurgicalProceduresList.class);
    }

    /**
     * Gets a {@link SurgicalProcedures} by its id.
     *
     * @param surgicalProcedureId The id of the {@link SurgicalProcedures} to fetch.
     * @return A {@link SurgicalProcedures} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public SurgicalProcedures getSurgicalProcedureBySurgicalProcedureId(final String surgicalProcedureId) {
        String query = "{\"=\":{\"surgicalProcedureId\":\"" + surgicalProcedureId + "\"}}";
        return getSingle("SelectSurgicalProcedures", query, SurgicalProceduresList.class);
    }

    /**
     * Gets all the {@link SurgicalProcedures} associated with an {@link AdmissionData}.
     *
     * @param admissionId The id of the {@link AdmissionData} whose {@link SurgicalProcedures} to fetch.
     * @return A list of {@link SurgicalProcedures} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<SurgicalProcedures> getSurgicalProceduresByAdmissionId(final String admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectSurgicalProcedures", query, SurgicalProceduresList.class);
    }

    /**
     * Gets all the {@link SurgicalProcedures} associated with a {@link Patient}.
     *
     * @param patientId The id of the {@link Patient} whose {@link SurgicalProcedures} to fetch.
     * @return A list of {@link SurgicalProcedures} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<SurgicalProcedures> getSurgicalProceduresByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectSurgicalProcedures", query, SurgicalProceduresList.class);
    }

    /**
     * Gets all the {@link SurgicalProcedures} associated with an {@link AdmissionData} and a {@link Patient}.
     *
     * @param admissionId The id of the {@link AdmissionData} whose {@link SurgicalProcedures} to fetch.
     * @param patientId   The id fo the {@link Patient} whose {@link SurgicalProcedures} to fetch.
     * @return A list of {@link SurgicalProcedures} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<SurgicalProcedures> getSurgicalProceduresByAdmissionIdAndPatientId(final String admissionId,
                                                                                   final String patientId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\",\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectSurgicalProcedures", query, SurgicalProceduresList.class);
    }

    /**
     * Adds a new {@link VitalSigns} to EHR.
     *
     * @param vitalSigns The {@link VitalSigns} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addVitalSigns(final VitalSigns vitalSigns) {
        return save("InsertVitalSigns", vitalSigns);
    }

    /**
     * Updates an existing {@link VitalSigns} in EHR.
     *
     * @param vitalSigns The {@link VitalSigns} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateVitalSigns(final VitalSigns vitalSigns) {
        return save("UpdateVitalSigns", vitalSigns);
    }

    /**
     * Gets all the {@link VitalSigns} saved in EHR.
     *
     * @return A list of {@link VitalSigns} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<VitalSigns> getAllVitalSigns() {
        return getList("SelectVitalSigns", VitalSignsList.class);
    }

    /**
     * Gets a {@link VitalSigns} saved in EHR by its id.
     *
     * @param vitalSignsId The id of the {@link VitalSigns} to fetch.
     * @return A {@link VitalSigns} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public VitalSigns getVitalSignsByVitalSignsId(final String vitalSignsId) {
        String query = "{\"=\":{\"vitalSignsId\":\"" + vitalSignsId + "\"}}";
        return getSingle("SelectVitalSigns", query, VitalSignsList.class);
    }

    /**
     * Gets all the {@link VitalSigns} associated with a {@link Coding}.
     *
     * @param codingId The id of the {@link Coding} whose {@link VitalSigns} to fetch.
     * @return A list of {@link VitalSigns} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<VitalSigns> getVitalSignsByCodingId(final String codingId) {
        String query = "{\"=\":{\"codingId\":\"" + codingId + "\"}}";
        return getList("SelectVitalSigns", query, VitalSignsList.class);
    }

    /**
     * Saves a new {@link PatientVitalSigns} to EHR.
     *
     * @param patientVitalSigns The {@link PatientVitalSigns} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addPatientVitalSigns(final PatientVitalSigns patientVitalSigns) {
        return save("InsertPatientVitalSigns", patientVitalSigns);
    }

    /**
     * Updates a {@link PatientVitalSigns} in EHR.
     *
     * @param patientVitalSigns The {@link PatientVitalSigns} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updatePatientVitalSigns(final PatientVitalSigns patientVitalSigns) {
        return save("UpdatePatientVitalSigns", patientVitalSigns);
    }

    /**
     * Gets all the {@link PatientVitalSigns} saved in EHR.
     *
     * @return A list of {@link PatientVitalSigns} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientVitalSigns> getAllPatientVitalSigns() {
        return getList("SelectPatientVitalSigns", PatientVitalSignsList.class);
    }

    /**
     * Gets a {@link PatientVitalSigns} by its id.
     *
     * @param patientVitalSignsId The id of the {@link PatientVitalSigns} to fetch.
     * @return A {@link PatientVitalSigns} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public PatientVitalSigns getPatientVitalSignsByPatientVitalSignsId(final String patientVitalSignsId) {
        String query = "{\"=\":{\"patientVitalSignsId\":\"" + patientVitalSignsId + "\"}}";
        return getSingle("SelectPatientVitalSigns", query, PatientVitalSignsList.class);
    }

    /**
     * Gets all the {@link PatientVitalSigns} associated with an {@link AdmissionData}.
     *
     * @param admissionId The id of the {@link AdmissionData} whose {@link PatientVitalSigns} to fetch.
     * @return A list of {@link PatientVitalSigns} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientVitalSigns> getPatientVitalSignsByAdmissionId(final String admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectPatientVitalSigns", query, PatientVitalSignsList.class);
    }

    /**
     * Gets all the {@link PatientVitalSigns} associated with a {@link Patient}.
     *
     * @param patientId The id of the {@link Patient} whose {@link PatientVitalSigns} to fetch.
     * @return A list of {@link PatientVitalSigns} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientVitalSigns> getPatientVitalSignsByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPatientVitalSigns", query, PatientVitalSignsList.class);
    }

    /**
     * Gets all the {@link PatientVitalSigns} associated with an {@link AdmissionData} and {@link Patient}.
     *
     * @param admissionId The id of the {@link AdmissionData} whose {@link PatientVitalSigns} to fetch.
     * @param patientId   The id of the {@link Patient} whose {@link PatientVitalSigns} to fetch.
     * @return A list of {@link PatientVitalSigns} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientVitalSigns> getPatientVitalSignsByAdmissionIdAndPatientId(final String admissionId,
                                                                                 final String patientId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\",\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPatientVitalSigns", query, PatientVitalSignsList.class);
    }

    /**
     * Saves a new {@link PatientBloodPressure} to EHR.
     *
     * @param patientBloodPressure The {@link PatientBloodPressure} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addPatientBloodPressure(final PatientBloodPressure patientBloodPressure) {
        return save("InsertPatientBloodPressure", patientBloodPressure);
    }

    /**
     * Updates a {@link PatientBloodPressure} saved in EHR.
     *
     * @param patientBloodPressure The {@link PatientBloodPressure} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updatePatientBloodPressure(final PatientBloodPressure patientBloodPressure) {
        return save("UpdatePatientBloodPressure", patientBloodPressure);
    }

    /**
     * Gets all the {@link PatientBloodPressure} saved in EHR.
     *
     * @return A list of {@link PatientBloodPressure} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientBloodPressure> getAllPatientBloodPressure() {
        return getList("SelectPatientBloodPressure", PatientBloodPressureList.class);
    }

    /**
     * Get all the {@link PatientBloodPressure} associated with an {@link AdmissionData}.
     *
     * @param admissionId The id of the {@link AdmissionData} whose {@link PatientBloodPressure} to fetch.
     * @return A list of {@link PatientBloodPressure} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientBloodPressure> getAllPatientBloodPressureByAdmissionId(final String admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectPatientBloodPressure", query, PatientBloodPressureList.class);
    }

    /**
     * Get all the {@link PatientBloodPressure} associated with a {@link Patient}.
     *
     * @param patientId The id of the {@link Patient} whose {@link PatientBloodPressure} to fetch.
     * @return A list of {@link PatientBloodPressure} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientBloodPressure> getAllPatientBloodPressureByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPatientBloodPressure", query, PatientBloodPressureList.class);
    }

    /**
     * Get all the {@link PatientBloodPressure} associated with an {@link AdmissionData} and a {@link Patient}.
     *
     * @param admissionId The id of the {@link AdmissionData} whose {@link PatientBloodPressure} to fetch.
     * @param patientId   The id of the {@link Patient} whose {@link PatientBloodPressure} to fetch.
     * @return A list of {@link PatientBloodPressure} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientBloodPressure> getAllPatientBloodPressureByAdmissionIdAndPatientId(final String admissionId,
                                                                                          final String patientId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\",\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPatientBloodPressure", query, PatientBloodPressureList.class);
    }

    /**
     * Saves a new {@link Vaccinations} to EHR.
     *
     * @param vaccinations The {@link Vaccinations} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addVaccination(final Vaccinations vaccinations) {
        return save("InsertVaccinations", vaccinations);
    }

    /**
     * Updates a {@link Vaccinations} saved in EHR.
     *
     * @param vaccination The {@link Vaccinations} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateVaccination(final Vaccinations vaccination) {
        return save("UpdateVaccinations", vaccination);
    }

    /**
     * Gets all the {@link Vaccinations} saved in EHR.
     *
     * @return A list of {@link Vaccinations} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<Vaccinations> getAllVaccinations() {
        return getList("SelectVaccinations", VaccinationsList.class);
    }

    /**
     * Gets a {@link Vaccinations} by its id.
     *
     * @param vaccinationId The id of the {@link Vaccinations} to fetch.
     * @return A {@link Vaccinations} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public Vaccinations getVaccinationByVaccinationId(final String vaccinationId) {
        String query = "{\"=\":{\"vaccinationId\":\"" + vaccinationId + "\"}}";
        return getSingle("SelectVaccinations", query, VaccinationsList.class);
    }

    /**
     * Adds a new {@link PatientVaccinations} to EHR.
     *
     * @param patientVaccinations The {@link PatientVaccinations} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addPatientVaccination(final PatientVaccinations patientVaccinations) {
        return save("InsertPatientVaccinations", patientVaccinations);
    }

    /**
     * Updates an existing {@link PatientVaccinations} in EHR.
     *
     * @param patientVaccination The {@link PatientVaccinations} to upadte.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updatePatientVaccination(final PatientVaccinations patientVaccination) {
        return save("UpdatePatientVaccinations", patientVaccination);
    }

    /**
     * Gets all the {@link PatientVaccinations} saved in EHR.
     *
     * @return A list of {@link PatientVaccinations} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientVaccinations> getAllPatientVaccinations() {
        return getList("SelectPatientVaccinations", PatientVaccinationsList.class);
    }

    /**
     * Gets all the {@link PatientVaccinations} associated with an {@link AdmissionData}.
     *
     * @param admissionId The id of the {@link AdmissionData} whose {@link PatientVaccinations} to fetch.
     * @return A list of {@link PatientVaccinations} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientVaccinations> getPatientVaccinationsByAdmissionId(final String admissionId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\"}}";
        return getList("SelectPatientVaccinations", query, PatientVaccinationsList.class);
    }

    /**
     * Gets all the {@link PatientVaccinations} associated with a {@link Patient}.
     *
     * @param patientId The id of the {@link Patient} whose {@link PatientVaccinations} to fetch.
     * @return A list of {@link PatientVaccinations} or null in case of no match.
     */
    public List<PatientVaccinations> getPatientVaccinationsByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPatientVaccinations", query, PatientVaccinationsList.class);
    }


    /**
     * Gets all the {@link PatientVaccinations} associated with an {@link AdmissionData} and a {@link Patient}.
     *
     * @param admissionId The id of the {@link AdmissionData} whose {@link PatientVaccinations} to fetch.
     * @param patientId   The id of the {@link Patient} whose {@link PatientVaccinations} to fetch.
     * @return A list of {@link PatientVaccinations} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientVaccinations> getPatientVaccinationsByAdmissionIdAndPatientId(final String admissionId,
                                                                                     final String patientId) {
        String query = "{\"=\":{\"admissionId\":\"" + admissionId + "\",\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectPatientVaccinations", query, PatientVaccinationsList.class);
    }

    /**
     * Gets all the {@link PatientVaccinations} associated with a {@link Vaccinations}.
     *
     * @param vaccinationId The id of the {@link Vaccinations} whose {@link PatientVaccinations} to fetch.
     * @return A list of {@link PatientVaccinations} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<PatientVaccinations> getPatientVaccinationsByVaccinationId(final String vaccinationId) {
        String query = "{\"=\":{\"vaccinationId\":\"" + vaccinationId + "\"}}";
        return getList("SelectPatientVaccinations", query, PatientVaccinationsList.class);
    }

    /**
     * Saves a new {@link SocialHistory} to EHR.
     *
     * @param socialHistory The {@link SocialHistory} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addSocialHistory(final SocialHistory socialHistory) {
        return save("InsertSocialHistory", socialHistory);
    }

    /**
     * Updates a {@link SocialHistory} saved in EHR.
     *
     * @param socialHistory The {@link SocialHistory} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateSocialHistory(final SocialHistory socialHistory) {
        return save("UpdateSocialHistory", socialHistory);
    }

    /**
     * Gets all the {@link SocialHistory} saved in EHR.
     *
     * @return A list of {@link SocialHistory} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<SocialHistory> getAllSocialHistory() {
        return getList("SelectSocialHistory", SocialHistoryList.class);
    }

    /**
     * Gets a {@link SocialHistory} saved in EHR by its id.
     *
     * @param socialHistoryId The id of the {@link SocialHistory} to fetch.
     * @return A {@link SocialHistory} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public SocialHistory getSocialHistoryBySocialHistoryId(final String socialHistoryId) {
        String query = "{\"=\":{\"socialHistoryId\":\"" + socialHistoryId + "\"}}";
        return getSingle("SelectSocialHistory", query, SocialHistoryList.class);
    }

    /**
     * Gets the {@link SocialHistory} associated with a {@link Patient}.
     *
     * @param patientId The id of the {@link Patient} whose {@link SocialHistory} to fetch.
     * @return A list of {@link SocialHistory} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<SocialHistory> getSocialHistoryByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectSocialHistory", query, SocialHistoryList.class);
    }

    /**
     * Saves a new {@link FunctionalStatus} to EHR.
     *
     * @param functionalStatus The {@link FunctionalStatus} to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String addFunctionalStatus(final FunctionalStatus functionalStatus) {
        return save("InsertFunctionalStatus", functionalStatus);
    }

    /**
     * Updates a {@link FunctionalStatus} saved in EHR.
     *
     * @param functionalStatus The {@link FunctionalStatus} to update.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    public String updateFunctionalStatus(final FunctionalStatus functionalStatus) {
        return save("UpdateFunctionalStatus", functionalStatus);
    }

    /**
     * Gets all the {@link FunctionalStatus} saved in EHR.
     *
     * @return A list of {@link FunctionalStatus} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<FunctionalStatus> getAllFunctionalStatus() {
        return getList("SelectFunctionalStatus", FunctionalStatusList.class);
    }

    /**
     * Gets all the {@link FunctionalStatus} associated with a {@link Patient}.
     *
     * @param patientId The id of the {@link Patient} whose {@link FunctionalStatus} to fetch.
     * @return A list of {@link FunctionalStatus} or null in case of no match.
     * @throws EhrClientException in case of an error.
     */
    public List<FunctionalStatus> getFunctionalStatusByPatientId(final String patientId) {
        String query = "{\"=\":{\"patientId\":\"" + patientId + "\"}}";
        return getList("SelectFunctionalStatus", query, FunctionalStatusList.class);
    }

    /**
     * Generic method for saving entities to EHR.
     *
     * @param path   The path where to save the entity.
     * @param entity The entity to save.
     * @param <A>    The type of the entity to save.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    private <A> String save(final String path, final A entity) {
        return postPath(path, entity);
    }

    /**
     * Generic methods to get all the entities in the given path that match the given query.
     *
     * @param path     The path to query.
     * @param query    The query to run.
     * @param theClass The class to convert the response to.
     * @param <A>      The type of the response.
     * @return An instance of {@link A} or null in case none matches the query.
     * @throws EhrClientException in case of an error.
     */
    private <A> A getAll(final String path, final String query, final Class<A> theClass) {
        String response = postPath(path, query);

        if (TextUtils.equals(EMPTY_JSON_ARRAY, response)) {
            //Empty Json Array means no match.
            return null;
        }

        try {
            //Try to convert the response.
            A responseEntity = objectMapper.readValue(response, theClass);
            return responseEntity;

        } catch (Exception error) {
            //Invalid response. eg. Mismatch between the response object and the class to convert to.
            error("Error while getting all entities from " + path, error);
            throw new EhrClientException(
                    path,
                    query,
                    error);
        }
    }

    /**
     * Generic methods to get all the entities in the given path.
     *
     * @param path     The path to query.
     * @param theClass The class to convert the response to.
     * @param <A>      The type of the response.
     * @return An instance of A or null in case none matches the query.
     * @throws EhrClientException in case of an error.
     */
    private <A> A getAll(final String path, final Class<A> theClass) {
        return getAll(path, QUERY_ALL, theClass);
    }

    /**
     * Generic methods to get all the entities in the given path.
     *
     * @param path     The path to query.
     * @param query    The query to match.
     * @param theClass The type of the response that is {@link Listable}.
     * @param <A>      The type of entities that will be returned.
     * @return A {@link List} of {@link A}s or null in case none matches the query.
     * @throws EhrClientException in case of an error.
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
     * @param path     The path to query.
     * @param theClass The type of the response that is {@link Listable}.
     * @param <A>      The type of entities that will be returned.
     * @return A {@link List} of {@link A}s or null in case none matches the query.
     * @throws EhrClientException in case of an error.
     */
    private <A, B extends Listable<A>> List<A> getList(final String path,
                                                       final Class<B> theClass) {
        return getList(path, QUERY_ALL, theClass);
    }

    /**
     * Gets the single entity of type {@link A} that matches the given query.
     *
     * @param path     The path from which to select the entity.
     * @param query    The query to match.
     * @param theClass The class of type {@link B} that the response will be converted to.
     * @param <A>      The type of the entity to return.
     * @param <B>      The type of the response EHR will return.
     * @return An instance of {@link A} or null in case none matches the query.
     * @throws EhrClientException in case of an error.
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
     * Posts the given entity to the given path.
     *
     * @param path   The path where to post.
     * @param entity The entity to post.
     * @param <A>    The type of the entity to post.
     * @return A JSON String.
     * @throws EhrClientException in case of an error.
     */
    private <A> String postPath(final String path, final A entity) {
        LOGGER.debug(String.format("Will try to post entity: \"%s\", to: \"%s\".", entity, path));
        try {
            Entity payload;
            if (entity.getClass().equals(String.class)) {
                LOGGER.debug(String.format("Payload to post to: \"%s\" is a String.", path));
                payload = Entity.json(entity);
            } else {
                LOGGER.debug(String.format("Payload to post to: \"%s\" is a %s.", path, entity.getClass().getSimpleName()));
                final byte[] data = objectMapper.writeValueAsBytes(entity);
                String json = new String(data, "UTF-8");
                json = StringEscapeUtils.unescapeHtml4(json);
                LOGGER.info(json);
                payload = Entity.json(json);
            }
            LOGGER.debug(String.format("Will try to post payload: \"%s\", to: \"%s\".", payload, path));

            Response response = getClientForPath(path).post(payload);
            Response.Status.Family statusFamily = response.getStatusInfo().getFamily();
            LOGGER.debug(String.format("Posting entity %s to %s returned a %s response.", entity, path, statusFamily));

            if (statusFamily == Response.Status.Family.SUCCESSFUL) {
                for (String key : response.getHeaders().keySet()) {
                    LOGGER.info(key + " : " + response.getHeaders().get(key));
                }
                String responseString = response.readEntity(String.class);
                LOGGER.debug(String.format("Returning \"%s\" for post to \"%s\"", responseString, path));
                return StringEscapeUtils.unescapeHtml4(responseString);

            } else {
                throw new EhrClientException(
                        path,
                        entity,
                        response.getStatusInfo().getStatusCode(),
                        response.getStatusInfo().getReasonPhrase());
            }

        } catch (Exception error) {
            error("Error while posting to path: " + path, error);
            throw new EhrClientException(
                    path,
                    entity,
                    error);
        }
    }

    /**
     * Get a client to the orion context broker for the selected path.
     *
     * @param path the path to request.
     * @return a client to execute an http request.
     */
    private Invocation.Builder getClientForPath(final String path) {
        Client client = ClientBuilder.newClient();
        return client.target(connectionUrl)
                .path(path)
                .request()
                .acceptEncoding("UTF-8")
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Accept", "application/json; charset=UTF-8");
    }
}
