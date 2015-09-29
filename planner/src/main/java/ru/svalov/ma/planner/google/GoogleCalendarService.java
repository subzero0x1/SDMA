package ru.svalov.ma.planner.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import ru.svalov.ma.auth.google.GoogleCredentialService;

import java.io.IOException;

public class GoogleCalendarService {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    private String applicationName;
    private GoogleCredentialService credentialService;

    public com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credentialService.authorize())
                .setApplicationName(applicationName)
                .build();
    }

    public void setCredentialService(GoogleCredentialService credentialService) {
        this.credentialService = credentialService;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
