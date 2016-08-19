package ru.svalov.ma.data.google;


import com.google.gdata.client.spreadsheet.SpreadsheetService;
import org.springframework.beans.factory.annotation.Value;
import ru.svalov.ma.auth.google.GoogleCredentialService;

import java.io.IOException;

public class GoogleSheetsService {

    @Value("application.name")
    private String applicationName;
    private GoogleCredentialService credentialService;

    public GoogleSheetsService(GoogleCredentialService credentialService) {
        this.credentialService = credentialService;
    }

    public SpreadsheetService getSheetsService() throws IOException {
        SpreadsheetService service = new SpreadsheetService(applicationName);
        service.setProtocolVersion(SpreadsheetService.Versions.V3);
        service.setOAuth2Credentials(credentialService.authorize());
        return service;
    }
}