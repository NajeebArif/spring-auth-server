package narif.manslp.msoauth2.authserver.service;

import lombok.extern.slf4j.Slf4j;
import narif.manslp.msoauth2.authserver.entities.client.Client;
import narif.manslp.msoauth2.authserver.entities.client.GrantType;
import narif.manslp.msoauth2.authserver.entities.client.RedirectUrl;
import narif.manslp.msoauth2.authserver.entities.client.Scope;
import narif.manslp.msoauth2.authserver.model.CustomClientDetails;
import narif.manslp.msoauth2.authserver.repositories.ClientJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomClientDetailsService implements ClientDetailsService {

    private ClientJpaRepository repository;

    public CustomClientDetailsService(ClientJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        return repository.findClientByClientId(s)
                .map(CustomClientDetails::new)
                .orElseThrow(() -> new ClientRegistrationException("CLIENT ID NOT FOUND!"));
    }
}
