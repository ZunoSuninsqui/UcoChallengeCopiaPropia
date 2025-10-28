package co.edu.uco.ucochallenge.secondary.adapters.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import co.edu.uco.ucochallenge.crosscuting.exception.ExceptionLayer;
import co.edu.uco.ucochallenge.crosscuting.exception.UcoChallengeException;
import co.edu.uco.ucochallenge.crosscuting.helper.TextHelper;
import co.edu.uco.ucochallenge.crosscuting.messages.MessageKey;
import co.edu.uco.ucochallenge.secondary.adapters.service.dto.RemoteCatalogEntry;
import co.edu.uco.ucochallenge.secondary.ports.service.ParameterServicePort;

@Component
public class ParameterServiceAdapter implements ParameterServicePort {

    private final RestTemplate restTemplate;
    private final String endpoint;

    public ParameterServiceAdapter(final RestTemplate restTemplate,
            @Value("${services.parameters.base-url}") final String endpoint) {
        this.restTemplate = restTemplate;
        this.endpoint = endpoint;
    }

    @Override
    public String getParameter(final String key) {
        if (TextHelper.isEmpty(key)) {
            return TextHelper.getDefault();
        }

        try {
            final ResponseEntity<RemoteCatalogEntry> response = restTemplate.getForEntity(resolveUrl(key),
                    RemoteCatalogEntry.class);
            final RemoteCatalogEntry body = response.getBody();
            return body == null || TextHelper.isEmpty(body.value()) ? TextHelper.getDefault() : body.value();
        } catch (final HttpClientErrorException.NotFound notFound) {
            return TextHelper.getDefault();
        } catch (final RestClientException exception) {
            throw UcoChallengeException.createTechnicalException(ExceptionLayer.APPLICATION,
                    MessageKey.GENERAL_TECHNICAL_ERROR, exception);
        }
    }

    private String resolveUrl(final String key) {
        return String.format("%s/%s", endpoint, key);
    }
}