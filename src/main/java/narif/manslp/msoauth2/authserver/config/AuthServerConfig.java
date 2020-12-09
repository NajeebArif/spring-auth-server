package narif.manslp.msoauth2.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.Map;
import java.util.Set;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${keypair.alias}")
    private String alias;
    @Value("${keypair.keystore}")
    private String keyStore;
    @Value("${keypair.storepass}")
    private String storepass;


    @Autowired
    private AuthenticationManager authenticationManagerBean;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        final var inMemoryClientDetailsService = getInMemoryClientDetailsService();
        clients.withClientDetails(inMemoryClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManagerBean)
                .tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        final var jwtAccessTokenConverter = new JwtAccessTokenConverter();
        final var keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keyStore), storepass.toCharArray());
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    //<editor-fold desc="HELPER METHODS">
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
//</editor-fold>
}
