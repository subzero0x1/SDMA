package ru.svalov.ma.planner.tempo;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Optional;

public class TempoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TempoService.class);

    private String tempoURL;
    private String login;
    private String password;

    public TempoService(String tempoURL, String login, String password) {
        this.tempoURL = tempoURL;
        this.login = login;
        this.password = password;
    }

    public Optional<String> getUserPlannedTasks(String userName, LocalDate start, LocalDate end) {
        HttpHost target = new HttpHost("jira.bssys.com", 80, "http");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials(login, password));

        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(target, basicAuth);
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAuthCache(authCache);

        final String url = String.format(tempoURL, userName, start, end);
        HttpGet httpget = new HttpGet(url);

        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build()) {
            try (CloseableHttpResponse httpResponse = httpClient.execute(target, httpget, localContext)) {
                if (httpResponse.getStatusLine().getStatusCode() != 200) {
                    LOGGER.error("Request to Tempo failed. Status code is {}. Response is {}", httpResponse.getStatusLine().getStatusCode(),
                            EntityUtils.toString(httpResponse.getEntity()));
                } else {
                    return Optional.of(EntityUtils.toString(httpResponse.getEntity()));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error during by getting labors from Tempo", e);
        }

        return Optional.empty();
    }
}
