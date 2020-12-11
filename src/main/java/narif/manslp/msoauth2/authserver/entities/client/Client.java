package narif.manslp.msoauth2.authserver.entities.client;

import lombok.Data;
import narif.manslp.msoauth2.authserver.model.ClientResource;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Oauth2_Clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String clientId;
    private String clientSecret;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Scope> scopes = new ArrayList<>();

    public void addScope(Scope scope){
        scopes.add(scope);
        scope.setClient(this);
    }

    public void removeScope(Scope scope){
        scopes.remove(scope);
        scope.setClient(null);
    }

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<GrantType> grantTypes = new ArrayList<>();

    public void addGrantType(GrantType grantType){
        grantTypes.add(grantType);
        grantType.setClient(this);
    }

    public void removeGrantType(GrantType grantType){
        grantTypes.remove(grantType);
        grantType.setClient(null);
    }

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RedirectUrl> redirectUrls = new ArrayList<>();

    public void addRedirectUrl(RedirectUrl redirectUrl){
        redirectUrls.add(redirectUrl);
        redirectUrl.setClient(this);
    }

    public void removeRedirectUrl(RedirectUrl redirectUrl){
        redirectUrls.remove(redirectUrl);
        redirectUrl.setClient(null);
    }

    public ClientResource mapToClientResource(){
        final var clientResource = new ClientResource();
        clientResource.setClientId(getClientId());
        clientResource.setClientSecret(getClientSecret());
        clientResource.setGrantTypes(getGrantTypes().stream().map(GrantType::getName).collect(Collectors.toList()));
        clientResource.setScopes(getScopes().stream().map(Scope::getName).collect(Collectors.toList()));
        clientResource.setRedirectUrls(getRedirectUrls().stream().map(RedirectUrl::getUrl).collect(Collectors.toList()));
        return clientResource;
    }
}
