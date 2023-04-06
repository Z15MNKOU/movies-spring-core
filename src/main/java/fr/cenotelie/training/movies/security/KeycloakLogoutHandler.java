package fr.cenotelie.training.movies.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Component
@Slf4j
public class KeycloakLogoutHandler implements LogoutHandler {

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public void logout(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) {
        doLogout((OidcUser) authentication.getPrincipal());
    }

    private void doLogout(final OidcUser oidcUser) {
        final var issuerLogoutEndpoint = oidcUser.getIssuer() + "/protocol/openid-connect/logout";
        final var uri = UriComponentsBuilder
                .fromUriString(issuerLogoutEndpoint)
                .queryParam("id_token_hint", oidcUser.getIdToken().getTokenValue());

        final var response = restTemplate.getForEntity(uri.toUriString(), String.class);
        log.info("Called Keycloak to log out\nResponse : {}\nStatus : {}",
                response.getBody(), response.getStatusCode());
    }
}
