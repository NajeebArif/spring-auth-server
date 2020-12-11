package narif.manslp.msoauth2.authserver.model;

import narif.manslp.msoauth2.authserver.entities.client.Client;
import narif.manslp.msoauth2.authserver.entities.client.GrantType;
import narif.manslp.msoauth2.authserver.entities.client.RedirectUrl;
import narif.manslp.msoauth2.authserver.entities.client.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;
import java.util.stream.Collectors;

public class CustomClientDetails implements ClientDetails {

    private Client client;

    public CustomClientDetails(Client client) {
        this.client = client;
    }

    @Override
    public String getClientId() {
        return client.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return Set.of(getClientSecret());
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return client.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return client.getScopes().stream().map(Scope::getName).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return client.getGrantTypes().stream().map(GrantType::getName).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return client.getRedirectUrls().stream().map(RedirectUrl::getUrl).collect(Collectors.toSet());
    }

//    @Override
//    public Collection<GrantedAuthority> getAuthorities() {
//        return Arrays.asList(new SimpleGrantedAuthority("ROLE_CLIENT"));
//    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return client.getScopes().stream().map(s->"ROLE_"+s.getName()).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 3600;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 3600;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
