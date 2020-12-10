package narif.manslp.msoauth2.authserver.controllers;

import narif.manslp.msoauth2.authserver.model.ClientResource;
import narif.manslp.msoauth2.authserver.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<ClientResource> getClients(){
        return clientService.getClients();
    }

    @GetMapping("{client_id}")
    public ClientResource getClient(@PathVariable("client_id") String clientId){
        return clientService.getClient(clientId);
    }

    @PostMapping
    public ClientResource saveClient(@RequestBody ClientResource resource){
        return clientService.saveClient(resource);
    }
}
