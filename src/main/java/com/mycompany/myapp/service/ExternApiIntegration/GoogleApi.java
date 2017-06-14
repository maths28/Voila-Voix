package com.mycompany.myapp.service.ExternApiIntegration;

public class GoogleApi {
    private static GoogleApi ourInstance = new GoogleApi();

    public static GoogleApi getInstance() {
        return ourInstance;
    }

    private GoogleApi() {
    }
}
