package com.amaxilatis.ehr.exception;

import com.amaxilatis.ehr.EhrClient;

/**
 * An {@link Exception} that occurred during an {@link com.amaxilatis.ehr.EhrClient}
 * method invocation. This {@link Exception} will always include the status code
 * and status message of the request that failed.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
public class EhrClientException extends RuntimeException {

    private final String path;
    private final String entity;
    private final int statusCode;
    private final String statusMessage;

    /**
     * Constructs a new {@link EhrClientException} with the given parameters.
     *
     * @param thePath The path of the failed request.
     * @param theEntity The entity that was sent as part of the failed request.
     * @param theStatusCode The status code of the failed request.
     * @param theStatusMessage The status message of the failed request.
     * @param <A> The type of entity that was sent as part of the failed request.
     */
    public <A> EhrClientException(final String thePath,
                                  final A theEntity,
                                  final int theStatusCode,
                                  final String theStatusMessage) {
        super(String.format(
                "Request to path \"%s\", with entity \"%s\" failed with status code \"%d\" and status message \"%s\"",
                thePath,
                theEntity,
                theStatusCode,
                theStatusMessage));

        path = thePath;
        entity = theEntity.toString();
        statusCode = theStatusCode;
        statusMessage = theStatusMessage;
    }

    /**
     * Constructs a new {@link EhrClientException} with the given parameters.
     *
     * @param thePath The path of the failed request.
     * @param theEntity The entity that was sent as part of the failed request.
     * @param cause The {@link Exception} that was thrown during the failed request.
     * @param <A> The type of the entity that was sent as part of the failed request.
     */
    public <A> EhrClientException(final String thePath,
                                  final A theEntity,
                                  final Exception cause) {
        super(String.format(
                "Request to path \"%s\", with entity \"%s\" failed with status code \"%d\" and status message \"%s\"",
                thePath,
                theEntity,
                500,
                cause.getMessage()));

        path = thePath;
        entity = theEntity.toString();
        statusCode = 500;
        statusMessage = cause.getMessage();
    }

    /**
     * Gets the path of the failed request.
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the entity {@link String} that was sent as part of the failed request.
     */
    public String getEntity() {
        return entity;
    }

    /**
     * Gets the status code of the failed request.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Gets the status message of the failed request.
     */
    public String getStatusMessage() {
        return statusMessage;
    }
}
