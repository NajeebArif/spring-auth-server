package narif.manslp.msoauth2.authserver.service;

import narif.manslp.msoauth2.authserver.model.CustomClientDetails;
import narif.manslp.msoauth2.authserver.repositories.ClientJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

public class CustomClientDetailsService implements ClientDetailsService {

    @Autowired
    private ClientJpaRepository repository;

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        repository.findClientByClientId(s)
                .map(CustomClientDetails::new).orElseThrow(()->new ClientRegistrationException("CLIENT ID NOT FOUND!"));
        return null;
    }
}
