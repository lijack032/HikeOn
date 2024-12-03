package use_case.locationSearch;

/**
 * The outputBoundary interface for the location user case.
 */
public interface LocationOutputBoundary {

    /**
     * Present the output data retrieved from the user case interactor.
     * @param outputData is the output data retrieved from the user case interactor.
     */
    void presentLocationResults(LocationOutputData outputData);
}
