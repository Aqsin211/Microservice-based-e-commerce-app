package az.company.gateway.mapper;


import az.company.gateway.model.response.AuthResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public enum AuthMapper {
    AUTH_MAPPER;

    public AuthResponse buildAuthResponse(OidcUser oidcUser, OAuth2AuthorizedClient client) {
        return AuthResponse.builder()
                .userId(oidcUser.getEmail())
                .accessToken(oidcUser.getIdToken().getTokenValue())
                .expiresAt(DateMapper.DATE_MAPPER.toLocalDateTime(client.getAccessToken().getExpiresAt()))
                .authorities(oidcUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
