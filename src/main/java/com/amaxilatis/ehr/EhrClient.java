package com.amaxilatis.ehr;

import com.amaxilatis.ehr.exception.PatientIdExistsException;
import com.amaxilatis.ehr.model.AdmissionData;
import com.amaxilatis.ehr.model.AdmissionType;
import com.amaxilatis.ehr.model.Allergies;
import com.amaxilatis.ehr.model.Patient;
import com.amaxilatis.ehr.model.list.AdmissionDataList;
import com.amaxilatis.ehr.model.list.AdmissionTypeList;
import com.amaxilatis.ehr.model.list.AllergiesDataList;
import com.amaxilatis.ehr.model.list.PatientDataList;
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
            PatientDataList list = new ObjectMapper().readValue(resp, PatientDataList.class);
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
            AllergiesDataList list = new ObjectMapper().readValue(resp, AllergiesDataList.class);
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
            AdmissionDataList list = new ObjectMapper().readValue(resp, AdmissionDataList.class);
            return list.getAdmissionData();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(resp);
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
            AdmissionTypeList list = new ObjectMapper().readValue(resp, AdmissionTypeList.class);
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
            PatientDataList list = new ObjectMapper().readValue(resp, PatientDataList.class);
            return list.getPatientData();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(resp);
            return null;
        }
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
            final Entity payload = Entity.json(new ObjectMapper().writeValueAsString(entity));
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
