package narif.manslp.msoauth2.authserver.model;

import lombok.Getter;
import lombok.Setter;
import narif.manslp.msoauth2.authserver.entities.client.Client;
import narif.manslp.msoauth2.authserver.entities.client.GrantType;
import narif.manslp.msoauth2.authserver.entities.client.RedirectUrl;
import narif.manslp.msoauth2.authserver.entities.client.Scope;

import java.util.List;

@Getter
@Setter
public class ClientResource {

    private String clientId;
    private String clientSecret;
    private List<String> scopes;
    private List<String> grantTypes;
    private List<String> redirectUrls;

    public Client mapToClient(){
        final var client = new Client();
        client.setClientId(getClientId());
        client.setClientSecret(getClientSecret());
        scopes.forEach(s->client.addScope(new Scope(s)));
        grantTypes.forEach(g->client.addGrantType(new GrantType(g)));
        redirectUrls.forEach(r->client.addRedirectUrl(new RedirectUrl(r)));
        return client;
    }
}
