package narif.manslp.msoauth2.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;

import java.util.Map;
import java.util.Set;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManagerBean;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManagerBean);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        var inMemoryClientDetailsService = getInMemoryClientDetailsService();
        clients.withClientDetails(inMemoryClientDetailsService);
    }

    private InMemoryClientDetailsService getInMemoryClientDetailsService() {
        var inMemoryClientDetailsService = new InMemoryClientDetailsService();
        var baseClientDetails = getBaseClientDetails();
        inMemoryClientDetailsService.setClientDetailsStore(Map.of("clientId",baseClientDetails));
        return inMemoryClientDetailsService;
    }

    private BaseClientDetails getBaseClientDetails() {
        var clientDetails = new BaseClientDetails();
        clientDetails.setClientId("clientId");
        clientDetails.setClientSecret("clientSecret");
        clientDetails.setScope(Set.of("read"));
        clientDetails.setAuthorizedGrantTypes(Set.of("password"));
        return clientDetails;
    }
}
