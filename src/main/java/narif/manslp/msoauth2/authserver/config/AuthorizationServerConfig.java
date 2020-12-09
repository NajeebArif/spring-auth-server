package narif.manslp.msoauth2.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;

import java.util.List;
import java.util.Map;

@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        var inMemoryClientDetailsService = getInMemoryClientDetailsService();

        clients.withClientDetails(inMemoryClientDetailsService);
    }

    private InMemoryClientDetailsService getInMemoryClientDetailsService() {
        var inMemoryClientDetailsService = new InMemoryClientDetailsService();
        var baseClientDetails = new BaseClientDetails();
        baseClientDetails.setClientId("clientId");
        baseClientDetails.setClientSecret("clientSecret");
        baseClientDetails.setScope(List.of("read"));
        baseClientDetails.setAuthorizedGrantTypes(List.of("password"));
        inMemoryClientDetailsService.setClientDetailsStore(Map.of("client",baseClientDetails));
        return inMemoryClientDetailsService;
    }
}
