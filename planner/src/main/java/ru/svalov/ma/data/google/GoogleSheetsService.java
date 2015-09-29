package ru.svalov.ma.data.google;


import com.google.gdata.client.spreadsheet.SpreadsheetService;
import ru.svalov.ma.auth.google.GoogleCredentialService;

import java.io.IOException;

public class GoogleSheetsService {

    private String applicationName;
    private GoogleCredentialService credentialService;

    public SpreadsheetService getSheetsService() throws IOException {
        SpreadsheetService service = new SpreadsheetService(applicationName);
        service.setProtocolVersion(SpreadsheetService.Versions.V3);
        service.setOAuth2Credentials(credentialService.authorize());
        return service;
    }

    public void setCredentialService(GoogleCredentialService credentialService) {
        this.credentialService = credentialService;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}