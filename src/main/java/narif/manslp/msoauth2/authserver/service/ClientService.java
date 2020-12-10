package narif.manslp.msoauth2.authserver.service;

import narif.manslp.msoauth2.authserver.entities.client.Client;
import narif.manslp.msoauth2.authserver.model.ClientResource;
import narif.manslp.msoauth2.authserver.repositories.ClientJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientJpaRepository clientJpaRepository;

    public ClientResource getClient(String clientId){
        return clientJpaRepository.findClientByClientId(clientId)
                .map(Client::mapToClientResource)
                .orElseThrow(()->new RuntimeException("Client ID Not found"));
    }

    public ClientResource saveClient(ClientResource clientResource){
        final var save = clientJpaRepository.save(clientResource.mapToClient());
        return save.mapToClientResource();
    }

    public List<ClientResource> getClients(){
        return clientJpaRepository.findAll().stream().map(Client::mapToClientResource).collect(Collectors.toList());
    }
}
