package fr.csid.voilavoix.service.ExternApiIntegration;

/**
 * Created by user on 20/02/2017.
 */
public class GoogleApi {
    private static GoogleApi ourInstance = new GoogleApi();

    public static GoogleApi getInstance() {
        return ourInstance;
    }

    private GoogleApi() {
    }
}
