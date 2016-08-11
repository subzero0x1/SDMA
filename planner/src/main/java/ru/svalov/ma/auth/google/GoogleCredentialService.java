package ru.svalov.ma.auth.google;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.springframework.beans.factory.InitializingBean;
import ru.svalov.ma.planner.google.GoogleCalendarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class GoogleCredentialService implements InitializingBean {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private FileDataStoreFactory DATA_STORE_FACTORY;
    private HttpTransport HTTP_TRANSPORT;
    private String accessType;
    private String clientSecret;
    private String credentialsStore;
    private List<String> scopes;

    public Credential authorize() throws IOException {
        InputStream in = GoogleCalendarService.class.getResourceAsStream("/" + clientSecret);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow
                        .Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType(accessType)
                        .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(
                    new java.io.File(
                            System.getProperty("user.home"),
                            credentialsStore
                    ));
        } catch (Exception t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setCredentialsStore(String credentialsStore) {
        this.credentialsStore = credentialsStore;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }
}